package uwu.narumi.crasher.api.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class UrlHelper {

  private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

  public static List<String> getListFromSite(String site) {
    List<String> strings = new ArrayList<>();
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) new URL(site).openConnection();
      connection.setRequestProperty("User-Agent", USER_AGENT);

      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(connection.getInputStream()));
      strings = bufferedReader.lines().collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }

    return strings;
  }

  public static URLConnection createConnection(String site, Proxy proxy) {
    try {
      URLConnection connection = new URL(site).openConnection(proxy);
      connection.setRequestProperty("User-Agent", USER_AGENT);
      return connection;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
