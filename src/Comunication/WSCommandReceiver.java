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
            if (universeController == null) {
                ackRequest.sendAckData(503);
                logger.warn("No command handler defined, command ignored");
                return;
            }

            switch (universeController.submitText(s)) {
                case -1:// Parameter missing
                    ackRequest.sendAckData(-1);
                    return;
                case 0:// Nothing has been found
                    ackRequest.sendAckData(0);
                    return;
                case 1:// The right command has been executed
                    ackRequest.sendAckData(1);
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
