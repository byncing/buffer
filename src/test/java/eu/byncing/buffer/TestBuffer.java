package eu.byncing.buffer;

import java.util.Arrays;

public class TestBuffer {

    public static void main(String[] args) {
        NioBuf buf = new NioBuf(0, false);
        buf.writeBoolean(true);
        buf.writeInt(19);

        System.out.println(Arrays.toString(buf.array()));

        buf.reset();
        System.out.println(buf.readBoolean());
        System.out.println(buf.readInt());
    }
}