package ar.edu.unlam.halcones.entities;

import java.util.List;
import java.util.Optional;

public class Npc extends GameEntity implements ITriggereable {
	
	private String description;
	private String talk;
	private List<Trigger> triggers;
	
	public Npc(String description, String talk, List<Trigger> triggers) {
		super();
		this.description = description;
		this.talk = talk;
		this.triggers = triggers;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTalk() {
		return talk;
	}
	public void setTalk(String talk) {
		this.talk = talk;
	}
	public List<Trigger> getTriggers() {
		return triggers;
	}
	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}
	
	public String Execute(Trigger trigger) {
		Optional<Trigger> aux = triggers.stream().filter(t -> t.getType().equals(trigger.getType()) && t.getThing().equals(trigger.getThing())).findAny();
		
		if (!aux.isPresent())
		{
			return "Accion no valida en el Npc";
		}
		
		super.status = trigger.getAfterTrigger();
		
		return trigger.getOnTrigger();
	}
}
