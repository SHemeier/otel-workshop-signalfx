package backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpserver.HttpServer;
import httputil.HttpUtil;
import io.github.cdimascio.dotenv.Dotenv;
import io.grpc.Context;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import otelutil.OtelUtil;
import redis.clients.jedis.Jedis;
import redisclient.TracingJedisWrapper;

public final class BackEnd implements AutoCloseable {

  public static final String BACKEND_PATH = "/backend";
  private static final String REDIS_KEY = "MyBackendKey";
  private final HttpServer httpServer;

  private BackEnd() throws IOException {
    Dotenv dotenv = Dotenv.load();
    int backendServerPort = Integer.parseInt(dotenv.get("BACKEND_SERVER_PORT"));
    System.out.println(dotenv.get("OTEL_RESOURCE_ATTRIBUTES"));
    this.httpServer =
        HttpServer.newBuilder(backendServerPort)
            .addHandler(BACKEND_PATH, new Handler(new ScheduledThreadPoolExecutor(4)))
            .build();
  }

  @Override
  public void close() {
    httpServer.close();
  }

  private static final class Handler implements HttpHandler {
    private final ScheduledThreadPoolExecutor executor;

    private Handler(ScheduledThreadPoolExecutor executor) {
      this.executor = executor;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
      String action;
      try {
        action = HttpUtil.splitQuery(httpExchange.getRequestURI()).get("action");
        if (action == null) {
          httpExchange.sendResponseHeaders(500, 0);
          httpExchange.getResponseBody().close();
          return;
        }
      } catch (UnsupportedEncodingException e) {
        httpExchange.sendResponseHeaders(500, 0);
        httpExchange.getResponseBody().close();
        return;
      }

      // Process the request
      Future<String> future =
          executor.submit(
              Context.current()
                  .wrap(
                      () -> {
                        Jedis jedis = new TracingJedisWrapper("localhost");
                        if ("increment".equals(action)) {
                          return jedis.incr(REDIS_KEY).toString();
                        }
                        if ("decrement".equals(action)) {
                          return jedis.decr(REDIS_KEY).toString();
                        }
                        if ("get".equals(action)) {
                          return jedis.get(REDIS_KEY);
                        }
                        if ("set".equals(action)) {
                          return jedis.set(REDIS_KEY, "42");
                        }
                        return null;
                      }));

      try {
        String response = future.get();
        if (response == null) {
          httpExchange.sendResponseHeaders(500, 0);
          httpExchange.getResponseBody().close();
          return;
        }
        httpExchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
          os.write(response.getBytes(Charset.defaultCharset()));
        }
      } catch (ExecutionException | InterruptedException ex) {
        httpExchange.sendResponseHeaders(500, 0);
        httpExchange.getResponseBody().close();
      }
    }
  }

  /**
   * Main method to run the example.
   *
   * @param args It is not required.
   * @throws IOException Something might go wrong.
   */
  public static void main(String[] args) throws IOException {
    OtelUtil.setupTraceExporter();
    final BackEnd backEnd = new BackEnd();

    // Gracefully close the servers
    Runtime.getRuntime().addShutdownHook(new Thread(backEnd::close));
  }
}
