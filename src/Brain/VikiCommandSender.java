package Brain;

import Comunication.CommandSender;
import Utility.Config;

public class VikiCommandSender implements CommandSender {

    private static final String vikiAddress = Config.getConfig().getVikiAddress();

    @Override
    public boolean send(String message) {
        //TODO Impelemnt, ask nicola where i have to send this
        return false;
    }
}
