package run;

public class Main {
    public static void main(String[] args) {
        EchoTest echoTest = new EchoTest();
        echoTest.setup();
        echoTest.givenServerClient_whenServerEchosMessage_thenCorrect();
        echoTest.teardown();
    }
}