package client.factory.channel;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

public class ChannelFactory {


    public static DatagramChannel getChannel() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.bind(null);
        System.out.printf(channel.getLocalAddress().toString() + '\n');
        return channel;
    }

}
