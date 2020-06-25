package ar.edu.unlam.halcones.entities;

import java.util.HashMap;
import java.util.Map;

public class Connection implements INombrable<Connection> {
	private String direction;
	private Location location;
	private Npc obstacle;

	public Connection(String direction, Location location, Npc obstacle) {
		super();
		this.direction = direction;
		this.location = location;
		this.obstacle = obstacle;
	}

	public String getMensajeObstaculo() {
		return this.obstacle.getDescription();
	}

	public boolean isConnectedTo(Location otherLocation) {
		return this.location.equals(otherLocation);
	}

	public Npc getObstacle() {
		return obstacle;
	}

	public String getInformation() {
		return " Al " + direction + " se puede ir hacia " + location.getFullDescriptionQty();
	}

	@Override
	public Map<String, Connection> getNombres() {

		Map<String,Connection> myMap = new HashMap<String,Connection>();
	    myMap.put(this.direction, this);
	    
	    return myMap;	
		
	}

}
