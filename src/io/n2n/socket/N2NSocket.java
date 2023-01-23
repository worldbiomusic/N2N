package io.n2n.socket;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface N2NSocket {
    void raed(byte[] bytes) throws IOException;
    void write(byte[] bytes) throws IOException;
    void close() throws IOException;
}
