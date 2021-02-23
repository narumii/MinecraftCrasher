package uwu.narumi.crasher.api.helper;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Generates a broken Minecraft-style twos-complement signed
 * hex digest. Tested and confirmed to match vanilla.
 */
public class BrokenHash {

  public static String hash(String str) {
    try {
      byte[] digest = digest(str, "SHA-1");
      return new BigInteger(digest).toString(16);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private static byte[] digest(String str, String algorithm) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance(algorithm);
    byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
    return md.digest(strBytes);
  }

  public static SecretKey createNewSharedKey() {
    try {
      KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
      keygenerator.init(128);
      return keygenerator.generateKey();
    }
    catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}