package uwu.narumi.crasher.api.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NetOut extends DataOutputStream {

  public NetOut() {
    this(new ByteArrayOutputStream());
  }

  public NetOut(OutputStream out) {
    super(out);
  }

  public void writeVarInt(int value) throws IOException {
    do {
      byte temp = (byte) (value & 0b01111111);
      value >>>= 7;
      if (value != 0) {
        temp |= 0b10000000;
      }
      writeByte(temp);
    } while (value != 0);
  }

  public void writeByteArray(byte[] bytes) throws IOException {
    writeVarInt(bytes.length);
    write(bytes);
  }

  public void writeString(String string) throws IOException {
    writeByteArray(string.getBytes(StandardCharsets.UTF_8));
  }

  public byte[] toByteArray() throws IOException {
    if (!(out instanceof ByteArrayOutputStream))
      throw new IOException(String.format("OutputSteam must be ByteArrayOutputStream, can't parse %s to byte array", out.getClass().getSimpleName()));

    return ((ByteArrayOutputStream) out).toByteArray();
  }
}
