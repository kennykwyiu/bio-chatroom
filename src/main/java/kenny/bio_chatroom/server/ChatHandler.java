package kenny.bio_chatroom.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatHandler implements Runnable {
    private ChatServer server;
    private Socket socket;
    public ChatHandler(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void run() {
        try {
            // save new online user
            server.addClient(socket);

            // ready the msg from user
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            String msg = null;
            while ((msg = reader.readLine()) != null) {
                String fwdMsg = "Client[" + socket.getPort() + "]: " + msg + "\n";
                System.out.print(fwdMsg);

                //fwding received msg to online user in the chatroom
                server.forwardMessage(socket, fwdMsg);

                // validate user quit or not
                if (server.readyToQuit(msg)) {
                   break;
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                server.removeClient(socket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
