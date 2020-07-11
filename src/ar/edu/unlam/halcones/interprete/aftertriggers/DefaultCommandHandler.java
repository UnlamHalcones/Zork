package ar.edu.unlam.halcones.interprete.aftertriggers;

import ar.edu.unlam.halcones.entities.Game;

public class DefaultCommandHandler implements CommandHandler{

    private CommandHandler next;

    @Override
    public void setNext(CommandHandler next) {
        this.next = next;
    }

    @Override
    public void handleCommand(Command command, Game game) {

    }
}
