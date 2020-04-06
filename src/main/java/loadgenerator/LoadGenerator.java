package loadgenerator;

import static frontend.FrontEnd.FRONTEND_PATH;

import httpclient.HttpClient;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class LoadGenerator implements AutoCloseable {
  private static final String[] operations = {
    "action=increment", "action=decrement", "action=get", "action=set"
  };
  // TODO: #3

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
      String operation = operations[Math.floorMod(random.nextInt(), operations.length)];

      // TODO: #3
      httpClient.sendGet(FRONTEND_PATH + "?" + operation);
    }
  }

  /**
   * Main method to run the example.
   *
   * @param args It is not required.
   */
  public static void main(String[] args) {
    // TODO: #2
    final LoadGenerator loadGenerator = new LoadGenerator();

    // Gracefully close the servers
    Runtime.getRuntime().addShutdownHook(new Thread(loadGenerator::close));
  }
}
