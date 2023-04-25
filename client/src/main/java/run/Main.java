package run;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        //setup
//        EchoClient client = EchoClient.start();
//
//        if (client == null)
//            System.exit(0);
//
//        //обмен данными
//        Scanner in = new Scanner(System.in);
//        while (true) {
//            try {
//                String line = in.nextLine().trim();
//                String resp = client.sendMessage(line);
//
//                System.out.println(resp);
//                if (resp.equals("exit")) {
//                    System.out.println("Program was successfully complete");
//                    break;
//                }
//            } catch (NoSuchElementException e) {
//                EchoClient.stop();
//                System.exit(0);
//            }
//        }
//
//        //teardown
//        EchoClient.stop();
//        in.close();


        boolean status = false;
        while (!status) {
            status = ClientManager.interactiveMode();
        }
    }
}