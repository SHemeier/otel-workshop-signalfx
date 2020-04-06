package loadgenerator;

import static frontend.FrontEnd.FRONTEND_PATH;

import httpclient.HttpClient;
import httpclient.HttpResult;
import httputil.HttpUtil;
import io.github.cdimascio.dotenv.Dotenv;
import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.context.Scope;
import io.opentelemetry.trace.Span;
import io.opentelemetry.trace.Span.Kind;
import io.opentelemetry.trace.Tracer;
import io.opentelemetry.trace.TracingContextUtils;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import otelutil.OtelUtil;

public final class LoadGenerator implements AutoCloseable {
  private static final Tracer tracer =
      OpenTelemetry.getTracerProvider().get(LoadGenerator.class.getName());
  private static final String[] operations = {
    "action=increment", "action=decrement", "action=get", "action=set"
  };

  private final Timer timer;

  private LoadGenerator() {
    Dotenv dotenv = Dotenv.load();
    int frontendServerPort = Integer.parseInt(dotenv.get("FRONTEND_SERVER_PORT"));
    timer = new Timer();
    timer.schedule(
        new RequestSender(frontendServerPort),
        TimeUnit.SECONDS.toMillis(1),
        TimeUnit.SECONDS.toMillis(1));
  }

  @Override
  public void close() {
    timer.cancel();
  }

  private static final class RequestSender extends TimerTask {
    private final HttpClient httpClient;
    private final AtomicInteger requestCount;
    private final Random random;

    private RequestSender(int frontendServerPort) {
      this.httpClient = new HttpClient(frontendServerPort);
      requestCount = new AtomicInteger();
      random = new Random();
    }

    @Override
    public void run() {
      int currentRequest = requestCount.getAndAdd(1);
      String operation = operations[Math.floorMod(random.nextInt(), operations.length)];
      Span span =
          tracer
              .spanBuilder(operation)
              .setNoParent()
              .setSpanKind(Kind.INTERNAL)
              .setAttribute("request_count", currentRequest)
              .startSpan();
      try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
        HttpResult result = httpClient.sendGet(FRONTEND_PATH + "?" + operation);
        span.setStatus(HttpUtil.parseResponseStatus(result.getHttpResponseCode(), null));
      } finally {
        span.end();
      }
    }
  }

  /**
   * Main method to run the example.
   *
   * @param args It is not required.
   */
  public static void main(String[] args) {
    OtelUtil.setupTraceExporter();
    final LoadGenerator loadGenerator = new LoadGenerator();

    // Gracefully close the servers
    Runtime.getRuntime().addShutdownHook(new Thread(loadGenerator::close));
  }
}
