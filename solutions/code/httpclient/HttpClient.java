package httpclient;

import httputil.HttpUtil;
import io.grpc.Context;
import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.HttpTextFormat;
import io.opentelemetry.trace.Span;
import io.opentelemetry.trace.Tracer;
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
  private static final Tracer tracer =
      OpenTelemetry.getTracerProvider().get("httpclient.HttpClient");

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
    Span span = tracer.spanBuilder(url.getPath()).setSpanKind(Span.Kind.CLIENT).startSpan();
    try (Scope ignored = tracer.withSpan(span)) {
      // Connect to the server locally
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

      span.setAttribute("component", "http");
      span.setAttribute("http.method", "GET");
      span.setAttribute("http.url", url.toString());

      // Inject the request with the current Context/Span.
      OpenTelemetry.getPropagators()
          .getHttpTextFormat()
          .inject(Context.current(), httpURLConnection, setter);

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
      span.setStatus(HttpUtil.parseResponseStatus(httpResponseCode, e));
    } finally {
      span.end();
    }

    return new HttpResult(httpResponseCode, httpResponseContent.toString());
  }
}
