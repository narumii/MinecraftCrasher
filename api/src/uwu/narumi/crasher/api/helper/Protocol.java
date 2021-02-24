package uwu.narumi.crasher.api.helper;

import java.util.Arrays;

public enum Protocol {

  v1_7("1.7", 5),
  v1_8("1.8", 47),
  v1_9("1.9", 109),
  v1_10("1.10", 210),
  v1_11("1.11", 316),
  v1_12("1.12", 340),
  v1_13("1.13", 404),
  v1_14("1.14", 485),
  v1_15("1.15", 578),
  v1_16("1.16", 751);

  private final String name;
  private final int id;

  Protocol(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static Protocol getByName(String name) {
    return Arrays.stream(values())
        .filter(protocol -> name.startsWith(protocol.getName()))
        .findFirst()
        .orElse(v1_8);
  }
}
