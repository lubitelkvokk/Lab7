package client.message_processing;

import client.message_processing.getting.MessageGetter;
import client.message_processing.sending.MessageSender;
import mid.fabrics.message.instance.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class SendAndGet {
    public static Message sendMessageGetResponse(DatagramChannel from, InetSocketAddress to, Message message) throws IOException, ClassNotFoundException {

        MessageSender.sendMessage(from, message, to);
        return MessageGetter.getMessage(from);
    }
}
