package ar.edu.unlam.halcones.interprete.aftertriggers;

import ar.edu.unlam.halcones.entities.Game;

public class CreateCommandHandler implements CommandHandler {
    private CommandHandler next;

    @Override
    public void setNext(CommandHandler next) {
        this.next = next;
    }

    @Override
    public void handleCommand(Command command, Game game) {
        String commandType = command.getCommandType();
        if(commandType.contains("create")) {
            game.crearNuevoItem(commandType.substring(6));
        } else {
            this.next.handleCommand(command, game);
        }
    }
}
