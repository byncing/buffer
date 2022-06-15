package eu.byncing.buffer;

import java.nio.ByteBuffer;

public class NioBuf extends ByteBuf {

    public static final NioBuf EMPTY = new NioBuf(0, true);

    private ByteBuffer buffer;
    private final boolean direct;

    public NioBuf(ByteBuffer buffer) {
        this.buffer = buffer;
        this.direct = buffer.isDirect();
    }

    public NioBuf(int capacity, boolean direct) {
        this.buffer = direct ? ByteBuffer.allocateDirect(capacity) : ByteBuffer.allocate(capacity);
        this.direct = direct;
    }

    @Override
    public void enlarge(int capacity) {
        ByteBuffer byteBuffer = direct ? ByteBuffer.allocateDirect((capacity + capacity())) : ByteBuffer.allocate((capacity + capacity()));

        offset(0);

        byteBuffer.put(buffer);

        buffer.clear();
        buffer = byteBuffer;
    }

    @Override
    public void writeByte(byte value) {
        enlarge(1);
        buffer.put(value);
    }

    @Override
    public byte readByte() {
        return buffer.get();
    }

    @Override
    public void writeInt(int value) {
        enlarge(Integer.BYTES);
        buffer.putInt(value);
    }

    @Override
    public int readInt() {
        return buffer.getInt();
    }

    @Override
    public void writeBoolean(boolean value) {
        writeByte((byte) (value ? 1 : 0));
    }

    @Override
    public boolean readBoolean() {
        return buffer.get() == 1;
    }

    @Override
    public void writeShort(short value) {
        enlarge(Short.BYTES);
        buffer.putShort(value);
    }

    @Override
    public short readShort() {
        return buffer.getShort();
    }

    @Override
    public void writeLong(long value) {
        enlarge(Long.BYTES);
        buffer.putLong(value);
    }

    @Override
    public long readLong() {
        return buffer.getLong();
    }

    @Override
    public void writeFloat(float value) {
        enlarge(Float.BYTES);
        buffer.putFloat(value);
    }

    @Override
    public float readFloat() {
        return buffer.getFloat();
    }

    @Override
    public void writeDouble(double value) {
        enlarge(Double.BYTES);
        buffer.putDouble(value);
    }

    @Override
    public double readDouble() {
        return buffer.get();
    }

    @Override
    public void writeChar(char value) {
        enlarge(Character.BYTES);
        buffer.putChar(value);
    }

    @Override
    public char readChar() {
        return buffer.getChar();
    }

    @Override
    public void writeBuf(ByteBuf buf) {
        int readable = buf.readable();
        byte[] bytes = new byte[readable];
        buf.readBytes(bytes, 0, readable);
        enlarge(2);

        buffer.position(offset());
        buffer.put(bytes);
    }

    @Override
    public void writeBuf(ByteBuffer buf) {
        enlarge(buf.position());
        buf.position(0);

        buffer.position(offset());
        buffer.put(buf);
    }

    @Override
    public void readBytes(byte[] bytes, int offset, int length) {
        buffer.position(offset);
        buffer.get(bytes, offset, length);
    }

    @Override
    public void flush() {
        buffer.clear();
        buffer = new NioBuf(0, direct).buffer;
    }

    @Override
    public void reset() {
        buffer.flip().rewind();
    }

    @Override
    public int readable() {
        return buffer.remaining();
    }

    @Override
    public int offset() {
        return buffer.position();
    }

    @Override
    public void offset(int offset) {
        buffer.position(offset);
    }

    @Override
    public int capacity() {
        return buffer.capacity();
    }

    @Override
    public byte[] array() {
        return buffer.array();
    }

    public boolean isDirect() {
        return direct;
    }

    @Override
    public boolean isReadable() {
        return buffer.hasRemaining();
    }
}