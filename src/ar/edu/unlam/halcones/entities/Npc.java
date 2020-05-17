package ar.edu.unlam.halcones.entities;

import java.util.List;

public class Npc extends GameEntity {

	private String description;
	private String talk;
	private List<Trigger> triggers;

	public Npc() {
		super();
	}
	
	public Npc(String name, String gender, String number, String description, String talk, List<Trigger> triggers) {
		super(name, gender, number);
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

	public boolean canDoTrigger(Object obj) {
		boolean canDo = false;

		if (obj instanceof GameEntity) {
			for (Trigger trigger : triggers) {
				if (obj.getClass().getName().toLowerCase().contains(trigger.getType().toLowerCase())) {
					GameEntity ge = (GameEntity) obj;
					if (ge.getName() != null && ge.getName() == trigger.getThing()) {
						canDo = true;
						break;
					}
				}
			}
		}

		return canDo;
	}

}
