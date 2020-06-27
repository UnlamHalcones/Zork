package ar.edu.unlam.halcones.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EndGame {
	private String condition;
	private String action;
	private String thing;
	private String description;

	@JsonCreator
	public EndGame(@JsonProperty("condition") String condition,
				   @JsonProperty("action") String action,
				   @JsonProperty("thing") String thing,
				   @JsonProperty("description") String description) {
		super();
		this.condition = condition;
		this.action = action;
		this.thing = thing;
		this.description = description;
	}
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getThing() {
		return thing;
	}

	public void setThing(String thing) {
		this.thing = thing;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
