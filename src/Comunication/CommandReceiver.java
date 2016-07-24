package Comunication;

import Brain.UniverseController;

public interface CommandReceiver {

    void startReceiver();

    void stopReceiver();

    void setUniverseController(UniverseController controller);
}
