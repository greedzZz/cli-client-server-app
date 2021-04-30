package client;

import client.utility.ClientAsker;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class Client {
    private SocketAddress address;
    private DatagramSocket socket;
    private final int PORT = 8725;
    private final String HOST = "localhost";

    public static void main(String[] args) {
        ClientAsker clientAsker = new ClientAsker();
        Client client = new Client();
        boolean tryingToConnect = true;
        while (tryingToConnect) {
            try {
                client.connect();
                client.run();
            } catch (IOException e) {
                System.out.println("Unfortunately, the server is currently unavailable.");
                if (clientAsker.ask() <= 0) {
                    tryingToConnect = false;
                }
            }
        }
        client.getSocket().close();
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

    public DatagramSocket getSocket() {
        return this.socket;
    }
}
