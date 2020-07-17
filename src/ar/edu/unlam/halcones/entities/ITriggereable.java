package ar.edu.unlam.halcones.entities;

public interface ITriggereable {
	ActionDTO execute(Trigger trigger);

	void triggerThis(String action);

	String getType();
}
