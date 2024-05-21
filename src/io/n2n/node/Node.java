package io.n2n.node;

import io.n2n.connection.Connection;
import io.n2n.connection.Message;
import io.n2n.router.Router;
import io.n2n.stabilizer.StabilizerRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A primary class for N2N(Node to Node) system. It has node's information, dispatch
 * connections using handlers, manage neighbors, stabilize the network and
 * has a router.
 */
public class Node {
    private NodeInfo info;
    private NodeSettings settings;

    public Node(NodeInfo info) {
        this.info = info;
        this.settings = new NodeSettings();
    }

    /**
     * Connects to the node with given id using the registered router and send a message(data) and
     * gets replies.
     *
     * @param id  the node's id
     * @param msg the message to send
     * @return replies filtered by given reply filter
     */
    public List<Message> sendData(String id, Message msg) {
        return sendData(id, msg, true);
    }

    /**
     * Connects to the node with given id using the registered router and send a message(data) and
     * gets replies.
     *
     * @param id        the node's id
     * @param msg       the message to send
     * @param waitReply whether to wait for replies
     * @return replies filtered by given reply filter
     */
    public List<Message> sendData(String id, Message msg, boolean waitReply) {
        Router router = this.settings.getRouter();
        if (router == null) {
            System.out.println("No router is registered to the node.");
            return new ArrayList<>();
        }

        NodeInfo receiver = router.route(id);
        if (receiver == null) {
            System.out.println("Can't find the node with id: " + id);
            return new ArrayList<>();
        }

        return sendData(receiver, msg, waitReply);
    }

    /**
     * Connects to the specific node and sends a message(data) and gets replies.
     *
     * @param node the receiver
     * @param msg  the message to send
     * @return list of replies (may be empty if error occurred)
     */
    public List<Message> sendData(NodeInfo node, Message msg) {
        return sendData(node, msg, true);
    }

    /**
     * Connects to the specific node and sends a message(data) and gets replies.
     *
     * @param node      the receiver
     * @param msg       the message to send
     * @param waitReply whether to wait for replies
     * @return list of replies (may be empty if error occurred)
     */
    public List<Message> sendData(NodeInfo node, Message msg, boolean waitReply) {
        List<Message> replies = new ArrayList<>();

        // create a connection
        try (Connection connection = new Connection(node)) {
            connection.sendData(msg);

            // get replies if want
            if (waitReply) {
                // receive replies from the receiver
                Message reply = connection.receiveData();
                while (reply != null) {
                    replies.add(reply);
                    reply = connection.receiveData();
                }
            }
        } catch (IOException e) {
            System.out.println("Error while sending data to " + node + ": " + e);
            e.printStackTrace();
        }
        return replies;
    }

    /**
     * Starts a new thread that is running for accepting incoming
     * connection after opened a server socket and dispatches them
     * to the registered handlers through the dispatcher.
     */
    public void startListeningSocket() {
        new SocketListener(this).start();
    }

    /**
     * Starts the registered stabilizer at specific intervals.
     *
     * @param delay the delay (in milliseconds)
     */
    public void startStabilizer(int delay) {
        new StabilizerRunner(this, this.settings.getStabilizer(), delay).start();
    }

    /**
     * Finds the node with given node id using registered the router.
     *
     * @param id the node's id
     * @return NodeInfo if found or null
     */
    public NodeInfo routeNode(String id) {
        return this.getSettings().getRouter().route(id);
    }

    public NodeInfo getInfo() {
        return info;
    }

    public NodeSettings getSettings() {
        return settings;
    }

    @Override
    public String toString() {
        return "Node{" +
                "info=" + info +
                ", settings=" + settings +
                '}';
    }
}
