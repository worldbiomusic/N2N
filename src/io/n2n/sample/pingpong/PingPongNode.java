package io.n2n.sample.pingpong;

import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.router.NormalRouter;

import java.util.List;


public class PingPongNode extends Node {
    public static final String PING = "PING";
    public static final String PONG = "PONG";

    public PingPongNode(NodeInfo info) {
        super(info);

        // handlers
        this.getSettings().getDispatcher().addHandler(new PingHandler());

        // router
        this.getSettings().setRouter(new NormalRouter(this));
    }

    public void ping(String host, int port) {
        NodeInfo info = new NodeInfo(host + ":" + port, host, port);
        List<Message> replies = sendData(info, new Message(PingPongNode.PING,
                this.getInfo().getHost() + " " + this.getInfo().getPort()));
        System.out.println("Sent a ping to " + info);

        if (replies.isEmpty()) {
            System.out.println(info + " didn't reply to ping you sent");
            return;
        }

//        System.out.println("Replies count: " + replies.size());
        Message reply = replies.get(0);
        if (reply.getType().equals(PingPongNode.PONG)) {
            System.out.println("Pong is arrived from " + info);
        }

        // test code for delayed pong msg
//        if (replies.size() == 2 && replies.get(1).getType().equals("TEST")) {
//            System.out.println("TEST reply is arrived after 1 sec");
//        }
    }
}



