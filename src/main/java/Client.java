import utility.CommandManager;
import utility.auxiliary.ClientAsker;

import java.io.IOException;
import java.net.*;

public class Client {
    private SocketAddress address;
    private DatagramSocket socket;
    private final ClientAsker clientAsker;
    private final int PORT = 5885;
    private final String HOST = "localhost";

    public Client() {
        this.clientAsker = new ClientAsker();
    }

    public static void main(String[] args) {
        Client client = new Client();
        boolean tryingToConnect = true;
        while (tryingToConnect) {
            try {
                client.connect();
                client.run();
            } catch (IOException e) {
                System.out.println("Unfortunately, the server is currently unavailable.");
                if (client.clientAsker.ask() <= 0) {
                    tryingToConnect = false;
                }
            }
        }
        client.socket.close();
        System.out.println("The program is finished.");
    }

    public void connect() throws SocketException {
        address = new InetSocketAddress(HOST, PORT);
        socket = new DatagramSocket();
    }

    public void run() throws IOException {
        CommandManager commandManager = new CommandManager();
        commandManager.readInput(address, socket);
    }
}
