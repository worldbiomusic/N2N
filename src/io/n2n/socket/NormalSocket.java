package io.n2n.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Fits the {@link N2NSocket} interface in the N2N system.
 */
public class NormalSocket implements N2NSocket {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public NormalSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = this.socket.getInputStream();
        this.outputStream = this.socket.getOutputStream();
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return this.inputStream.read(bytes);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        this.outputStream.write(bytes);
        this.outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
        this.inputStream.close();
        this.socket.close();
    }
}
