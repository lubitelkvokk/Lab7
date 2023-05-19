package client.message_processing.sending;


import mid.fabrics.message.instance.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class MessageSender {

    public static void sendMessage(DatagramChannel from, Message message, InetSocketAddress address) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(message);
        out.flush();
        byte[] data = bos.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        from.send(buffer, address);
    }
}
