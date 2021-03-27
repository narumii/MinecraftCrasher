package uwu.narumi.crasher.api.optimizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Optimizer {

  private static final List<Thread> threads = new ArrayList<>();
  private static Runnable stopAction;
  private static Class<?> clazz;

  private static long chokeTime = 500;
  private static long time = -1;

  private static boolean check = true;
  private static boolean disabling;
  private static boolean choke;

  static {
    Executors.newSingleThreadScheduledExecutor()
        .scheduleWithFixedDelay(() -> {
          if (!disabling && check && !choke && time != -1 && time < System.currentTimeMillis()) {
            choke = true;
            stopOptimizing();
            System.out.println("Cancelled crashing due too big delay between connections\n\r");
          }
        }, 0, 50, TimeUnit.MILLISECONDS);
  }

  public static void startOptimizing(Class<?> caller) {
    if (clazz != null) {
      throw new IllegalArgumentException("NO.");
    }

    check = true;
    clazz = caller;
  }

  public static void post(Runnable runnable) {
    if (!new Throwable().getStackTrace()[1].getClassName().equals(clazz.getName())) {
      throw new IllegalArgumentException("NO.");
    }

    post0(runnable);
  }

  public static void post(Runnable runnable, int amount) {
    if (!new Throwable().getStackTrace()[1].getClassName().equals(clazz.getName())) {
      throw new IllegalArgumentException("NO.");
    }

    for (int i = 0; i < amount; i++) {
      post0(runnable);
    }
  }

  public static void postFor(Runnable runnable, long time) {
    if (!new Throwable().getStackTrace()[1].getClassName().equals(clazz.getName())) {
      throw new IllegalArgumentException("NO.");
    }

    Executors.newSingleThreadExecutor().submit(() -> {
      long forTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(time);
      while (!choke && !disabling && forTime > System.currentTimeMillis()) {
        post0(runnable);
      }
    });
  }

  public static void postAndAfter(TimeUnit unit, long time, Runnable runnable, Runnable after) {
    if (!new Throwable().getStackTrace()[1].getClassName().equals(clazz.getName())) {
      throw new IllegalArgumentException("NO.");
    }

    try {
      post0(runnable);
      unit.sleep(time);
      post0(after);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void post0(Runnable runnable) {
    if (!choke && !disabling) {
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

    choke = false;
    clazz = null;
    time = -1;
    stopAction = null;
    chokeTime = 500;
    check = true;

    threads.clear();

    if (stopAction != null) {
      stopAction.run();
    }

    disabling = false;
  }

  public static void setStopAction(Runnable runnable) {
    if (!new Throwable().getStackTrace()[1].getClassName().equals(clazz.getName())) {
      throw new IllegalArgumentException("NO.");
    }

    stopAction = runnable;
  }

  public static void setChokeTime(long time) {
    if (!new Throwable().getStackTrace()[1].getClassName().equals(clazz.getName())) {
      throw new IllegalArgumentException("NO.");
    }

    chokeTime = time;
  }

  public static void update() {
    time = System.currentTimeMillis() + chokeTime;
  }

  public static void toggle() {
    check = !check;
  }
}
