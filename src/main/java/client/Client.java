package client;

import utility.CommandManager;

import java.net.*;

public class Client {
    private SocketAddress address;
    private DatagramSocket socket;

    public static void main(String[] args) throws SocketException {
        Client client = new Client();
        client.connect();
        client.run();
    }

    public void connect() throws SocketException {
        String hostname = "localhost";
        int port = 5555;
        address = new InetSocketAddress(hostname, port);
        socket = new DatagramSocket();
    }

    public void run() {
        CommandManager commandManager = new CommandManager();
        commandManager.readInput(address, socket);
    }
}
