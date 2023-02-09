package io.n2n.connection;

import io.n2n.socket.N2NSocket;
import io.n2n.socket.SocketFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages handlers for a node.
 */
public class Dispatcher {

    private class HandlerDispatcher extends Thread {
        private N2NSocket socket;

        public HandlerDispatcher(Socket socket) throws IOException {
            this.socket = SocketFactory.create(socket);
        }


        @Override
        public void run() {
            try (Connection connection = new Connection(null, socket)) {
                Message msg = connection.receiveData();
                String type = msg.getType();

                // handle the message
                handle(type, connection, msg);
            } catch (IOException e) {
                System.out.println("Exception while reading message from a socket: " + e);
            }
        }
    }

    private Map<String, Handler> handlers; // using Map for efficiency

    public Dispatcher() {
        this.handlers = new HashMap<>();
    }

    /**
     * Adds a new handler to the dispatcher.
     *
     * @param handler the handler itself
     * @return false if the same thing exist already
     */
    public boolean addHandler(Handler handler) {
        if (this.handlers.containsKey(handler.getName())) {
            return false;
        }

        this.handlers.put(handler.getName(), handler);
        return true;
    }

    /**
     * Removes handlers from the dispatcher.
     *
     * @param name the name of the handler
     * @return True if removed or false
     */
    public boolean removeHandler(String name) {
        return this.handlers.remove(name) != null;
    }

    public boolean hasHandler(String name) {
        return this.handlers.containsKey(name);
    }

    public Handler getHandler(String name) {
        return this.handlers.get(name);
    }

    /**
     * Dispatches a connection with a registered handler.<br>
     * Invoked by a handler dispatcher only.
     *
     * @param name       the name of the handler
     * @param connection the connection which will be processed by a handler
     * @param msg        the message received from the connection
     */
    private void handle(String name, Connection connection, Message msg) {
        if (hasHandler(name)) {
            this.handlers.get(name).handle(connection, msg);
        }
    }

    public List<Handler> getHandlers() {
        return new ArrayList<>(this.handlers.values());
    }

    /**
     * Dispatches the socket to {@link HandlerDispatcher}.
     *
     * @param socket the socket which has some data
     * @throws IOException if has any errors with the socket
     */
    public void dispatch(Socket socket) throws IOException {
        new HandlerDispatcher(socket).run();
    }

    @Override
    public String toString() {
        String handlerNames = getHandlers().stream().map(h -> h.getName()).collect(Collectors.joining(", "));
        return "Dispatcher{" +
                "handlers=" + handlerNames +
                '}';
    }
}