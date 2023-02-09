package io.n2n.sample.pingpong;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;

/**
 * Sends a pong message to where sent a ping message
 */
public class PingHandler implements Handler {

    @Override
    public void handle(Connection conn, Message msg) {
        String[] args = msg.getData().split(" ");
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        System.out.println("Ping from " + host + ":" + port);

        conn.sendData(new Message(PingPongNode.PONG, ""));
        System.out.println("Sent a Pong to " + host + ":" + port);

        /*
         1. if make sleep current thread processing the socket, connection is not
         gonna be closed cuz socket read timeout(Socket#setSoTimeout()) is infinite.
         */
//        try {
//            Thread.sleep(1000 * 1);
//            conn.sendData(new Message("TEST", ""));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        /*
        2. if create a new thread and send a message after a while, connection
        is gonna be closed cuz the thread processing socket is still on work and
        closed after this method finished.
         */
//        new Thread(() -> {
//            try {
//                Thread.sleep(1000 * 1);
//                conn.sendData(new Message("TEST", ""));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

    @Override
    public String getName() {
        return PingPongNode.PING;
    }
}
