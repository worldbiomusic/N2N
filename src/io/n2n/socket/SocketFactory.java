package io.n2n.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Factory for {@link N2NSocket} implementation. Used in the N2N system to
 * generate sockets for normal use, or testing, or etc purposes.
 */
public abstract class SocketFactory {
    private static SocketFactory factory = new NormalSocketFactory();

    public static void setFactory(SocketFactory factory) throws NullPointerException {
        if (factory == null) {
            throw new NullPointerException("Attempting to set socket factory with null");
        }
        SocketFactory.factory = factory;
    }

    public static SocketFactory getFactory() {
        return factory;
    }

    public final static N2NSocket create(String host, int port) throws IOException, UnknownHostException {
        return factory.createSocket(host, port);
    }

    public final static N2NSocket create(Socket socket) throws IOException {
        return factory.createSocket(socket);
    }

    protected abstract N2NSocket createSocket(String host, int port) throws IOException, UnknownHostException;

    protected abstract N2NSocket createSocket(Socket socket) throws IOException;
}
