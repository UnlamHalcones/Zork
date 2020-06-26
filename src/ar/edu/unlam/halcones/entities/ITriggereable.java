package ar.edu.unlam.halcones.entities;

public interface ITriggereable {
	public String execute(Trigger trigger);

	public void triggerThis(String action);

	public String getType();
}
