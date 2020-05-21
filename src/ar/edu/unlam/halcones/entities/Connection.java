package ar.edu.unlam.halcones.entities;

public class Connection {
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

}
