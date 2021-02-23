package uwu.narumi.crasher.api.helper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.SocketImpl;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class ProxyHelper {

  private static final List<Proxy> HTTP_PROXIES = new ArrayList<>();
  private static final List<Proxy> SOCKS_PROXIES = new ArrayList<>();
  private static final AtomicInteger HTTP_INDEX = new AtomicInteger();
  private static final AtomicInteger SOCKS_INDEX = new AtomicInteger();

  private static final String[] SOCKS_URLS = {
      "https://www.proxy-list.download/api/v1/get?type=socks4",
      "https://api.proxyscrape.com/?request=displayproxies&proxytype=socks4"
  };

  private static final String[] HTTP_URLS = {
      "https://www.proxy-list.download/api/v1/get?type=http",
      "https://api.proxyscrape.com/?request=displayproxies&proxytype=http",
      "https://www.proxy-list.download/api/v1/get?type=https",
      "https://api.proxyscrape.com/?request=displayproxies&proxytype=https"
  };
  
  public static void loadProxiesFromFile(Type type, File file) throws IOException {
    if (!file.exists())
      throw new IOException("Input file isn't found");

    System.out.printf("Loading %s proxies from file %s", type, file.getAbsoluteFile());
    Files.readAllLines(file.toPath()).stream()
        .filter(line -> !line.startsWith("#"))
        .filter(line -> line.contains(":"))
        .forEach(line -> {
          String[] split = line.trim().split(":");
          switch (type) {
            case HTTP:
              HTTP_PROXIES.add(new Proxy(type, new InetSocketAddress(split[0], Integer.parseInt(split[1]))));
              break;
            case SOCKS:
              SOCKS_PROXIES.add(new Proxy(type, new InetSocketAddress(split[0], Integer.parseInt(split[1]))));
              break;
            default:
              throw new IllegalArgumentException("NO");
          }
        });
    System.out.printf("Loaded %s %s proxies from file %s", (type == Type.SOCKS ? SOCKS_PROXIES.size() : HTTP_PROXIES.size()), type, file.getAbsoluteFile());
  }

  public static void loadProxies() throws IOException {
    System.out.println("Loading HTTP proxies from API");
    for (String url : HTTP_URLS) {
      UrlHelper.getListFromSite(url).stream()
          .filter(line -> !line.startsWith("#"))
          .filter(line -> line.contains(":"))
          .forEach(line -> {
            String[] split = line.trim().split(":");
            HTTP_PROXIES.add(
                new Proxy(Type.HTTP, new InetSocketAddress(split[0], Integer.parseInt(split[1]))));
          });
    }
    System.out.printf("Loaded %s HTTP proxies from API\n", HTTP_PROXIES.size());

    System.out.println("Loading SOCKS proxies from API");
    for (String url : SOCKS_URLS) {
      UrlHelper.getListFromSite(url).stream()
          .filter(line -> !line.startsWith("#"))
          .filter(line -> line.contains(":"))
          .forEach(line -> {
            String[] split = line.trim().split(":");
            SOCKS_PROXIES.add(new Proxy(Type.SOCKS, new InetSocketAddress(split[0], Integer.parseInt(split[1]))));
          });
    }
    System.out.printf("Loaded %s SOCKS proxies from API\n", SOCKS_PROXIES.size());
  }

  public static Socket createSocket(Proxy proxy) { //i love java so fuckin much
    try {
      Socket socket = new Socket(proxy);
      Class<?> clazz  = socket.getClass();
      Field field = clazz.getDeclaredField("impl");
      field.setAccessible(true);

      SocketImpl socketImpl  = (SocketImpl) field.get(socket);
      Class<?> clazzSocksImpl  =  socketImpl.getClass();
      Method method  = clazzSocksImpl.getDeclaredMethod("setV4");
      method.setAccessible(true);
      method.invoke(socketImpl);
      field.set(socket, socketImpl);
      return socket;
    } catch (Exception e) {
      return null;
    }
  }

  public static Proxy getHttp() {
    if (HTTP_INDEX.get() >= HTTP_PROXIES.size())
      HTTP_INDEX.set(0);

    return HTTP_PROXIES.get(HTTP_INDEX.getAndIncrement());
  }

  public static Proxy getSocks() {
    if (SOCKS_INDEX.get() >= SOCKS_PROXIES.size())
      SOCKS_INDEX.set(0);

    return SOCKS_PROXIES.get(SOCKS_INDEX.getAndIncrement());
  }

  public class VersionProxy {
    public int version;
    public Proxy proxy;

    public VersionProxy(int version, Proxy proxy) {
      this.version = version;
      this.proxy = proxy;
    }

    public int getVersion() {
      return version;
    }

    public Proxy getProxy() {
      return proxy;
    }
  }
}
