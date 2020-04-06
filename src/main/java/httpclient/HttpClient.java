package httpclient;

import io.opentelemetry.context.propagation.HttpTextFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public final class HttpClient {
  private static final Logger logger = Logger.getLogger(HttpClient.class.getName());
  // TODO: #4

  // Inject the span context into the request
  private static HttpTextFormat.Setter<HttpURLConnection> setter =
      (carrier, key, value) -> {
        if (carrier == null) {
          return;
        }
        carrier.setRequestProperty(key, value);
      };

  private final String address;

  public HttpClient(int port) {
    this.address = "http://127.0.0.1:" + port;
    logger.info("Client connect to: " + address);
  }

  public HttpResult sendGet(String path) {
    URL url;
    try {
      url = new URL(address + path);
    } catch (MalformedURLException e) {
      return new HttpResult(400, "Bad Request");
    }

    StringBuilder httpResponseContent = new StringBuilder();
    int httpResponseCode = 0;
    // TODO: #4
    try {
      // Connect to the server locally
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      // TODO: #4

      // Process the request
      httpURLConnection.setRequestMethod("GET");
      httpResponseCode = httpURLConnection.getResponseCode();
      BufferedReader in =
          new BufferedReader(
              new InputStreamReader(httpURLConnection.getInputStream(), Charset.defaultCharset()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        httpResponseContent.append(inputLine);
      }
      in.close();
    } catch (IOException e) {
      logger.info(e.getMessage());
      logger.info("httpResponseCode = " + httpResponseCode);
    }
    // TODO: #4

    return new HttpResult(httpResponseCode, httpResponseContent.toString());
  }
}
