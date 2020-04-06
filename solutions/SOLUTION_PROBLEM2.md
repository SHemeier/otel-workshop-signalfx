# Add support for `action=set` in the BackEnd application.

```java
public final class BackEnd implements AutoCloseable {
  // ...

  private static final class Handler implements HttpHandler {
    // ...

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
      // ...
      Future<String> future =
          executor.submit(
              Context.current()
                  .wrap(
                      () -> {
                        Jedis jedis = new TracingJedisWrapper("localhost");
                        if ("increment".equals(action)) {
                          return jedis.incr(REDIS_KEY).toString();
                        }
                        if ("decrement".equals(action)) {
                          return jedis.decr(REDIS_KEY).toString();
                        }
                        if ("get".equals(action)) {
                          return jedis.get(REDIS_KEY);
                        }
                        if ("set".equals(action)) {
                          return jedis.set(REDIS_KEY, "42");
                        }
                        return null;
                      }));
      // ...
    }
  }
}
```