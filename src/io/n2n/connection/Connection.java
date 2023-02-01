package io.n2n.connection;

import io.n2n.node.NodeInfo;
import io.n2n.socket.N2NSocket;
import io.n2n.socket.SocketFactory;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * A connection that has a socket and data which will be sent or received already.
 * Doesn't contain sender's info, but only sender's
 */
public class Connection implements AutoCloseable {
    private NodeInfo receiver;
    private Message msg;
    private N2NSocket socket;

    public Connection(NodeInfo receiver) throws IOException, UnknownHostException {
        this(receiver, null);
    }

    public Connection(NodeInfo receiver, Message msg) throws IOException, UnknownHostException {
        this(receiver, msg, SocketFactory.create(receiver.getHost(), receiver.getPort()));
    }

    public Connection(NodeInfo receiver, Message msg, N2NSocket socket) {
        this.msg = msg;
        this.receiver = receiver;
        this.socket = socket;
    }

    public void send() {
        if (msg == null) {
            System.out.println("Can not send a null message");
            return;
        }

        try {
            this.socket.write(this.msg.toBytes());
        } catch (IOException e) {
            System.out.println("Error while sending a message: " + e);
        }
    }

    public Message receive() {
        try {
            return new Message(this.socket);
        } catch (IOException e) {
            System.out.println("Error while receiving a reply: " + e);
            return null;
        }
    }

    /**
     * Constructs a connection and extract message from the socket.<br>
     * Use {@link #getMsg()} to get message extracted from the socket.
     *
     * @param socket the socket sent by other node
     * @return the connection that has a message only received from the socket
     */
    public static Connection fromSocket(N2NSocket socket) {
        try {
            return new Connection(null, new Message(socket));
        } catch (IOException e) {
            System.out.println("Error while reading data from the socket: " + e);
            return null;
        }
    }

    public NodeInfo getReceiver() {
        return receiver;
    }

    public void setReceiver(NodeInfo receiver) {
        this.receiver = receiver;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public N2NSocket getSocket() {
        return socket;
    }

    public void setSocket(N2NSocket socket) {
        this.socket = socket;
    }

    /**
     * Supports {@link AutoCloseable}
     *
     * @throws IOException
     * @see AutoCloseable#close()
     */
    @Override
    public void close() throws IOException {
        if (this.socket != null) {
            this.socket.close();
        }
    }
}
