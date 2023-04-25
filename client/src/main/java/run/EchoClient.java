package run;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;



public class EchoClient {
    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static EchoClient instance;

    private EchoClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 5454));
            buffer = ByteBuffer.allocate(256);
        } catch (ConnectException e) {
            System.out.println("SERVER NOT ANSWER");
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EchoClient start() {
        if (instance == null)
            instance = new EchoClient();
        if (client == null)
            return null;
        return instance;
    }

    public static void stop() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer = null;
    }

    public String sendMessage(String msg) {
//        if (!client.isConnected())
//            return null;
        Message response = null;
        try {
            Message message = new Message(msg);

            //serialization
            byte[] objectBytes = SerializationUtils.serialize(message);
            buffer = ByteBuffer.wrap(objectBytes);
            //

            client.write(buffer);
            buffer.clear();

            client.read(buffer);

            //deserialization
            response = SerializationUtils.deserialize(buffer.array());
            buffer.clear();
            //

            System.out.println("response=" + response.getMessage());
        } catch (IOException e) {
            return null;
        }
        return response.getMessage();
    }

}
