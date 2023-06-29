package io.n2n.sample.pingpong;

import io.n2n.node.NodeInfo;

import java.util.Scanner;

public class PingPongApp {
    private PingPongNode node;

    public static void main(String[] args) {
        new PingPongApp();
    }

    public PingPongApp() {
//        LoggerUtil.setHandlersLevel(Level.INFO);

        System.out.println("[ PingPongApp ]");
        Scanner sc = new Scanner(System.in);

        String cmd = "";
        while (true) {
            System.out.print("> ");
            cmd = sc.nextLine();

            if (cmd.startsWith("init")) {
                init(cmd);
            } else if (cmd.startsWith("ping")) {
                ping(cmd);
            } else if (cmd.equalsIgnoreCase("exit")) {
                break;
            } else if (cmd.equalsIgnoreCase("status")) {
                System.out.println(this.node);
            } else {
                System.out.println("Wrong command. Try following commands");
                System.out.println("init <id> <host> <port>: initialize the node");
                System.out.println("ping <host> <port>: send a ping message to host:port");
                System.out.println("status: print my status");
                System.out.println("exit: exit the program");
            }
        }

        System.out.println("Bye");
    }

    private void init(String cmd) {
        // remove cmd head
        cmd = cmd.substring("init ".length());

        // init <id> <host> <port>
        String[] args = cmd.split(" ");
        String id = args[0];
        String host = args[1];
        int port = Integer.parseInt(args[2]);

        this.node = new PingPongNode(new NodeInfo(id, host, port));
        this.node.startListeningSocket();

        System.out.println("Initialized successfully");
    }

    private void ping(String cmd) {
        // remove cmd head
        cmd = cmd.substring("ping ".length());

        // ping <host> <port>
        String[] args = cmd.split(" ");
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        this.node.ping(host, port);
    }
}
