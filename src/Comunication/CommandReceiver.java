package Comunication;

public interface CommandReceiver {
    void addCommandHandler(CommandHandler commandHandler);

    void startReceiver();

    void stopReceiver();
}
