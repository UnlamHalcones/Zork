package ar.edu.unlam.halcones.entities;

import java.util.List;
import java.util.Optional;

public class Npc extends GameEntity implements ITriggereable {
	
	private String description;
	private String talk;
	private List<Trigger> triggers;

	public Npc() {
		super();
		this.type=GameEntityTypes.NPC;
	}
	
	public Npc(String description, String state) {
		super(description, state);
	}

	public Npc(String description, String talk, List<Trigger> triggers) {
		this.description = description;
		this.talk = talk;
		this.triggers = triggers;
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

	public boolean canDoTrigger(GameEntity ge) {
		boolean canDo = false;

		for (Trigger trigger : triggers) {
			if (ge.getClass().getName().toLowerCase().contains(trigger.getType().toLowerCase())) {
				if (ge.getName() != null && ge.getName() == trigger.getThing()) {
					canDo = true;
					break;
				}
			}
		}

		return canDo;
	}
	
	public String Execute(Trigger trigger) throws Exception {
		Optional<Trigger> aux = triggers.stream().filter(t -> t.getType().equals(trigger.getType()) && t.getThing().equals(trigger.getThing())).findAny();	
		
		if (!aux.isPresent())
		{
			throw new Exception("Accion no valida en el Npc");
		}
		
		super.status = aux.get().getAfterTrigger();
		
		return aux.get().getOnTrigger();
	}
	
	@Override
	public void triggerThis(String action) throws Exception {
		
		for (Trigger triggers_IT : triggers) {
		
			if(triggers_IT.getType().contentEquals(action)) {
				this.Execute(triggers_IT);
				return;
			}
			
		}
		
	}
}
