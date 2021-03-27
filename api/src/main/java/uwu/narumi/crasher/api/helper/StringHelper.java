package uwu.narumi.crasher.api.helper;

import java.util.concurrent.ThreadLocalRandom;

public final class StringHelper {

  private static final char[] CHARS = "aAbBcCd_DeEfFgGhHiIjJkKlLmMnNoOpPqQ_rRsStTuUvVwWxXyY_zZ00123456789"
      .toCharArray();

  public static String getRandomString(final int length) {
    final StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      stringBuilder.append(CHARS[ThreadLocalRandom.current().nextInt(CHARS.length)]);
    }
    return stringBuilder.toString();
  }

  public static String getRandomUTF(final int size) {
    final char[] chars = new char[size];
    for (int i = 0; i < size; i++) {
      chars[i] = (char) (ThreadLocalRandom.current().nextInt(Short.MAX_VALUE) + 1337);
    }
    return new String(chars);
  }
}
