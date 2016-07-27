package Comunication;

import Brain.Command;
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
         * COMMAND
         */
        server.addEventListener(Config.getConfig().getTextCommandMessage(), String.class, (client, s, ackRequest) -> {
            logger.info("Command received");
            if (universeController == null) {
                ackRequest.sendAckData(503);
                logger.warn("No command handler defined, command ignored");
                return;
            }

            Command command = universeController.submitText(s);
            if (command == null) {// No commands found
                ackRequest.sendAckData(0);
                return;
            }

            switch (command.getStatus()) {
                case MISSING_PARAMETERS:// Parameter missing
                    ackRequest.sendAckData(-1);
                    break;
                case OK:// The right command has been executed
                    universeController.sendCommand(command);
                    ackRequest.sendAckData(1);
                    break;
                case LOW_CONFIDENCE: // The best command has too confidence below accepted level
                    ackRequest.sendAckData(0);
                    break;
            }
        });
        /*
         *  CONNECTION
         */
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
