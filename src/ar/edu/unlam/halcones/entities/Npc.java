package ar.edu.unlam.halcones.entities;

import java.util.HashMap;

import ar.edu.unlam.halcones.interprete.aftertriggers.HandlerAfterTrigger;
import ar.edu.unlam.halcones.interprete.aftertriggers.Command;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Npc extends GameEntity implements ITriggereable, INombrable<Npc> {

	@JsonProperty("description")
	private String description;

	@JsonProperty("talk")
	private String talk;

	@JsonProperty("triggers")
	private List<Trigger> triggers;

	public Npc() {
		super();
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

	@Override
	public ActionDTO execute(Trigger trigger) {
		Optional<Trigger> aux = triggers.stream()
				.filter(t -> t.getType().equals(trigger.getType()) && t.getThing().equals(trigger.getThing()))
				.findAny();
		if (!aux.isPresent()) {
			return new ActionDTO(this.getName(), false, "No puede hacer eso con " + this.getFullDescription());
		}

		String afterTrigger = aux.get().getAfterTrigger();
		if(afterTrigger != null) {
			String[] split = afterTrigger.split(",");
			for(String s : split) {
				Command command = new Command(s, this.getName(), this.getType());
				HandlerAfterTrigger.handleCommand(command);
			}
		}
		return new ActionDTO(this.getName(), true, aux.get().getOnTrigger());
	}

	@Override
	public void triggerThis(String action) {

		for (Trigger triggers_IT : triggers) {

			if (triggers_IT.getType().contentEquals(action)) {
				this.execute(triggers_IT);
				return;
			}

		}

	}

	public Map<String, Npc> getNombres() {
		Map<String, Npc> myMap = new HashMap<String, Npc>();

		myMap.put(this.getName().trim(), this);
		myMap.put(this.getFullDescription().trim(), this);
		myMap.put(this.getFullDescriptionQty().trim(), this);

		return myMap;
	}

	@Override
	public String getType() {
		return "npc";
	}
	
	@Override 
	public Npc getEntity() {
		return this;
	}

	@Override
	public ActionDTO ver() {
		return new ActionDTO(this.getName(), true, StringUtils.capitalize(this.getDescription()));
	}
}
