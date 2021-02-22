package uwu.narumi.crasher;

import java.io.IOException;
import uwu.narumi.crasher.core.Crasher;

public class Bootstrap {

  public static void main(String[] args) throws IOException {
    Crasher.INSTANCE.init();
  }
}
