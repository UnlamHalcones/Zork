package ar.edu.unlam.halcones.entities;

public interface ITriggereable {
	
	public String Execute(Trigger trigger) throws Exception;
	public void triggerThis(String action) throws Exception;
}
