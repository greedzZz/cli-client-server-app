package client;

import utility.CommandManager;

import java.net.*;

public class Client {
//    SocketAddress address;
//    DatagramSocket socket;
//    CommandManager commandManager;
//    private final String hostname = "localhost";
//    private final int port = 5555;
//    public Client() throws SocketException {
//        this.socket = new DatagramSocket();
//    }

    public static void main(String[] args) throws Exception {

        SocketAddress a = new InetSocketAddress("localhost", 5555);
        DatagramSocket s = new DatagramSocket();
        CommandManager commandManager = new CommandManager();
        commandManager.readInput(a, s);

    }

//    public void connect() {
//        address = new InetSocketAddress(hostname, port);
//    }
//
//    public void run() {
//        commandManager = new CommandManager(address, socket);
//    }
}
