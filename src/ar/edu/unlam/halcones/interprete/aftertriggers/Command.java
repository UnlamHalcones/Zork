package ar.edu.unlam.halcones.interprete.aftertriggers;

public class Command {

    private String commandType;
    private String thingName;
    private String type;

    public Command(String commandType, String thingName, String type) {
        this.commandType = commandType;
        this.thingName = thingName;
        this.type = type;
    }

    public String getCommandType() {
        return this.commandType;
    }

    public String getThingName() {
        return this.thingName;
    }

    public String getType() {
        return this.type;
    }
}
