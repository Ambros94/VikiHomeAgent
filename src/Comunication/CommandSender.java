package Comunication;

/**
 * When a command is ready to be executed a Command sender can send that to the executor
 */
public interface CommandSender {
    /**
     * Send the command to the executor
     *
     * @param message Message that has be sent on to the executor
     * @return true if the command has been successfully executed, false if something bad has occurred (More info should be available on the log)
     */
    boolean send(String message);

    void startSender();

    void stopSender();
}
