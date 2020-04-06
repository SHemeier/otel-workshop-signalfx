package otelutil;

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

  public static void setupTraceExporter() {
    TracerSdkProvider tracerSdkProvider = OpenTelemetrySdk.getTracerProvider();

    SpanExporter spanExporter =
        MultiSpanExporter.create(
            Arrays.asList(
                new LoggingSpanExporter(),
                OtlpGrpcSpanExporter.newBuilder()
                    .setChannel(
                        ManagedChannelBuilder.forTarget("localhost:55680").usePlaintext().build())
                    .build()));
    tracerSdkProvider.addSpanProcessor(
        BatchSpansProcessor.newBuilder(spanExporter)
            .setScheduleDelayMillis(TimeUnit.SECONDS.toMillis(5))
            .build());
  }

  private OtelUtil() {}
}
