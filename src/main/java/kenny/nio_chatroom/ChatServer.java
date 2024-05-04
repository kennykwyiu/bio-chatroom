package kenny.nio_chatroom;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ChatServer {
    private int DEFAULT_PORT = 8888;
    private final String QUIT = "quit";
    private static final int BUFFER = 1024;
    private ServerSocketChannel server;
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER);
    private Charset charset = StandardCharsets.UTF_8;
    private int port;

    public ChatServer() {
        this.port = DEFAULT_PORT;
    }

    public ChatServer(int port) {
        this.port = port;
    }

    private void Start() {

    }

    private boolean readyToQuit(String msg) {
        return QUIT.equals(msg);
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
                System.out.println("Closing the " + closeable.getClass());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
