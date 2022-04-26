# buffer
stores data in bytes

### Get started
````gradle
repositories {
    maven {url('https://repo.byncing.eu/')}
}

dependencies {
    implementation('eu.byncing:buffer:1.0.0-SNAPSHOT')
}
````

````java
package eu.byncing.buffer;

import java.util.Arrays;

public class TestBuffer {

    public static void main(String[] args) {
        IByteBuffer buffer = new ByteBuffer();

        buffer.writeString("byncing");
        buffer.writeInt(19);

        System.out.println("raw: " + Arrays.toString(buffer.getBytes()));

        //flip the bytebuffer
        System.out.println("flip: " + Arrays.toString(buffer.flip().getBytes()));

        System.out.println("name: " + buffer.readString() + ", age: " + buffer.readInt());
    }
}
````
