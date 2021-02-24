package uwu.narumi.crasher.api.helper;

import java.util.HashMap;
import java.util.Map;

public class ProtocolHelper {

  private static final Map<String, Integer> protocols = new HashMap<>() {{
    put("1.7", 5);
    put("1.8", 47);
    put("1.9", 109);
    put("1.10", 210);
    put("1.11", 316);
    put("1.12", 340);
    put("1.13", 404);
    put("1.14", 485);
    put("1.15", 578);
    put("1.16", 751);
    //put("1.17", -1);
  }};

  public static int getProtocol(Object name) {
    return protocols.entrySet().stream()
        .filter(entry -> entry.getKey().startsWith((String) name))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Invalid minecraft version"))
        .getValue();
  }

  public static int getProtocol(String name) {
    return protocols.entrySet().stream()
        .filter(entry -> entry.getKey().startsWith(name))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Invalid minecraft version"))
        .getValue();
  }
}
