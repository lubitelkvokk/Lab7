package server;

import mid.fabrics.commands.instance.Command;
import mid.fabrics.commands.instance.with_content.with_group.AddCommand;
import mid.fabrics.commands.instance.with_content.with_group.AddIfMinCommand;
import mid.fabrics.commands.instance.with_content.with_group.RemoveGreaterCommand;
import mid.fabrics.commands.instance.with_content.with_group.UpdateCommand;
import mid.fabrics.commands.instance.with_content.with_int.RemoveByIdCommand;
import mid.fabrics.commands.instance.without_content.*;
import server.collection.converter.ParsingCollection;
import server.collection.manager.CollectionManager;
import server.handler.client_processing.ClientHandler;
import server.handler.client_processing.NewClientHandler;
import server.handler.console.ConsoleHandler;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;


public class Server {

    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) throws IOException {

        ExecutorService executor = Executors.newCachedThreadPool();

        DatagramChannel server = DatagramChannel.open();
        server.bind(new InetSocketAddress(Integer.parseInt(args[0])));


        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(65535);
            SocketAddress socketAddress = server.receive(byteBuffer);
            byteBuffer.flip();
            byte[] buf = byteBuffer.array();
            logger.info("Обработка клиента" + socketAddress);
            NewClientHandler clientHandler = new NewClientHandler(server, buf, socketAddress);
            Thread thread = new Thread(clientHandler);
            executor.submit(thread);
        }
    }
}