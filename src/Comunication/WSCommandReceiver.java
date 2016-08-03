package Comunication;

import Brain.Command;
import Brain.CommandStatus;
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
            if (command == null) // No commands found
                ackRequest.sendAckData(CommandStatus.UNKNOWN);
            else
                ackRequest.sendAckData(command.getStatus());
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
