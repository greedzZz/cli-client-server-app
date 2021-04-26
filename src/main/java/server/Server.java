package server;

import commands.Command;
import utility.CollectionManager;
import utility.auxiliary.Serializer;
import utility.parsing.FileManager;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {
    private final SocketAddress address;
    private DatagramChannel channel;

    public Server() {
        int port = 5555;
        this.address = new InetSocketAddress(port);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run(args);
    }

    public Command readRequest(ByteBuffer buffer, byte[] bytes) throws IOException, ClassNotFoundException {
        buffer.clear();
        channel.receive(buffer);
        return (Command) new Serializer().deserialize(bytes);
    }

    public void executeCommand(Command command, CollectionManager cm) {
        command.execute(cm);
    }

    public void sendAnswer() {
    }

    public void run(String[] args) {
        try {
            if (args.length == 0 || args[0].matches("(/dev/)\\w*")) {
                throw new IllegalArgumentException();
            }
            CollectionManager collectionManager = new CollectionManager();
            FileManager fileManager = new FileManager(new File(args[0]));
            fileManager.manageXML(collectionManager);

            channel = DatagramChannel.open();
            channel.bind(address);
            byte[] bytes = new byte[1000000];
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            while(true){
                executeCommand(readRequest(buffer, bytes), collectionManager);
            }
        } catch (Exception e) {
            System.out.println("There is no file pathname in the command argument or entered pathname is incorrect.");
        }
    }
}
