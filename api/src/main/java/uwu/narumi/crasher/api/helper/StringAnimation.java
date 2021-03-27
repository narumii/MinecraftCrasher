package uwu.narumi.crasher.api.helper;

import java.util.concurrent.atomic.AtomicInteger;

public class StringAnimation {

  private final AtomicInteger index = new AtomicInteger();

  private Thread thread;
  private String text;
  private String[] chars;
  private long delay;

  public StringAnimation() {
  }

  public StringAnimation(String text, long delay, String... chars) {
    this.text = text;
    this.chars = chars;
    this.delay = delay;
  }

  public void start() {
    thread = new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(delay);
          System.out.print(text + " " + chars[get()] + " \r");
        } catch (Exception e) {
        }
      }
    });
    thread.start();
  }

  private int get() {
    if (index.get() >= chars.length) {
      index.set(0);
    }

    return index.getAndIncrement();
  }

  public void stop() {
    thread.stop();
    thread = null;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setChars(String... chars) {
    this.chars = chars;
  }

  public void setDelay(long delay) {
    this.delay = delay;
  }
}
