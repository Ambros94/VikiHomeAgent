package Comunication;

import Utility.Config;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.apache.log4j.Logger;

import java.net.URISyntaxException;

public class WSCommandSender implements CommandSender {

    private static final String vikiAddress = Config.getConfig().getVikiAddress();
    private Logger logger = Logger.getLogger(WSCommandSender.class);
    private Socket socket;

    @Override
    public boolean send(String message) {
        if (socket == null)
            logger.error("You HAVE TO start the sender before usage");
        logger.info("Message sent :" + message);
        socket.emit(Config.getConfig().getCommandEventMessage(), message);
        return false;
    }

    @Override
    public void startSender() {
        try {
            socket = IO.socket(vikiAddress);
            socket.open();
            logger.info("Socket successfully opened.");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopSender() {
        logger.info("Socket closed.");
        socket.close();
    }
}
