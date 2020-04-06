package httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public final class HttpServer implements AutoCloseable {
  private static final Logger logger = Logger.getLogger(HttpServer.class.getName());
  // TODO: #4

  private final com.sun.net.httpserver.HttpServer httpServer;

  private HttpServer(com.sun.net.httpserver.HttpServer httpServer) {
    this.httpServer = httpServer;
    httpServer.start();
    logger.info("Server ready on port: " + httpServer.getAddress().getPort());
  }

  public static Builder newBuilder(int serverPort) throws IOException {
    return new Builder(serverPort);
  }

  @Override
  public void close() {
    httpServer.stop(0);
  }

  public static final class Builder {
    private final com.sun.net.httpserver.HttpServer server;

    private Builder(int serverPort) throws IOException {
      server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(serverPort), 0);
    }

    public Builder addHandler(String path, HttpHandler handler) {
      server.createContext(path, new HttpHandlerWrapper(handler));
      return this;
    }

    public HttpServer build() {
      return new HttpServer(server);
    }
  }

  private static final class HttpHandlerWrapper implements HttpHandler {
    private final HttpHandler wrappedHandler;

    private HttpHandlerWrapper(HttpHandler wrappedHandler) {
      this.wrappedHandler = wrappedHandler;
    }

    @Override
    public void handle(HttpExchange httpExchange) {
      // TODO: #4
      try {
        wrappedHandler.handle(httpExchange);
      } catch (IOException e) {
        logger.info(e.getMessage());
      }
    }
  }
}
