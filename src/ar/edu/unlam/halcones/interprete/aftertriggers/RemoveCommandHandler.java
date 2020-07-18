package ar.edu.unlam.halcones.interprete.aftertriggers;

import ar.edu.unlam.halcones.entities.Game;

public class RemoveCommandHandler implements CommandHandler {

    private CommandHandler next;

    @Override
    public void setNext(CommandHandler next) {
        this.next = next;
    }

    @Override
    public void handleCommand(Command command, Game game) {
 
    	if (game != null) {
	        if(command.getCommandType().equals("remove")) {
	            if(command.getType().equals("npc"))
	                game.removeNpc(command.getThingName());
	            if(command.getType().equals("item"))
	                game.removeItemFromCharacter(command.getThingName());
	        } else {
	            this.next.handleCommand(command, game);
	        }
    	}
    }
}
