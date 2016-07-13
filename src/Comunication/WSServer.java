package Comunication;

import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collections;


public class WSServer extends WebSocketServer {

    private MessageHandler messageHandler;
    private static final Logger logger = Logger.getLogger(WSServer.class);

    public WSServer(int port) {
        super(new InetSocketAddress(port), Collections.singletonList(new Draft_17()));

    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("Connection opened");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(s);
        } else
            logger.warn("No message handler defined");

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

}
