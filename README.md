# Welcome to the OpenTelemetry Java Workshop!

In this workshop, we will instrument a Java application. The application and workshop
steps can be found in `/src/main/java`. Have suggestions on how to improve this lab? PRs welcomed!

## Prerequisites
* Java 1.8.231
* `make`
* Docker
* `git clone https://github.com/signalfx/otel-workshop-signalfx.git`
* Be on the project root folder

## Make useful commands
* Run Redis Docker
  ```bash
  make run-redis
  ```
* Stop Redis Docker
  ```bash
  make stop-redis
  ```
* Run OpenTelemetry Collector Docker 
  ```bash
  make SIGNALFX_TOKEN="YOUR_TOKEN_HERE" run-otelcol
  ```
* Stop OpenTelemetry Collector Docker
  ```bash
  make stop-otelcol
  ```
* Run the Backend Application
  ```bash
  make run-backend
  ```
* Run the Frontend Application
  ```bash
  make run-frontend
  ```
* Run the Load Generator
  ```bash
  make run-loadgenerator
  ```
* Code Formatting
  ```bash
  make format-code
  ```

## Setting up the environment.

1. Configure the [OpenTelemetry Collector](https://github.com/open-telemetry/opentelemetry-collector)
- Go to `otelcol/otel-collector-config.yaml`
- Update the `attributes` processor to set your name as the `environment` key value.
```dtd
  attributes:
    actions:
      - key: "environment"
        value: "<insert-your-name-here>"
        action: insert
```
1. Start `otelcol` by calling `make SIGNALFX_TOKEN="YOUR_TOKEN_HERE" run-otelcol`.
1. Start redis by calling `make run-redis`.
1. Start the Backend by calling `make run-backend`.
1. Start the Frontend by calling `make run-frontend`.
1. Start the LoadGenerator by calling `make run-loadgenerator`.

### To validate correct set up of environment
#### OpenTelemetry Collector
Check the docker logs by running
``` docker logs --tail 100 otelcol_workshop```

You should see the following message that state the Collector has started successfully.
```dtd
{"level":"info","ts":1586919956.7714996,"caller":"builder/receivers_builder.go:73","msg":"Receiver is starting...","receiver":"otlp"}
{"level":"info","ts":1586919956.7717748,"caller":"builder/receivers_builder.go:78","msg":"Receiver started.","receiver":"otlp"}
{"level":"info","ts":1586919956.771825,"caller":"service/service.go:212","msg":"Everything is ready. Begin running and processing data."}

```

#### Redis Container
Check the docker logs by running
````docker container logs redis_workshop````

You should see no errors, like
```
 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
 # Redis version=5.0.8, bits=64, commit=00000000, modified=0, pid=1, just started
 # Warning: no config file specified, using the default config. In order to specify a config file use redis-server /path/to/redis.conf
 * Running mode=standalone, port=6379.
 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
 # Server initialized

```


#### Frontend
Send a request to the frontend using curl
```dtd
curl -v localhost:50001/frontend?action=get
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 50001 (#0)
> GET /frontend?action=get HTTP/1.1
> Host: localhost:50001
> User-Agent: curl/7.54.0
> Accept: */*
>
< HTTP/1.1 200 OK
< Date: Wed, 15 Apr 2020 03:21:37 GMT
< Content-length: 2
<
* Connection #0 to host localhost left intact
10
```
In your terminal, you will see the following message
```dtd
OTEL_RESOURCE_ATTRIBUTES="service.name=frontend" \
        java -cp ./build/libs/otel-workshop-all-0.1.0.jar frontend.FrontEnd
service.name=frontend
Apr 14, 2020 8:11:25 PM httpclient.HttpClient <init>
INFO: Client connect to: http://127.0.0.1:50000
Apr 14, 2020 8:11:25 PM httpserver.HttpServer <init>
INFO: Server ready on port: 50001
```

#### Backend
In your terminal, you will see the following message
```dtd
2 actionable tasks: 2 up-to-date
OTEL_RESOURCE_ATTRIBUTES="service.name=backend" \
        java -cp ./build/libs/otel-workshop-all-0.1.0.jar backend.BackEnd
service.name=backend
Apr 14, 2020 8:10:27 PM httpserver.HttpServer <init>
INFO: Server ready on port: 50000
```

#### Loadgenerator
In your terminal, you will see the following message
```dtd
OTEL_RESOURCE_ATTRIBUTES="service.name=loadgenerator" \
        java -cp ./build/libs/otel-workshop-all-0.1.0.jar loadgenerator.LoadGenerator
Apr 14, 2020 8:12:21 PM httpclient.HttpClient <init>
INFO: Client connect to: http://127.0.0.1:50001
```

Note: The response log line `500 for URL: http://127.0.0.1:50001/frontend?action=set` is expected until we get to the
problem solving of the workshop.


## Instrumenting Java Apps with OpenTelemetry

Your task is to instrument this application using [OpenTelemetry
Java](https://github.com/open-telemetry/opentelemetry-java).

### 1. Add the relevant dependencies and repositories to `build.gradle`.

```groovy
    compile("io.grpc:grpc-protobuf:1.28.0")
    compile("io.grpc:grpc-netty-shaded:1.28.0")
    // ...
    compile("io.opentelemetry:opentelemetry-api:${opentelemetryVersion}")
    compile("io.opentelemetry:opentelemetry-context-prop:${opentelemetryVersion}")
    compile("io.opentelemetry:opentelemetry-sdk:${opentelemetryVersion}")
    compile("io.opentelemetry:opentelemetry-exporters-logging:${opentelemetryVersion}")
    compile("io.opentelemetry:opentelemetry-exporters-otlp:${opentelemetryVersion}")
```

### 2. Initiate the library SDK, the exporters and invoke it during the Main class initializer.

**OtelUtil.java**
```java
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.exporters.logging.LoggingSpanExporter;
import io.opentelemetry.exporters.otlp.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.TracerSdkProvider;
import io.opentelemetry.sdk.trace.export.BatchSpansProcessor;
import io.opentelemetry.sdk.trace.export.MultiSpanExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public final class OtelUtil {
  // ...
  public static void setupTraceExporter() {
    TracerSdkProvider tracerSdkProvider = OpenTelemetrySdk.getTracerProvider();

    // In this workshop we are going to use Logging and OTLP exporters.
    SpanExporter spanExporter =
        MultiSpanExporter.create(
            Arrays.asList(
                new LoggingSpanExporter(),
                OtlpGrpcSpanExporter.newBuilder()
                    .setChannel(
                        ManagedChannelBuilder.forTarget("localhost:55680").usePlaintext().build())
                    .build()));
    // Use BatchSpansProcessor to offload exporting out of critical path.
    tracerSdkProvider.addSpanProcessor(
        BatchSpansProcessor.newBuilder(spanExporter)
            // Batch spans for 5 seconds then export them.
            .setScheduleDelayMillis(TimeUnit.SECONDS.toMillis(5))
            .build());
  }
  // ...
}
```

**LoadGenerator.java**
```java
import otelutil.OtelUtil;

public final class LoadGenerator implements AutoCloseable {
  // ...
  public static void main(String[] args) {
    OtelUtil.setupTraceExporter();
    // ...
  }
}
```

**FrontEnd.java**
```java
import otelutil.OtelUtil;

public final class FrontEnd implements AutoCloseable {
  // ...
  public static void main(String[] args) {
    OtelUtil.setupTraceExporter();
    // ...
  }
}
```

**BackEnd.java**
```java
import otelutil.OtelUtil;

public final class BackEnd implements AutoCloseable {
  // ...
  public static void main(String[] args) {
    OtelUtil.setupTraceExporter();
    // ...
  }
}
```

After this has been added, ensure compilation runs successfully by running ```make compile```

### 3. Create a `Span` for every operation in the Load-Generator.

This is important because we want to have visibility into end-2-end operations. After this step
 we will be able to see the first results in the logging console as well as in the APM backend.

```java
import httpclient.HttpResult;
import httputil.HttpUtil;
import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.context.Scope;
import io.opentelemetry.trace.Span;
import io.opentelemetry.trace.Span.Kind;
import io.opentelemetry.trace.Tracer;
import io.opentelemetry.trace.TracingContextUtils;

public final class LoadGenerator implements AutoCloseable {
  private static final Tracer tracer =
      OpenTelemetry.getTracerProvider().get(LoadGenerator.class.getName());
  // ...
  private static final class RequestSender extends TimerTask {
    // ...
    @Override
    public void run() {
      int currentRequest = requestCount.getAndAdd(1);
      String operation = operations[Math.floorMod(random.nextInt(), operations.length)];
      // Start a new Span with the name of the operation.
      Span span =
          tracer
              .spanBuilder(operation)
              .setNoParent()
              .setSpanKind(Kind.INTERNAL)
              .setAttribute("request_count", currentRequest)
              .startSpan();
      // Attach the newly created Span to the Context.
      // Use try-with-resources to ensure the previous Context is restored at the end.
      try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
        HttpResult result = httpClient.sendGet(FRONTEND_PATH + "?" + operation);
        span.setStatus(HttpUtil.parseResponseStatus(result.getHttpResponseCode(), null));
      } finally {
        // End the Span, we are done with this operation.
        span.end();
      }
      // ...
    }
  }
}
```

Ensure compilation is successful and restart the `loadgenerator` application.

### 4. Instrument HTTP Client util library.

Now we can see the first traces in APM backend, it will be good to have visibility in what
 happens downstream.

```java
import httputil.HttpUtil;
import io.grpc.Context;
import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.context.Scope;
import io.opentelemetry.trace.Span;


public final class HttpClient {
  private static final Logger logger = Logger.getLogger(HttpClient.class.getName());
  private static final Tracer tracer =
      OpenTelemetry.getTracerProvider().get("httpclient.HttpClient");

  // Setter that adds HTTP headers to the HttpURLConnection.
  private static HttpTextFormat.Setter<HttpURLConnection> setter =
      (carrier, key, value) -> {
        if (carrier == null) {
          return;
        }
        carrier.setRequestProperty(key, value);
      };

  // ...

  public HttpResult sendGet(String path) {
    // ...
    int httpResponseCode = 0;
    Span span = tracer.spanBuilder(url.getPath()).setSpanKind(Span.Kind.CLIENT).startSpan();
    try (Scope ignored = tracer.withSpan(span)) {
      // Set the Semantic Convention attributes.
      span.setAttribute("component", "http");
      span.setAttribute("http.method", "GET");
      span.setAttribute("http.url", url.toString());

      // Inject the request with the current Context/Span.
      OpenTelemetry.getPropagators()
          .getHttpTextFormat()
          .inject(Context.current(), httpURLConnection, setter);

      // ...

      span.setStatus(HttpUtil.parseResponseStatus(httpResponseCode, null));
    } catch (IOException e) {
      span.setStatus(HttpUtil.parseResponseStatus(httpResponseCode, e));
    } finally {
      span.end();
    }

    return new HttpResult(httpResponseCode, httpResponseContent.toString());
  }
}
```

### 5. Instrument HTTP Server util library.

After this step we will see almost end-2-end what is happening with our requests. You should see
in the APM backend 3 services LoadGenerator/FrontEnd/BackEnd and requests going through.

```java
import com.sun.net.httpserver.Headers;
import httputil.HttpUtil;
import io.grpc.Context;
import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.HttpTextFormat;
import io.opentelemetry.trace.Span;
import io.opentelemetry.trace.Tracer;
import io.opentelemetry.trace.TracingContextUtils;

public final class HttpServer implements AutoCloseable {
  private static final Logger logger = Logger.getLogger(HttpServer.class.getName());
  private static final Tracer tracer =
      OpenTelemetry.getTracerProvider().get(HttpServer.class.getName());
  // Getter that reads HTTP headers from HttpExchange.
  private static HttpTextFormat.Getter<HttpExchange> getter =
      (carrier, key) -> {
        Headers headers = carrier.getRequestHeaders();
        if (headers != null && headers.containsKey(key)) {
          return headers.get(key).get(0);
        }
        return null;
      };

  // ...

  private static final class HttpHandlerWrapper implements HttpHandler {
    // ...

    @Override
    public void handle(HttpExchange httpExchange) {
      // Extract the context from http headers
      Context context =
          OpenTelemetry.getPropagators()
              .getHttpTextFormat()
              .extract(Context.current(), httpExchange, getter);
      Span span =
          tracer
              .spanBuilder(httpExchange.getHttpContext().getPath())
              .setParent(TracingContextUtils.getSpan(context))
              .setSpanKind(Span.Kind.SERVER)
              .startSpan();

      // Set the Semantic Convention attributes.
      span.setAttribute("component", "http");
      span.setAttribute("http.method", httpExchange.getRequestMethod());
      span.setAttribute("http.scheme", httpExchange.getProtocol());
      span.setAttribute("http.host", "localhost:" + httpExchange.getLocalAddress().getPort());
      span.setAttribute("http.target", httpExchange.getRequestURI().toString());

      try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
        span.addEvent("Start Processing");
        wrappedHandler.handle(httpExchange);
        span.addEvent("Finish Processing");
        span.setStatus(HttpUtil.parseResponseStatus(httpExchange.getResponseCode(), null));
      } catch (IOException e) {
        span.setStatus(HttpUtil.parseResponseStatus(httpExchange.getResponseCode(), e));
      } finally {
        // Close the span
        span.end();
      }
    }
  }

}
```

### 6. Use instrumentation plugin for Jedis client.

Here we will use a provided library for Jedis client. Usually for all the major client libraries
OpenTelemetry will provide contrib packages that help with instrumentation.

```java
import redisclient.TracingJedisWrapper;

public final class BackEnd implements AutoCloseable {
  // ...

  private static final class Handler implements HttpHandler {
    // ...

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
      // ...
      Jedis jedis = new TracingJedisWrapper("localhost");
      // ...
    }
  }
}
```

## Problem Solving!
Once the above instructions have been followed to instrument the application, there are three issues in the code base.
Problem 1. Broken traces
Problem 2. Error in application code
Problem 3. Why is it so slow?


## Resources

- [OpenTelemetry Collector](https://github.com/open-telemetry/opentelemetry-collector)
- [OpenTelemetry Java](https://github.com/open-telemetry/opentelemetry-java)
- [OpenTelemetry Community](https://github.com/open-telemetry/community)
