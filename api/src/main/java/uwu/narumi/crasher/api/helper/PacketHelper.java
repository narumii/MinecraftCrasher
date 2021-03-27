package uwu.narumi.crasher.api.helper;

import java.nio.charset.StandardCharsets;
import uwu.narumi.crasher.api.io.NetOut;
import uwu.narumi.crasher.api.packet.Packet;

public final class PacketHelper {

  public static Packet createHandshake(int protocol, String ip, short port, int state) {
    try {
      NetOut netOut = new NetOut();
      netOut.writeVarInt(protocol);
      netOut.writeString(ip);
      netOut.writeShort(port);
      netOut.writeVarInt(state);

      return Packet.builder().id(0x00).data(netOut).build();
    } catch (Exception e) {
    }

    return null;
  }

  public static Packet createLoginStart(String nick) {
    try {
      NetOut netOut = new NetOut();
      netOut.writeString(nick);

      return Packet.builder().id(0x00).data(netOut).build();
    } catch (Exception e) {
    }

    return null;
  }

  public static Packet createEncryptionResponse(String nick) {
    try {
      NetOut netOut = new NetOut();
      netOut.writeByteArray(BrokenHash.createNewSharedKey().getEncoded());
      netOut.writeByteArray(BrokenHash.hash(nick).getBytes(StandardCharsets.UTF_8));

      return Packet.builder().id(0x01).data(netOut).build();
    } catch (Exception e) {
    }

    return null;
  }

  public static Packet createPing() {
    try {
      NetOut netOut = new NetOut();
      netOut.writeLong(System.currentTimeMillis());

      return Packet.builder().id(0x01).data(netOut).build();
    } catch (Exception e) {
    }

    return null;
  }
}
