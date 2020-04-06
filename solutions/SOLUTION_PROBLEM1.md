# Fix Context propagation in the BackEnd application.

```java
public final class BackEnd implements AutoCloseable {
  // ...

  private static final class Handler implements HttpHandler {
    // ...

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
      // ...
      executor.submit(Context.current().wrap(/* ... */));
      // ...
    }
  }
}
```