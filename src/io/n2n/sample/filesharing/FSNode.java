package io.n2n.sample.filesharing;

import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.node.NeighborManager;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.router.NormalRouter;
import io.n2n.sample.filesharing.handler.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File sharing node
 */
public class FSNode extends Node {
    private Map<String, NodeInfo> files;

    public FSNode(NodeInfo info) {
        super(info);

        // settings
        this.files = new ConcurrentHashMap<>();

        // handlers
        List<Handler> handlers = List.of(
                new NameHandler(this),
                new ListHandler(this),
                new JoinHandler(this),
                new QueryHandler(this),
                new ResponseHandler(this),
                new FileGetHandler(this),
                new QuitHandler(this)
        );
        handlers.forEach(this.getSettings().getDispatcher()::addHandler);

        // router
        this.getSettings().setRouter(new NormalRouter(this));
    }

    public Map<String, NodeInfo> getFiles() {
        return files;
    }

    public void buildNodes(String host, int port, int hops) {
        NeighborManager neighborManager = this.getSettings().getNeighborManager();

        if (neighborManager.isFull()) {
            System.out.println("Neighbor list is full" + " (max: " + neighborManager.getNodeCount() + ")");
            return;
        }

        System.out.println("Building nodes from " + host + " " + port + " (remained hops: " + hops + ")");

        // add new node
        NodeInfo newNode = new NodeInfo("", host, port);
        Message msg = new Message(MessageType.NAME.name(), "");
        Message reply = this.sendData(newNode, msg).stream().findFirst().get();
        String nodeId = reply.getData();
        newNode.setId(nodeId);

        // check if node already exists
        if (neighborManager.hasNode(nodeId)) {
            System.out.println("Node already exists: " + newNode);
        } else {
            neighborManager.addNode(newNode);
            System.out.println("New node added: " + newNode);
        }

        // list DFS to build more nodes
        List<Message> replies = this.sendData(newNode, new Message(MessageType.LIST.name(), ""));
        for (Message m : replies) {
            String[] data = m.getData().split(" ");
            String newId = data[0];
            String newHost = data[1];
            int newPort = Integer.parseInt(data[2]);

            if (!newId.equals(getInfo().getId())) {
                this.buildNodes(newHost, newPort, hops - 1);
            }
        }
    }
}
