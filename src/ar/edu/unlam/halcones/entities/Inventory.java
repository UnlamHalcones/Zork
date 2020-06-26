package ar.edu.unlam.halcones.entities;

import com.sun.xml.internal.ws.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory implements INombrable<Inventory> {
    private List<Item> items;

    public Inventory(List<Item> items) {
        super();
        this.items = items;
    }

    public Inventory() {
        super();
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public String add(Item item) {
        if (!this.items.contains(item)) {
            this.items.add(item);
            return "Agregaste " + item.getFullDescription() + " al inventario.";
        }
        return "Ya tienes ese item en el inventario";
    }

    public boolean add(List<Item> itemsForInventory) {
        return this.items.addAll(itemsForInventory);
    }

    public String remove(Item item) {
        if (this.items.contains(item)) {
            this.items.remove(item);
            return StringUtils.capitalize(item.getFullDescription()) + " ya no esta en tu inventario.";
        }
        return StringUtils.capitalize(item.getFullDescription()) + " no esta en tu inventario.";
    }

    public String showItems() {
        if (!this.items.isEmpty()) {
            String dataInventario = "Tienes los siguientes items en el inventario:\n";
            for (Item item : getItems()) {
                dataInventario = dataInventario.concat("- " + item.getName() + ".\n");
            }
            return dataInventario;
        } else {
            return "No tienes items en tu inventario.";
        }
    }

    public boolean hasItem(Item item) {
        return items.contains(item);
    }

	@Override
	public Map<String, Inventory> getNombres() {
		Map<String,Inventory> myMap = new HashMap<String,Inventory>();
	    myMap.put("inventario", this);
	    myMap.put("mis items", this);
	    
	    return myMap;	
	}
}
