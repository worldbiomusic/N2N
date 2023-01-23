package io.n2n.connection;

/**
 * A wrapper class of data which can be one of any types.
 * Provides methods for converting data to/from byte arrays.
 */
public class Message {
    private byte[] data;

    public Message(byte[] data) {
        this.data = data;
    }

    public Message(String data) {
        this.data = data.getBytes();
    }


}
