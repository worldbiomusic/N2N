package io.n2n.connection;

import io.n2n.node.NodeInfo;
import io.n2n.socket.N2NSocket;

/**
 * A connection that has a socket and data which will be sent or received already.
 * Doesn't contain sender's info
 */
public class Connection {
    private Message msg;
    private NodeInfo receiver;
    private N2NSocket socket;

    public Connection(Message msg, NodeInfo receiver, N2NSocket socket) {
        this.msg = msg;
        this.receiver = receiver;
        this.socket = socket;
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

    public void setReceiver(NodeInfo receiver) {
        this.receiver = receiver;
    }

    public N2NSocket getSocket() {
        return socket;
    }

    public void setSocket(N2NSocket socket) {
        this.socket = socket;
    }
}
