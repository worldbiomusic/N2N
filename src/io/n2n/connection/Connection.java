package io.n2n.connection;

import io.n2n.node.NodeInfo;
import io.n2n.socket.N2NSocket;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * A connection that has a socket and data which will be sent or received already.
 * Doesn't contain sender's info
 */
public class Connection {
    private NodeInfo receiver;
    private Message msg;
    private N2NSocket socket;

    public Connection(NodeInfo receiver) throws IOException, UnknownHostException {
        this(receiver, null);
    }

    public Connection(NodeInfo receiver, Message msg) throws IOException, UnknownHostException {
        this(receiver, msg, null);
    }

    public Connection(NodeInfo receiver, Message msg, N2NSocket socket) {
        this.msg = msg;
        this.receiver = receiver;
        this.socket = socket;
    }

    public void send() {
        if (msg == null) {
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

    public void close() {
        if (this.socket == null) {
            return;
        }

        try {
            this.socket.close();
        } catch (IOException e) {
            System.out.println("Error while closing a socket: " + e);
        }
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public NodeInfo getReceiver() {
        return receiver;
    }

    public N2NSocket getSocket() {
        return socket;
    }

}
