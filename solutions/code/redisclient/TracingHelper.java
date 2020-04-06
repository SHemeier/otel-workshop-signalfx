package redisclient;

import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.trace.Span;
import io.opentelemetry.trace.Span.Kind;
import io.opentelemetry.trace.Status;
import io.opentelemetry.trace.Tracer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TracingHelper {
  private static final Tracer tracer =
      OpenTelemetry.getTracerProvider().get("io.opentelemetry.plugin.Jedis");
  private static final String DB_TYPE = "redis";

  private static Span.Builder builder(String operationName) {
    return tracer
        .spanBuilder(operationName)
        .setSpanKind(Kind.CLIENT)
        .setAttribute("component", "java-redis")
        .setAttribute("db.type", DB_TYPE);
  }

  static Span buildSpan(String operationName, byte[][] keys) {
    return builder(operationName).setAttribute("keys", toString(keys)).startSpan();
  }

  static Span buildSpan(String operationName) {
    return builder(operationName).startSpan();
  }

  static Span buildSpan(String operationName, Object key) {
    return builder(operationName).setAttribute("key", nullable(key)).startSpan();
  }

  static Span buildSpan(String operationName, byte[] key) {
    return builder(operationName).setAttribute("key", Arrays.toString(key)).startSpan();
  }

  static Span buildSpan(String operationName, Object[] keys) {
    return builder(operationName).setAttribute("keys", Arrays.toString(keys)).startSpan();
  }

  static void onError(Throwable throwable, Span span) {
    if (throwable != null) {
      span.setStatus(Status.UNKNOWN.withDescription(throwable.getMessage()));
    } else {
      span.setStatus(Status.UNKNOWN);
    }
  }

  static String nullable(Object object) {
    if (object == null) {
      return "";
    }
    return object.toString();
  }

  static <V> String toString(Map<String, V> map) {
    List<String> list = new ArrayList<>();
    if (map != null) {
      for (Entry<String, V> entry : map.entrySet()) {
        list.add(entry.getKey() + "=" + entry.getValue());
      }
    }

    return "{" + String.join(", ", list) + "}";
  }

  static String toString(byte[][] array) {
    if (array == null) {
      return "null";
    }

    List<String> list = new ArrayList<>();

    for (byte[] bytes : array) {
      list.add(Arrays.toString(bytes));
    }

    return "[" + String.join(", ", list) + "]";
  }

  static String toString(Collection<byte[]> collection) {
    if (collection == null) {
      return "null";
    }
    List<String> list = new ArrayList<>();

    for (byte[] bytes : collection) {
      list.add(Arrays.toString(bytes));
    }

    return "[" + String.join(", ", list) + "]";
  }

  static String toString(List<String> list) {
    if (list == null) {
      return "null";
    }

    return String.join(", ", list);
  }

  static String toStringMapBytes(Map<byte[], byte[]> map) {
    List<String> list = new ArrayList<>();
    if (map != null) {
      for (Entry<byte[], byte[]> entry : map.entrySet()) {
        list.add(Arrays.toString(entry.getKey()) + "=" + Arrays.toString(entry.getValue()));
      }
    }

    return "{" + String.join(", ", list) + "}";
  }

  static <V> String toStringMapGeneric(Map<byte[], V> map) {
    List<String> list = new ArrayList<>();
    if (map != null) {
      for (Entry<byte[], V> entry : map.entrySet()) {
        list.add(Arrays.toString(entry.getKey()) + "=" + entry.getValue());
      }
    }

    return "{" + String.join(", ", list) + "}";
  }

  private TracingHelper() {}
}
