package ar.edu.unlam.halcones.entities;

import java.util.List;

public class Location extends GameEntity {
	private String description;
	private List<Place> places;
	private List<Npc> npcs;
	private List<Connection> connections;
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Location(String name, String gender, String number) {
		super(name, gender, number);
		// TODO Auto-generated constructor stub
	}
	public Location(String description, List<Place> places, List<Npc> npcs, List<Connection> connections) {
		super();
		this.description = description;
		this.places = places;
		this.npcs = npcs;
		this.connections = connections;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Place> getPlaces() {
		return places;
	}
	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	public List<Npc> getNpcs() {
		return npcs;
	}
	public void setNpcs(List<Npc> npcs) {
		this.npcs = npcs;
	}
	public List<Connection> getConnections() {
		return connections;
	}
	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}
	
	
	
	
}
