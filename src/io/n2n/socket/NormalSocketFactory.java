package io.n2n.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A factory that creates a {@link N2NSocket} using the standard socket in the Java library
 */
public class NormalSocketFactory extends SocketFactory {
    @Override
    protected N2NSocket createSocket(String host, int port) throws IOException, UnknownHostException {
        return new NormalSocket(new Socket(host, port));
    }

    @Override
    protected N2NSocket createSocket(Socket socket) throws IOException {
        return new NormalSocket(socket);
    }
}
