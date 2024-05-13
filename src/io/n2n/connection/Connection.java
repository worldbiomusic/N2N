package io.n2n.connection;

import io.n2n.node.NodeInfo;
import io.n2n.socket.N2NSocket;
import io.n2n.socket.SocketFactory;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * A connection that has a socket and data which will be sent or received already.
 * Doesn't contain sender's info, but only receiver's
 */
public class Connection implements AutoCloseable {
    private NodeInfo receiver;
    private N2NSocket socket;

    public Connection(NodeInfo receiver) throws IOException, UnknownHostException {
        this(receiver, SocketFactory.create(receiver.getHost(), receiver.getPort()));
    }

    public Connection(NodeInfo receiver, N2NSocket socket) {
        this.receiver = receiver;
        this.socket = socket;
    }

    public void sendData(Message msg) {
        if (msg == null) {
            System.out.println("Can not send a null message");
            return;
        }

        try {
            this.socket.write(msg.toBytes());
        } catch (IOException e) {
            System.out.println("Error while sending a message: " + e);
        }
    }

    public Message receiveData() {
        try {
            return new Message(this.socket);
        } catch (IOException e) {
            System.out.println("No more reply or error while receiving a reply: " + e);
            return null;
        }
    }

    public NodeInfo getReceiver() {
        return receiver;
    }

    public void setReceiver(NodeInfo receiver) {
        this.receiver = receiver;
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
