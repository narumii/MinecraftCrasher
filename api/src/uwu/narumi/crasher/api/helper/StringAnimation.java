package uwu.narumi.crasher.api.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StringAnimation {

  private final ExecutorService executorService = Executors.newSingleThreadExecutor();
  private final String text;
  private final String[] chars;
  private final long delay;

  public StringAnimation(String text, long delay, String... chars) {
    this.text = text;
    this.chars = chars;
    this.delay = delay;
  }

  public void start() {
    executorService.submit(() -> {
      try {
        for (String c : chars) {
          System.out.print(text + " " + c + " \r");
          Thread.sleep(delay);
        }
      } catch (Exception e) { }
    });
  }

  public void stop() {
    executorService.shutdown();
  }
}
