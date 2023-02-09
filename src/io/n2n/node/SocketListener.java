package io.n2n.node;

import io.n2n.util.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * A listener that listens for incoming client socket and
 * handle the connection by passing to handler manager.
 */
public class SocketListener extends Thread {
    private Node node;

    public SocketListener(Node node) {
        this.node = node;
    }

    @Override
    public void run() {
        NodeInfo info = this.node.getInfo();
        NodeSettings settings = this.node.getSettings();
        try (ServerSocket server = new ServerSocket(info.getPort(), settings.getBacklog())) {
            // for many incoming connections
            server.setReuseAddress(true);
            server.setSoTimeout(Config.SOCKET_CONNECTION_TIMEOUT);
            while (settings.isActive()) {
                try {
                    Socket client = server.accept();
                    // set infinite timeout: socket will be closed after a handler dispatched it
                    client.setSoTimeout(Config.SOCKET_READ_TIMEOUT);
                    System.out.println("New connection");

                    // pass the socket to dispatcher
                    settings.getDispatcher().dispatch(client);
                } catch (SocketTimeoutException e) {
//                    System.out.println("Socket timeout. Started again...");
                    continue;
                }
            }
        } catch (SocketException e) {
            System.out.println("Exception while making a socket: " + e);
        } catch (IOException e) {
            System.out.println("Exception while opening a socket: " + e);
        }


    }
}
