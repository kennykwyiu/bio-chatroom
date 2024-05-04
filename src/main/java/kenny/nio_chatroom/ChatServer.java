package kenny.nio_chatroom;

import java.io.Closeable;

public class ChatServer {
    private int DEFAULT_PORT = 8888;
    private final String QUIT = "quit";
    private static final int BUFFER = 1024;
    private void Start() {

    }

    private boolean readyToQuit(String msg) {
        return QUIT.equals(msg);
    }

    private void close(Closeable closeable) {

    }
}
