package ar.edu.unlam.halcones.entities;

import java.util.List;

public class Place extends GameEntity {

	private List<Item> items;

	public Place() {
		super();
	}

	public Place(String name, String gender, String number) {
		super(name, gender, number);
	}

	public Place(List<Item> items) {
		super();
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String getInformation() {
		String info = "";

		if (!items.isEmpty()) {
			info += "En " + getFullDescription() + " hay " + getFullInformationQty(items);
		} else {
			info += "Hay " + getFullDescriptionQty();
		}

		return info;
	}

	public boolean isItemInPlace(Item item) {
		return items.stream().filter(itemInPlace -> itemInPlace.equals(item)).findAny().isPresent();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

}
