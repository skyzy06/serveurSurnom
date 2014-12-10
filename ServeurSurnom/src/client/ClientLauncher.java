package client;

import java.io.IOException;

public class ClientLauncher {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {

        ClientByConsol client = new ClientByConsol("localhost", 1234);
        try {
            while (client.commandPrompt() && client.connect());
            System.out.println("Exiting programm...");
        } catch (IOException e1) {
        } finally {
        }
    }
}
