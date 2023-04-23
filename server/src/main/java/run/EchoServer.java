package run;


import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {

    private static final String POISON_PILL = "exit";

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 5454));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(256);

        while (true) {
            System.out.println(selector.select());
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {

                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }

                if (key.isReadable()) {
                    answerWithEcho(buffer, key);
                }

                iter.remove();
            }
        }
    }

    private static void answerWithEcho(ByteBuffer buffer, SelectionKey key)
            throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        int r = client.read(buffer);

        //deserialization
        Message message = SerializationUtils.deserialize(buffer.array());
        System.out.println(message.getMessage());


        //serialization
        byte[] objectBytes = SerializationUtils.serialize(message);
        buffer = ByteBuffer.wrap(objectBytes);
        ByteBuffer byteBuffer = ByteBuffer.wrap(objectBytes);

        if (r == -1 || message.getMessage().equals(POISON_PILL)) {
            System.out.println(String.format(
                    "Not accepting client %s messages anymore", client.getRemoteAddress()));
            client.close();
        }
        else {
            buffer.flip();
            client.write(byteBuffer);
            buffer.clear();
            byteBuffer.clear();
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket) {
        try {
            SocketChannel client = serverSocket.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
