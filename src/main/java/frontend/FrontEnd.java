package frontend;

import static backend.BackEnd.BACKEND_PATH;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpclient.HttpClient;
import httpclient.HttpResult;
import httpserver.HttpServer;
import httputil.HttpUtil;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public final class FrontEnd implements AutoCloseable {

  public static final String FRONTEND_PATH = "/frontend";
  private final HttpServer httpServer;

  private FrontEnd() throws IOException {
    Dotenv dotenv = Dotenv.load();
    int frontendServerPort = Integer.parseInt(dotenv.get("FRONTEND_SERVER_PORT"));
    int backendServerPort = Integer.parseInt(dotenv.get("BACKEND_SERVER_PORT"));
    System.out.println(dotenv.get("OTEL_RESOURCE_ATTRIBUTES"));
    this.httpServer =
        HttpServer.newBuilder(frontendServerPort)
            .addHandler(FRONTEND_PATH, new Handler(backendServerPort))
            .build();
  }

  @Override
  public void close() {
    httpServer.close();
  }

  private static final class Handler implements HttpHandler {
    private final HttpClient httpClient;

    private Handler(int backendServerPort) {
      this.httpClient = new HttpClient(backendServerPort);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
      String action;
      try {
        action = HttpUtil.splitQuery(httpExchange.getRequestURI()).get("action");
      } catch (UnsupportedEncodingException e) {
        httpExchange.sendResponseHeaders(500, 0);
        httpExchange.getResponseBody().close();
        return;
      }
      HttpResult result = httpClient.sendGet(BACKEND_PATH + "?action=" + action);
      httpExchange.sendResponseHeaders(
          result.getHttpResponseCode(), result.getHttpResponseContent().length());
      try (OutputStream os = httpExchange.getResponseBody()) {
        os.write(result.getHttpResponseContent().getBytes(Charset.defaultCharset()));
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
    // TODO: #2
    final FrontEnd frontEnd = new FrontEnd();

    // Gracefully close the servers
    Runtime.getRuntime().addShutdownHook(new Thread(frontEnd::close));
  }
}
