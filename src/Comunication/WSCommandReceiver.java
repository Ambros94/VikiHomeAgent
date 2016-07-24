package Comunication;

import Brain.UniverseController;
import Utility.Config;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.apache.log4j.Logger;


public class WSCommandReceiver implements CommandReceiver {

    private SocketIOServer server;
    private static final Logger logger = Logger.getLogger(WSCommandReceiver.class);
    private UniverseController universeController;

    /**
     * Builds a WsCommandReceiver that (after been started) will stand on address:port defined in config file
     */
    public WSCommandReceiver() {
        this(Config.getConfig().getCommandReceiverAddress(), Config.getConfig().getCommandReceiverPort());
    }

    /**
     * Builds a WsCommandReceiver that (after been started) will stand on address:port passed as params
     *
     * @param hostname Address where system will listen (usually localhost )
     * @param port     Port where the system will open the socket.
     */
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
        server.addEventListener(Config.getConfig().getTextCommandMessage(), String.class, (client, s, ackRequest) -> {
            if (universeController != null)
                universeController.submitText(s);
            else {
                ackRequest.sendAckData(404);
                logger.warn("No command handler defined, command ignored");
            }
            /*
             * Notify socket.io client that command has been received
             */
            ackRequest.sendAckData(200);
        });
        server.addConnectListener(socketIOClient -> logger.info("New inbound connection received !"));
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

    @Override
    public void setUniverseController(UniverseController controller) {
        this.universeController = controller;
    }

}
