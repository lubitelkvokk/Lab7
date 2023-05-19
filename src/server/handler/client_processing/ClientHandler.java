package server.handler.client_processing;

import mid.fabrics.commands.CommandFabric;
import mid.fabrics.commands.instance.Command;
import mid.fabrics.message.instance.Message;
import server.collection.manager.CollectionManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {


    private CollectionManager collectionManager;
    private Command[] commands;

    private Integer port;

    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    public ClientHandler(CollectionManager collectionManager, Command[] commands, Integer port) {
        this.collectionManager = collectionManager;
        this.commands = commands;
        this.port = port;
    }

    @Override
    public void run() {
        try {

            CommandExecutor commandExecutor = new CommandExecutor(commands);

            logger.info(String.valueOf(ZonedDateTime.now()));
            // open the UDP channel
            DatagramChannel dgc = DatagramChannel.open();
            // set the channel to non blocking mode
            dgc.configureBlocking(false);
            // bind the port
            dgc.socket().bind(new InetSocketAddress(port));

            Selector selector = Selector.open();
            dgc.register(selector, SelectionKey.OP_READ);
            logger.info("UDP Server is started:" + port + "\n");
            try {
                ByteBuffer bb = ByteBuffer.allocateDirect(1_000_000);
                while (true) {
                    selector.select();
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        SelectionKey sk = keys.next();
                        // ready to read
                        if (sk.isReadable()) {
                            DatagramChannel curdc = (DatagramChannel) sk.channel();
                            InetSocketAddress address = (InetSocketAddress) curdc.receive(bb);
                            // accept mid.data
                            Command command = CommandFabric.createCommand(getMessage(bb));
                            sendMessage(curdc,
                                    commandExecutor.runCommand(command, collectionManager),
                                    address);
                        }
                    }
                }
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }


    public static Message getMessage(ByteBuffer bb) throws IOException, ClassNotFoundException {
        bb.flip();
        byte[] bytes = new byte[bb.remaining()];
        bb.get(bytes);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
        bb.clear();
        return (Message) ois.readObject();
    }

    public static void sendMessage(DatagramChannel curds, Message message, InetSocketAddress address) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(message);
        out.flush();
        byte[] data = bos.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        curds.send(buffer, address);
    }
}
