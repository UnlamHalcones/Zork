package ar.edu.unlam.halcones.entities;

public class Character {
	private Location location;
	private Inventory inventory;

	public Character(Location location) {
		this.location = location;
	}

	public Character(Location location, Inventory inventory) {
		super();
		this.location = location;
		this.inventory = inventory;
	}

	public void moveTo(Location otherLocation) {
		try {
			this.location = this.location.goTo(otherLocation);
			System.err.println(this.location.getDescription());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
