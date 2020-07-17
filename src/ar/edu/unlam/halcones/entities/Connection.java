package ar.edu.unlam.halcones.entities;

import java.util.HashMap;
import java.util.Map;

public class Connection implements INombrable<Location> {
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

	public void removeNpc(Npc npc) {
		if(this.obstacle != null && this.obstacle.equals(npc)) {
			this.obstacle = null;
		}
	}

	@Override
	public Map<String, Location> getNombres() {

		Map<String,Location> myMap = new HashMap<String,Location>();
	    myMap.put(this.direction, this.location);
	    
	    return myMap;	
	}

	@Override 
	public Location getEntity() {
		return this.location;
	}

	@Override
	public ActionDTO ver() {
		return new ActionDTO(this.getInformation(), true);
	}
	
	public String getDirection() {
		return direction;
	}
	
	public Location getLocation() {
		return location;
	}
}
