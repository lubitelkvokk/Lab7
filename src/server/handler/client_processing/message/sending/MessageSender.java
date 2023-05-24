package server.handler.client_processing.message.sending;

import mid.fabrics.message.instance.Message;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class MessageSender {

    public static void sendMessage(DatagramChannel server, Message message, SocketAddress client) throws IOException, ClassNotFoundException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(baos);
        ous.writeObject(message);
        byte[] buffer = baos.toByteArray();
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

        server.send(byteBuffer, client);
    }
}
