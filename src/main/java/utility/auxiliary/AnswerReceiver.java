package utility.auxiliary;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class AnswerReceiver {
    private final DatagramSocket socket;
    private final Serializer serializer;
    byte[] bytes;

    public AnswerReceiver(DatagramSocket socket, Serializer serializer) {
        this.serializer = serializer;
        this.socket = socket;
        bytes = new byte[1000000];
    }

    public String receive() throws IOException, ClassNotFoundException {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        socket.setSoTimeout(2 * 1000);
        socket.receive(packet);
        return (String) serializer.deserialize(bytes);
    }
}
