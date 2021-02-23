package uwu.narumi.crasher.api.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import uwu.narumi.crasher.api.io.NetOut;

public class Packet {

  private final byte[] packetData;

  private Packet(byte[] data) {
    this.packetData = data;
  }

  public void send(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeInt(packetData.length);
    dataOutputStream.write(packetData);
  }

  public void send(DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(packetData.length);
    dataOutput.write(packetData);
  }

  public void send(NetOut netOut) throws IOException {
    netOut.writeByteArray(packetData);
  }

  public static PacketBuilder builder() {
    return new PacketBuilder();
  }

  public final static class PacketBuilder {

    private int id;
    private byte[] bytes;
    private boolean compression;

    private PacketBuilder() {}

    public PacketBuilder id(int id) {
      this.id = id;
      return this;
    }

    public PacketBuilder compression(boolean compression) {
      this.compression = compression;
      return this;
    }

    public PacketBuilder data(byte... data) {
      this.bytes = data;
      return this;
    }

    public PacketBuilder data(ByteArrayOutputStream out) {
      this.bytes = out.toByteArray();
      return this;
    }

    public PacketBuilder data(NetOut out) {
      try {
        this.bytes = out.toByteArray();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return this;
    }

    public Packet build() {
      try {
        NetOut netOut = new NetOut();
        netOut.writeVarInt(id);
        if (compression)
          netOut.writeVarInt(0);

        netOut.write(bytes);
        return new Packet(netOut.toByteArray());
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }
  }
}
