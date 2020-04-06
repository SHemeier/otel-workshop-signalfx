package httpclient;

public final class HttpResult {
  private final int httpResponseCode;
  private final String httpResponseContent;

  public HttpResult(int httpResponseCode, String httpResponseContent) {
    this.httpResponseCode = httpResponseCode;
    this.httpResponseContent = httpResponseContent;
  }

  public int getHttpResponseCode() {
    return httpResponseCode;
  }

  public String getHttpResponseContent() {
    return httpResponseContent;
  }
}
