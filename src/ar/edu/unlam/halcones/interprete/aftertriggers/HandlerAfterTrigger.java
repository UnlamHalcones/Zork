package ar.edu.unlam.halcones.interprete.aftertriggers;

import ar.edu.unlam.halcones.entities.Game;

public class HandlerAfterTrigger {

    private static Game game;
    private static CommandHandler commandHandler;

    static {
        VidaCommandHandler vidaCommandHandler = new VidaCommandHandler();
        DefaultCommandHandler defaultCommandHandler = new DefaultCommandHandler();
        CreateCommandHandler createCommandHandler = new CreateCommandHandler();
        commandHandler = new RemoveCommandHandler();
        commandHandler.setNext(vidaCommandHandler);
        vidaCommandHandler.setNext(createCommandHandler);
        createCommandHandler.setNext(defaultCommandHandler);

    }

    public static void handleCommand(Command command) {
        commandHandler.handleCommand(command, game);
    }

    public static void setGame(Game gameParameter) {
        game = gameParameter;
    }
}
