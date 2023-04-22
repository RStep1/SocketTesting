package run;

import run.EchoServer;
import java.io.IOException;
import java.util.Scanner;


public class EchoTest {

    private Process server;
    private EchoClient client;

    public void setup() {
        try {
            server = EchoServer.start();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        client = EchoClient.start();
    }

    public void givenServerClient_whenServerEchosMessage_thenCorrect() {
        Scanner in = new Scanner(System.in);
        while (true) {
            String line = in.nextLine().trim();
            String resp = client.sendMessage(line);
            System.out.println(resp);
            if (resp.equals("exit")) {
                System.out.println("Program was successfully complete");
                break;
            }
        }
    }

    public void teardown() {
        server.destroy();
        EchoClient.stop();
    }
}