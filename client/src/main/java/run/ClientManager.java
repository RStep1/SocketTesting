package run;

import java.beans.EventHandler;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientManager {

    public static boolean interactiveMode() {
        EchoClient client = EchoClient.start();
        if (client == null) {
            System.out.println("client = null");
            //нет соединения с сервером
            return false;
        }
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                String line = in.nextLine().trim();
                System.out.println(line);
                String resp = client.sendMessage(line);
                if (resp == null) {
                    EchoClient.stop();
                    in.close();
                    System.out.println("resp = null");
                    return false;
                }
                System.out.println(resp);
                if (resp.equals("exit")) {
                    System.out.println("Program was successfully complete");
                    break;
                }
            } catch (NoSuchElementException e) {
                EchoClient.stop();
                in.close();
//                System.out.println("No such element exception");
                return false;
//                System.exit(0);
            }
        }
        EchoClient.stop();
        in.close();
        return true;
    }
}
