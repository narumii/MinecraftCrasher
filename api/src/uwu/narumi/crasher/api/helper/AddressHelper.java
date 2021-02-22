package uwu.narumi.crasher.api.helper;

import java.util.Hashtable;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public final class AddressHelper {

  public static McAddress resolve(String address) {
    if (address == null)
      return null;

    String[] split = address.split(":");
    if (split.length > 2)
      split = new String[] { address };

    String ip = split[0];
    int port = (split.length > 1) ? parseIntWithDefault(split[1]) : 25565;
    if (port == 25565) {
      String[] resolvedAddress = getServerAddress(ip);
      ip = resolvedAddress[0];
      port = parseIntWithDefault(resolvedAddress[1]);
    }
    return new McAddress(removeCode(ip), port);
  }

  private static String removeCode(String address) {
    return address.endsWith(".") ? address.substring(0, address.length() - 1) : address;
  }

  private static String[] getServerAddress(String address) {
    try {
      Class.forName("com.sun.jndi.dns.DnsContextFactory");
      Hashtable<String, String> hashtable = new Hashtable<>();
      hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
      hashtable.put("java.naming.provider.url", "dns:");
      hashtable.put("com.sun.jndi.dns.timeout.retries", "1");
      InitialDirContext context = new InitialDirContext(hashtable);
      Attributes attributes = context.getAttributes("_minecraft._tcp." + address, new String[] { "SRV" });
      String[] srv = attributes.get("srv").get().toString().split(" ", 4);
      return new String[] { srv[3], srv[2] };
    }
    catch (Throwable throwable) { return new String[] { address, Integer.toString(25565) }; }
  }

  private static int parseIntWithDefault(String address) {
    try { return Integer.parseInt(address.trim()); }
    catch (Exception e) { return 25565; }
  }

  public static final class McAddress {
    private final String ip;
    private final int port;

    public McAddress(String ip, int port) {
      this.ip = ip;
      this.port = port;
    }

    public String getIp() {
      return ip;
    }

    public int getPort() {
      return port;
    }

    @Override
    public String toString() {
      return ip + ":" + port;
    }
  }
}
