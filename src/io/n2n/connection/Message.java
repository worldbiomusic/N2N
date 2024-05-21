package io.n2n.connection;

import io.n2n.exception.socket.SocketFieldLengthException;
import io.n2n.socket.N2NSocket;
import io.n2n.util.Config;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * A wrapper class of data which can be one of any types.
 * Provides methods for converting data to/from byte arrays.
 */
public class Message {
    private byte[] type;
    private byte[] data;

    /**
     * Constructs a new node message.
     *
     * @param type the message type ({@link Config#MSG_TYPE_FIELD_LENGTH} bytes)
     * @param data the message type
     */
    public Message(byte[] type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    /**
     * Constructs a new node message with strings.<br>
     * Using {@link StandardCharsets#UTF_8}
     *
     * @param type the message type (4 characters)
     * @param data the message data
     */
    public Message(String type, String data) {
        this(type.getBytes(Config.CHARSET), data.getBytes(Config.CHARSET));
    }

    /**
     * Constructs a new node message by reading data from the given socket connection.<br>
     * Data format: type({@link Config#MSG_TYPE_FIELD_LENGTH} bytes) | length of data({@link Config#MSG_DATA_FIELD_LENGTH} bytes) | data (length of data)
     *
     * @param socket a socket connection
     * @throws IOException               if I/O error occurs
     * @throws SocketFieldLengthException if data length is incorrect while reading
     */
    public Message(N2NSocket socket) throws IOException, SocketFieldLengthException {
        // read first 4 bytes to get type of the message
        this.type = new byte[Config.MSG_TYPE_FIELD_LENGTH];
        if (socket.read(this.type) != Config.MSG_TYPE_FIELD_LENGTH) {
            throw new SocketFieldLengthException("Number of type bytes is not " + Config.MSG_TYPE_FIELD_LENGTH);
        }

        byte[] dataLengthBytes = new byte[Config.MSG_DATA_FIELD_LENGTH];
        // read 4 bytes to get data length
        if (socket.read(dataLengthBytes) != Config.MSG_DATA_FIELD_LENGTH) {
            throw new SocketFieldLengthException("Number of data length bytes is not " + Config.MSG_DATA_FIELD_LENGTH);
        }

        int dataLength = new BigInteger(dataLengthBytes).intValue();
        this.data = new byte[dataLength];
        // read remained data
        if (socket.read(data) != dataLength) {
            throw new SocketFieldLengthException("Data length is not " + dataLength);
        }
    }

    public byte[] getTypeBytes() {
        return this.type;
    }

    public String getType() {
        return new String(this.type, Config.CHARSET);
    }

    public byte[] getDataBytes() {
        return data.clone();
    }

    public String getData() {
        return new String(this.data, Config.CHARSET);
    }

    /**
     * Returns a packed representation of this message as an array of bytes.
     *
     * @return byte array of type and data with length
     */
    public byte[] toBytes() {
        int dataLength = this.data.length;
        byte[] bytes = new byte[Config.MSG_TYPE_FIELD_LENGTH + Config.MSG_DATA_FIELD_LENGTH + dataLength];
        byte[] dataLengthBytes = ByteBuffer.allocate(Config.MSG_DATA_FIELD_LENGTH).putInt(dataLength).array();

        System.arraycopy(this.type, 0, bytes, 0, Config.MSG_TYPE_FIELD_LENGTH);
        System.arraycopy(dataLengthBytes, 0, bytes, Config.MSG_TYPE_FIELD_LENGTH, Config.MSG_DATA_FIELD_LENGTH);
        System.arraycopy(this.data, 0, bytes, Config.MSG_TYPE_FIELD_LENGTH +
                Config.MSG_DATA_FIELD_LENGTH, dataLength);

        return bytes;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + getType() +
                "data=" + getData() +
                '}';
    }
}
