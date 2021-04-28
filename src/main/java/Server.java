import commands.Command;
import utility.CollectionManager;
import utility.auxiliary.Serializer;
import utility.parsing.FileManager;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private SocketAddress address;
    private DatagramChannel channel;
    private Selector selector;
    private final int SERVER_WAITING_TIME = 60 * 60 * 1000;
    private final int PORT = 5885;
    private final Serializer serializer;
    private final Logger logger;

    public Server() {
        logger = LogManager.getLogger();
        this.address = new InetSocketAddress(PORT);
        this.serializer = new Serializer();
        logger.info("Server start.");

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run(args);
    }

    public void openChannel() throws IOException {
        selector = Selector.open();
        this.channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        channel.bind(address);
        logger.info("The channel is open and bound to an address.");
    }

    public Command readRequest() throws IOException, ClassNotFoundException {
        byte[] bytes = new byte[1000000];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.clear();
        address = channel.receive(buffer);
        logger.info("The server received a new request.");
        return (Command) serializer.deserialize(bytes);
    }

    public String executeCommand(Command command, CollectionManager cm) {
        return command.execute(cm);
    }

    public void sendAnswer(String str) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(serializer.serialize(str));
        channel.send(byteBuffer, address);
        logger.info("The server sent an answer to client.");
    }

    public void run(String[] args) {
        try {
            if (args.length == 0 || args[0].matches("(/dev/)\\w*")) {
                throw new IllegalArgumentException();
            }
            CollectionManager collectionManager = new CollectionManager();
            FileManager fileManager = new FileManager(new File(args[0]));
            fileManager.manageXML(collectionManager);
            logger.info("The collection is created based on the contents of the file.");
            openChannel();
            while (true) {
                int readyChannels = selector.select(SERVER_WAITING_TIME);
                if (readyChannels == 0) {
                    selector.close();
                    channel.close();
                    collectionManager.save();
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }
                    if (key.isWritable()) {
                        sendAnswer(executeCommand(readRequest(), collectionManager));
                        channel.register(selector, SelectionKey.OP_READ);
                    }
                    keyIterator.remove();
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("There is no file pathname in the command argument or entered pathname is incorrect.");
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
        }
    }
}
