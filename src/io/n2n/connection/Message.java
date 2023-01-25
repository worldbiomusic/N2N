package io.n2n.connection;

import io.n2n.exception.socket.SocketDataLengthException;
import io.n2n.socket.N2NSocket;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

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

    public Message(N2NSocket socket) throws IOException, SocketDataLengthException {
        byte[] dataLengthBytes = new byte[4];

        // read first 4 bytes to get data length
        if (socket.read(dataLengthBytes) != 4) {
            throw new SocketDataLengthException("Number of data length bytes is not 4");
        }

        int dataLength = new BigInteger(dataLengthBytes).intValue();
        this.data = new byte[dataLength];

        // read all the data
        if (socket.read(data) != dataLength) {
            throw new SocketDataLengthException("Data length is not " + dataLength);
        }
    }


    public byte[] getDataBytes() {
        return data.clone();
    }

    public String getData() {
        return String.valueOf(data);
    }

    public byte[] toBytes() {
        int dataLength = this.data.length;
        byte[] bytes = new byte[4 + dataLength];
        byte[] dataLengthBytes = ByteBuffer.allocate(4).putInt(dataLength).array();

        System.arraycopy(dataLengthBytes, 0, bytes, 0, dataLengthBytes.length);
        System.arraycopy(this.data, 0, bytes, 4, dataLength);

        return bytes;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data=" + getData() +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("hi");
        byte[] a = ByteBuffer.allocate(4).putInt(300).array();
        for (byte b : a) {
            System.out.print((byte) b + " ");
        }

    }
}
