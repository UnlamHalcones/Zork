package ar.edu.unlam.halcones.entities;

import java.util.List;

public class Npc extends GameEntity {
	
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
	
	
}
