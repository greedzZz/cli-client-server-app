package client;

import utility.CommandManager;
import utility.auxiliary.ClientAsker;

import java.io.IOException;
import java.net.*;

public class Client {
    private SocketAddress address;
    private DatagramSocket socket;

    public static void main(String[] args) {
        Client client = new Client();
        boolean tryingToConnect = true;
        while (tryingToConnect) {
            try {
                client.connect();
                client.run();
            } catch (IOException e) {
                System.out.println("Unfortunately, the server is currently unavailable.");
                if (new ClientAsker().ask() <= 0) {
                    tryingToConnect = false;
                }
            }
        }
        System.out.println("The program is finished.");
    }

    public void connect() throws SocketException {
        String hostname = "localhost";
        int port = 5555;
        address = new InetSocketAddress(hostname, port);
        socket = new DatagramSocket();
    }

    public void run() throws IOException {
        CommandManager commandManager = new CommandManager();
        commandManager.readInput(address, socket);
    }
}
