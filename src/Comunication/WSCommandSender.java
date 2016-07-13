package Comunication;


import java.net.URI;
import java.net.URISyntaxException;

public class WSCommandSender implements CommandSender {


    private WSClient wsClient = null;

    @Override
    public boolean send(String message) {

        try {
            /**
             * Open the websocket
             */
            if (wsClient == null) {
                wsClient = new WSClient(new URI("ws://localhost:8888/websocketserver"));
                wsClient.addMessageHandler(System.out::println);
            }

        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
            return false;
        }
        wsClient.sendMessage(message);
        return true;
    }
}
