# Fix broken instrumentation in TracingJedisWrapper.

```java
public final class TracingJedisWrapper extends Jedis {
  // ...

  @Override
  public String get(String key) {
    Span span = TracingHelper.buildSpan("Redis.Get", key);
    try (Scope ignored = TracingContextUtils.currentContextWith(span)) {
      return super.get(key);
    } catch (Exception e) {
      TracingHelper.onError(e, span);
      throw e;
    } finally {
      span.end();
    }
  }

  // ...

}
```