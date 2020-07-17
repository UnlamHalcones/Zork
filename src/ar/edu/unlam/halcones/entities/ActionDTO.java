package ar.edu.unlam.halcones.entities;

public class ActionDTO {
    private String command;
    private String thing;
    private String response;
    private boolean isPerformed;

    public ActionDTO(String response, boolean isPerformed) {
        this.response = response;
        this.isPerformed = isPerformed;
    }

    public ActionDTO(String command, String thing, String response, boolean isPerformed) {
        this.command = command;
        this.thing = thing;
        this.response = response;
        this.isPerformed = isPerformed;
    }

    public ActionDTO(String command, String thing) {
        this.command = command;
        this.thing = thing;
    }

    public ActionDTO(String thing, boolean performed, String response) {
        this.thing = thing;
        this.isPerformed = performed;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public boolean isPerformed() {
        return isPerformed;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public String getThing() {
        return thing;
    }
}
