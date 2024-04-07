package kenny.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
    private int DEFAULT_PORT = 8888;
    private final String QUIT = "quit";

    private ServerSocket serverSocket;
    private Map<Integer, Writer> connectedClients;

    public ChatServer() {
        connectedClients = new HashMap<Integer, Writer>();
    }

    public void addClient(Socket socket) throws IOException {
        if (socket != null) {
            int port = socket.getPort();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            connectedClients.put(port, writer);
            System.out.println("Client[" + port + "] is connected to server");
        }
    }

    public void removeClient(Socket socket) throws IOException {
        if (socket != null) {
            int port = socket.getPort();
            if (connectedClients.containsKey(port)) {
                connectedClients.get(port).close();
            }
            connectedClients.remove(port);
            System.out.println("Client[" + port + "] is disconnected");
        }
    }

    public void forwardMessage(Socket socket, String fwdMsg) throws IOException {
        for (Integer id : connectedClients.keySet()) {
            if (!id.equals(socket.getPort())) {
                Writer writer = connectedClients.get(id);
                writer.write(fwdMsg);
                writer.flush();
            }
        }
    }

    public void close() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                System.out.println("Closing the serverSocket");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("Server is started, serverSocket port: " + DEFAULT_PORT + "...");

            while (true) {
                // waiting for user to connect
                Socket socket = serverSocket.accept();

                // create ChatHandler thread

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start();
    }
}
