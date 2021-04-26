package utility.auxiliary;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class AnswerReceiver {
    private final DatagramSocket socket;
    byte[] bytes;

    public AnswerReceiver(DatagramSocket socket) {
        this.socket = socket;
        bytes = new byte[1000];
    }

    public String receive() throws IOException {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        socket.receive(packet);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
