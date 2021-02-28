package uwu.narumi.crasher;

import java.io.IOException;
import java.net.Proxy;
import uwu.narumi.crasher.api.helper.ProxyHelper;
import uwu.narumi.crasher.core.Crasher;

public class Bootstrap {

  public static void main(String[] args) throws IOException {
    ProxyHelper.createSocket(Proxy.NO_PROXY); //DISABLE ILLEGAL REFLECTIVE WARNING IN FIRST
    Crasher.INSTANCE.init();
  }
}
