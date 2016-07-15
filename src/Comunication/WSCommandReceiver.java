package Comunication;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.apache.log4j.Logger;


public class WSCommandReceiver implements CommandReceiver {

    private CommandHandler commandHandler;
    private SocketIOServer server;
    private static final Logger logger = Logger.getLogger(WSCommandReceiver.class);

    public WSCommandReceiver(String hostname, int port) {
        /*
         * Configure the socket server
         */
        Configuration config = new Configuration();
        config.setHostname(hostname);
        config.setPort(port);
        server = new SocketIOServer(config);
        /*
         * When a command is received it's delegated to the commandHandler
         */
        server.addEventListener("command", String.class, (client, s, ackRequest) -> {
            if (commandHandler != null)
                commandHandler.handleCommand(s);
            else
                logger.warn("No command handler defined, command ignored");
            /*
             * Notify socket.io client that command has been received
             */
            client.sendEvent("ack");
        });
    }

    @Override
    public void addCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void startReceiver() {
        logger.info("SocketServer started.");
        server.start();
    }

    @Override
    public void stopReceiver() {
        logger.info("SocketServer stopped.");
        server.stop();
    }

}
