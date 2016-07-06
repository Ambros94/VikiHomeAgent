package Brain;

import GUI.JavaFxGui;

public class GuiCommandSender implements CommandSender {

    @Override
    public boolean send(String message) {
        JavaFxGui.getSingleton().showMessage(message);
        return true;
    }
}
