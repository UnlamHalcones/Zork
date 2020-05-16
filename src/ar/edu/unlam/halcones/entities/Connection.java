package ar.edu.unlam.halcones.entities;

public class Connection {
	private String direction;
	private Location location;
	private GameEntity obstacle;
	public Connection(String direction, Location location, GameEntity obstacle) {
		super();
		this.direction = direction;
		this.location = location;
		this.obstacle = obstacle;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public GameEntity getObstacle() {
		return obstacle;
	}
	public void setObstacle(GameEntity obstacle) {
		this.obstacle = obstacle;
	}
	
	public String getInformation(){
		String info = "Al " + direction + " hay " + location;
		
		return info;
	}
	
}
