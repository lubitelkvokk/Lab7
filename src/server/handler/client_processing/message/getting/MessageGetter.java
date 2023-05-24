package server.handler.client_processing.message.getting;


import mid.fabrics.message.instance.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class MessageGetter {

    public static Message getMessage(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
        Message message = (Message) ois.readObject();
        return message;
    }
}