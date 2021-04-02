package uwu.narumi.crasher.api.optimizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Optimizer {

  private static final List<Thread> threads = new ArrayList<>();
  private static Runnable stopAction;
  private static Class<?> clazz;
  private static long time = -1;
  private static boolean disabling;

  static {
    Executors.newSingleThreadScheduledExecutor()
        .scheduleWithFixedDelay(() -> {
          if (!disabling && time != -1 && time < System.currentTimeMillis()) {
            stopOptimizing();
            System.out.println("Cancelled crashing due too big delay between connections\n\r");
          }
        }, 0, 50, TimeUnit.MILLISECONDS);
  }

  public static void startOptimizing(Class<?> caller) {
    if (clazz != null) {
      throw new IllegalArgumentException("NO.");
    }
    clazz = caller;
  }

  public static void post(Runnable runnable, int amount) {
    for (int i = 0; i < amount; i++) {
      post(runnable);
    }
  }

  public static void postFor(Runnable runnable, long time) {
    Executors.newSingleThreadExecutor().submit(() -> {
      long forTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(time);
      while (!disabling && forTime > System.currentTimeMillis()) {
        post(runnable);
      }
    });
  }

  public static void postAndAfter(TimeUnit unit, long time, Runnable runnable, Runnable after) {
    try {
      post(runnable);
      unit.sleep(time);
      post(after);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void post(Runnable runnable) {
    if (!new Throwable().getStackTrace()[2].getClassName().equals(clazz.getName())) {
      throw new IllegalArgumentException("NO.");
    }

    if (!disabling) {
      Thread thread = new Thread(runnable);
      thread.start();
      threads.add(thread);
    }
  }

  public static void stopOptimizing() {
    disabling = true;
    for (Thread thread : threads) {
      thread.stop();
    }

    threads.clear();
    if (stopAction != null) {
      stopAction.run();
    }

    clazz = null;
    time = -1;
    stopAction = null;
    disabling = false;
  }

  public static void setStopAction(Runnable runnable) {
    if (!new Throwable().getStackTrace()[1].getClassName().equals(clazz.getName())) {
      throw new IllegalArgumentException("NO.");
    }

    stopAction = runnable;
  }

  public static void update() {
    time = System.currentTimeMillis() + 500;
  }
}
