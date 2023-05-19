package client.message_processing.getting;


import mid.fabrics.message.instance.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class MessageGetter {

    public static Message getMessage(DatagramChannel client) throws IOException, ClassNotFoundException {
        ByteBuffer bb = ByteBuffer.allocate(1_000_000);
        client.receive(bb);
        bb.flip();
        byte[] bytes = new byte[bb.remaining()];
        bb.get(bytes);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
        Message message = (Message) ois.readObject();
        bb.clear();
        return message;
    }
}
