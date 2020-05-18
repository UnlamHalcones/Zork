package ar.edu.unlam.halcones.entities;

public class Character implements ITriggereable {
	private Location location;
	private Inventory inventory;
	protected String status;
	

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
	public String lookAround() {
		return location.getInformation();
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
		 * Esto va comentado, porque creo que despues deber�amos tener un trigger ac�. La idea es para saber que le hace un item a otro item
		 * if (!triggers.contains(trigger))
			System.out.println("Accion no valida en el personaje");*/
		
		//Esto de los estados podr�a ser una colecci�n, ejemplo: vidaMaximaIncrementada, ataqueIncrementado. O incluso un map
		status = trigger.getAfterTrigger();
		
		return trigger.getOnTrigger();
	}

}
