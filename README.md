# buffer
stores data in bytes
````java
package eu.byncing.buffer;

public class TestBuffer {

    public static void main(String[] args) {
        IByteBuffer buffer = new ByteBuffer();

        buffer.writeString("byncing");
        buffer.writeInt(19);

        System.out.println("name: " + buffer.readString() + ", age: " + buffer.readInt());
    }
}
````
