package eu.byncing.buffer;

import java.util.UUID;

public interface IByteBuffer {

    void writeByte(byte value);

    byte readByte();

    void writeBytes(byte[] value);

    byte[] readBytes();

    void writeInt(int value);

    int readInt();

    void writeShort(short value);

    short readShort();

    void writeLong(long value);

    long readLong();

    void writeString(String value);

    String readString();

    void writeChar(char value);

    char readChar();

    void writeBoolean(boolean value);

    boolean readBoolean();

    void writeUUID(UUID value);

    UUID readUUID();

    IByteBuffer flip();

    void reset();

    byte[] getBytes();

    boolean isReadable();
}