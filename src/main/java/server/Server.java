package server;

import commands.Command;
import utility.CollectionManager;
import utility.auxiliary.Serializer;
import utility.parsing.FileManager;

import java.io.File;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {
    public static void main(String[] args) {
        try {
            CollectionManager collectionManager = new CollectionManager();
            FileManager fileManager = new FileManager(new File(args[0]));
            fileManager.manageXML(collectionManager);

            Serializer serializer = new Serializer();
            byte[] bytes = new byte[1000000];
            SocketAddress address = new InetSocketAddress(5555);
            DatagramChannel channel = DatagramChannel.open();
            channel.bind(address);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            Command command;
            while (true) {
                buffer.clear();
                channel.receive(buffer);
                command = (Command) serializer.deserialize(bytes);
                command.execute(collectionManager);
            }
        } catch (Exception e) {
            System.out.println("There is no file pathname in the command argument or entered pathname is incorrect.");
        }
//        Serializer serializer = new Serializer();
//        byte bytes[] = new byte[1000000];
//        SocketAddress address = new InetSocketAddress(5555);
//        DatagramChannel channel = DatagramChannel.open();
//        channel.bind(address);
//        ByteBuffer buffer = ByteBuffer.wrap(bytes);
//        buffer.clear();
//        channel.receive(buffer);
//        Command command = (Command) serializer.deserialize(bytes);
//        CollectionManager collectionManager = new CollectionManager();
//        FileManager fileManager = new FileManager(new File(args[0]));
//        fileManager.manageXML(collectionManager);
//        command.execute(collectionManager);


//        for (int i = 0; i < 3; i++) {
//            b[i] *= 2;
//        }
//        f.flip();
//        s.send(f, a);
    }
}
