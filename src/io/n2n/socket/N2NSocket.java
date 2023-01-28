package io.n2n.socket;

import java.io.IOException;

/**
 * The socket interface for N2N system. Methods for reading and writing
 * are being used for basic InputStream and OutputStream classes of the
 * Java library.
 */
public interface N2NSocket extends AutoCloseable{
    int read(byte[] bytes) throws IOException;
    void write(byte[] bytes) throws IOException;

    /**
     * Used in try-with-resources
     * @see AutoCloseable#close()
     * @throws IOException
     */
    @Override
    void close() throws IOException;
}
