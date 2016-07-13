package Comunication;

import GUI.JavaFxGui;

/**
 * Sends a command to the gui, that will display it in a textBox
 */
public class GuiCommandSender implements CommandSender {

    @Override
    public boolean send(String message) {
        JavaFxGui.getSingleton().showMessage(message);
        return true;
    }
}
