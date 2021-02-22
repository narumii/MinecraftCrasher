package uwu.narumi.crasher.api.helper;

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
    }catch (Exception e) { }

    return null;
  }

  public static Packet createLoginStart(String nick) {
    try {
      NetOut netOut = new NetOut();
      netOut.writeString(nick);

      return Packet.builder().id(0x00).data(netOut).build();
    }catch (Exception e) { }

    return null;
  }

  public static Packet createChatPacket(String message) {
    try {
      NetOut netOut = new NetOut();
      netOut.writeString(message);

      return Packet.builder().id(0x00).compression(true).data(netOut).build();
    }catch (Exception e) { }

    return null;
  }
}
