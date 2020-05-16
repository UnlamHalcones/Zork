package ar.edu.unlam.halcones.entities;

public class Character {
	private Location location;
	private Inventory inventory;
	public Character(Location location, Inventory inventory) {
		super();
		this.location = location;
		this.inventory = inventory;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public String lookAround() {
		return location.getInformation();
	}
	
}
