package eu.byncing.buffer;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ByteBuffer implements IByteBuffer {

    private byte[] bytes;

    private int write, read;

    public ByteBuffer(byte[] bytes) {
        this.bytes = bytes;
        this.write = bytes.length;
    }

    public ByteBuffer() {
        this(new byte[0]);
    }

    @Override
    public void writeByte(byte value) {
        byte[] result = new byte[bytes.length + 1];

        result[write++] = value;

        for (int i = 0; i < result.length; i++) {
            if (i != write && i < bytes.length) result[i] = bytes[i];
        }

        this.bytes = result;
    }

    @Override
    public byte readByte() {
        if (read > bytes.length - 1) return 0;
        return bytes[read++];
    }

    @Override
    public void writeBytes(byte[] bytes) {
        writeInt(bytes.length);
        for (byte aByte : bytes) writeByte(aByte);
    }

    @Override
    public byte[] readBytes() {
        int length = readInt();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) bytes[i] = readByte();
        return bytes;
    }

    @Override
    public void writeInt(int value) {
        do {
            int part = value & 0x7F;
            value >>>= 7;
            if (value != 0) part |= 0x80;
            writeByte((byte) part);
        } while (value != 0);
    }

    @Override
    public int readInt() {
        int value = 0, bytes = 0;
        byte b;
        do {
            b = readByte();
            value |= (b & 0x7F) << (bytes++ * 7);
            if (bytes > 5) return value;
        } while ((b & 0x80) == 0x80);
        return value;
    }

    @Override
    public void writeShort(short value) {
        writeByte((byte) (((short) (value >> 7)) & ((short) 0x7f) | 0x80));
        writeByte((byte) ((value & ((short) 0x7f))));
    }

    @Override
    public short readShort() {
        return (short) (128 * ((byte) (readByte() & (byte) 0x7f)) + readByte());
    }

    @Override
    public void writeLong(long value) {
        for (int i = 7; i >= 0; i--) {
            writeByte((byte) (value & 0xFF));
            value >>= Long.BYTES;
        }
    }

    @Override
    public long readLong() {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= Long.BYTES;
            result |= (readByte() & 0xFF);
        }
        return result;
    }

    @Override
    public void writeString(String value) {
        if (value.length() < Short.MAX_VALUE) {
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            writeInt(bytes.length);
            for (byte aByte : bytes) writeInt(aByte);
        }
    }

    @Override
    public String readString() {
        int length = readInt();
        if (length < 1) return "";
        byte[] bytes = new byte[length];
        for (int i = 0; i < bytes.length; i++) bytes[i] = (byte) readInt();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public void writeChar(char value) {
        writeInt(value);
    }

    @Override
    public char readChar() {
        return (char) readInt();
    }

    @Override
    public void writeBoolean(boolean value) {
        writeInt(value ? 1 : 0);
    }

    @Override
    public boolean readBoolean() {
        int i = readInt();
        return i == 1;
    }

    @Override
    public void writeUUID(UUID value) {
        writeString(value.toString());
    }

    @Override
    public UUID readUUID() {
        String string = readString();
        if (string.isEmpty()) return null;
        return UUID.fromString(string);
    }

    @Override
    public ByteBuffer flip() {
        byte[] bytes = new byte[this.bytes.length];
        int i = 0;
        for (int i1 = this.bytes.length; i1 > 0; i1--) {
            bytes[i] = this.bytes[i1 - 1];
            i++;
        }
        return new ByteBuffer(bytes);
    }

    @Override
    public void reset() {
        write = 0;
        read = 0;
        bytes = new byte[0];
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean isReadable() {
        return read < bytes.length;
    }
}