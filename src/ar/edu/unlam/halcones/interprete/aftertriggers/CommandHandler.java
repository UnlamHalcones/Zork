package ar.edu.unlam.halcones.interprete.aftertriggers;

import ar.edu.unlam.halcones.entities.Game;

public interface CommandHandler {

    void setNext(CommandHandler next);

    void handleCommand(Command command, Game game);
}
