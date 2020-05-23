package ar.edu.unlam.halcones.entities;

public class Trigger {
	private String type;
	private String thing;
	private String onTrigger;
	private String afterTrigger;

	public Trigger(String type, String thing, String onTrigger, String afterTrigger) {
		super();
		this.type = type;
		this.thing = thing;
		this.onTrigger = onTrigger;
		this.afterTrigger = afterTrigger;
	}

	public Trigger(String type, String thing) {
		super();
		this.type = type;
		this.thing = thing;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getThing() {
		return thing;
	}

	public void setThing(String thing) {
		this.thing = thing;
	}

	public String getOnTrigger() {
		return onTrigger;
	}

	public void setOnTrigger(String onTrigger) {
		this.onTrigger = onTrigger;
	}

	public String getAfterTrigger() {
		return afterTrigger;
	}

	public void setAfterTrigger(String afterTrigger) {
		this.afterTrigger = afterTrigger;
	}
}
