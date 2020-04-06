package httputil;

import io.opentelemetry.trace.Status;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public final class HttpUtil {
  private static final Status STATUS_100 = Status.UNKNOWN.withDescription("Continue");
  private static final Status STATUS_101 = Status.UNKNOWN.withDescription("Switching Protocols");
  private static final Status STATUS_402 = Status.UNKNOWN.withDescription("Payment Required");
  private static final Status STATUS_405 = Status.UNKNOWN.withDescription("Method Not Allowed");
  private static final Status STATUS_406 = Status.UNKNOWN.withDescription("Not Acceptable");
  private static final Status STATUS_407 =
      Status.UNKNOWN.withDescription("Proxy Authentication Required");
  private static final Status STATUS_408 = Status.UNKNOWN.withDescription("Request Time-out");
  private static final Status STATUS_409 = Status.UNKNOWN.withDescription("Conflict");
  private static final Status STATUS_410 = Status.UNKNOWN.withDescription("Gone");
  private static final Status STATUS_411 = Status.UNKNOWN.withDescription("Length Required");
  private static final Status STATUS_412 = Status.UNKNOWN.withDescription("Precondition Failed");
  private static final Status STATUS_413 =
      Status.UNKNOWN.withDescription("Request Entity Too Large");
  private static final Status STATUS_414 = Status.UNKNOWN.withDescription("Request-URI Too Large");
  private static final Status STATUS_415 = Status.UNKNOWN.withDescription("Unsupported Media Type");
  private static final Status STATUS_416 =
      Status.UNKNOWN.withDescription("Requested range not satisfiable");
  private static final Status STATUS_417 = Status.UNKNOWN.withDescription("Expectation Failed");
  private static final Status STATUS_502 = Status.UNKNOWN.withDescription("Bad Gateway");
  private static final Status STATUS_505 =
      Status.UNKNOWN.withDescription("HTTP Version not supported");

  private HttpUtil() {}

  /**
   * Parse OpenTelemetry Status from HTTP response status code.
   *
   * @param statusCode the HTTP response status code. {@code 0} means invalid response.
   * @param error the error occurred during response transmission (optional).
   * @return the corresponding OpenTelemetry {@code Status}.
   */
  public static final Status parseResponseStatus(int statusCode, Throwable error) {
    String message = null;

    if (error != null) {
      message = error.getMessage();
      if (message == null) {
        message = error.getClass().getSimpleName();
      }
    }

    // set status according to response
    if (statusCode == 0) {
      return Status.UNKNOWN.withDescription(message);
    } else {
      if (statusCode >= 200 && statusCode < 400) {
        return Status.OK;
      } else {
        // error code, try parse it
        switch (statusCode) {
          case 100:
            return STATUS_100;
          case 101:
            return STATUS_101;
          case 400:
            return Status.INVALID_ARGUMENT.withDescription(message);
          case 401:
            return Status.UNAUTHENTICATED.withDescription(message);
          case 402:
            return STATUS_402;
          case 403:
            return Status.PERMISSION_DENIED.withDescription(message);
          case 404:
            return Status.NOT_FOUND.withDescription(message);
          case 405:
            return STATUS_405;
          case 406:
            return STATUS_406;
          case 407:
            return STATUS_407;
          case 408:
            return STATUS_408;
          case 409:
            return STATUS_409;
          case 410:
            return STATUS_410;
          case 411:
            return STATUS_411;
          case 412:
            return STATUS_412;
          case 413:
            return STATUS_413;
          case 414:
            return STATUS_414;
          case 415:
            return STATUS_415;
          case 416:
            return STATUS_416;
          case 417:
            return STATUS_417;
          case 429:
            return Status.RESOURCE_EXHAUSTED.withDescription(message);
          case 500:
            return Status.INTERNAL.withDescription(message);
          case 501:
            return Status.UNIMPLEMENTED.withDescription(message);
          case 502:
            return STATUS_502;
          case 503:
            return Status.UNAVAILABLE.withDescription(message);
          case 504:
            return Status.DEADLINE_EXCEEDED.withDescription(message);
          case 505:
            return STATUS_505;
          default:
            return Status.UNKNOWN.withDescription(message);
        }
      }
    }
  }

  public static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {
    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
    String query = uri.getQuery();
    String[] pairs = query.split("&");
    for (String pair : pairs) {
      int idx = pair.indexOf("=");
      query_pairs.put(
          URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
          URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
    }
    return query_pairs;
  }
}