package ar.edu.unlam.halcones.entities;

public class Character implements ITriggereable {
	private Location location;
	private Inventory inventory;
	protected String status;
	
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
	
	@Override
	public String Execute(Trigger trigger) {
		/*
		 * Esto va comentado, porque creo que despues deberíamos tener un trigger acá. La idea es para saber que le hace un item a otro item
		 * if (!triggers.contains(trigger))
			System.out.println("Accion no valida en el personaje");*/
		
		//Esto de los estados podría ser una colección, ejemplo: vidaMaximaIncrementada, ataqueIncrementado. O incluso un map
		status = trigger.getAfterTrigger();
		
		return trigger.getOnTrigger();
	}

}
