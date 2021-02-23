package uwu.narumi.crasher.api.optimizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Optimizer {

  private static final List<Thread> threads = new ArrayList<>();
  private static Thread stopAction;
  private static Class<?> clazz;
  private static boolean choke;
  private static long time = -1;
  private static long chokeTime = 500;

  public static void startOptimizing(Class<?> caller) {
    if (clazz != null)
      throw new IllegalArgumentException("NO.");

    clazz = caller;
  }

  public static void post(Runnable runnable) {
    if (!new Throwable().getStackTrace()[1].getClassName().equals(clazz.getName()))
      throw new IllegalArgumentException("NO.");

    if (!choke) {
      Thread thread = new Thread(runnable);
      thread.start();
      threads.add(thread);
    }
  }

  public static void setStopAction(Runnable runnable) {
    if (!new Throwable().getStackTrace()[1].getClassName().equals(clazz.getName()))
      throw new IllegalArgumentException("NO.");

    stopAction = new Thread(runnable);
  }

  public static void setChokeTime(long time) {
    if (!new Throwable().getStackTrace()[1].getClassName().equals(clazz.getName()))
      throw new IllegalArgumentException("NO.");

    chokeTime = time;
  }

  public static void update() {
    time = System.currentTimeMillis() + chokeTime;
  }


  private static void stopOptimizing() {
    for (Thread thread : threads) {
      thread.stop();
    }

    choke = false;
    clazz = null;
    time = -1;
    stopAction = null;
    chokeTime = 500;
    threads.clear();

    if (stopAction != null)
      stopAction.start();
  }

  static {
    Executors.newSingleThreadScheduledExecutor()
        .scheduleWithFixedDelay(() -> {
          if (!choke && time != -1 && time < System.currentTimeMillis()) {
            choke = true;
            stopOptimizing();
            System.err.println("Cancelled crashing due too big delay between connections");
          }
        }, 0, 100, TimeUnit.MILLISECONDS);
  }
}
