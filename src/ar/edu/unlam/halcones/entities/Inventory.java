package ar.edu.unlam.halcones.entities;

import ar.edu.unlam.halcones.game.VentanaInventario;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.*;

public class Inventory implements INombrable<Inventory> {
    private Map<Item, Integer> itemsMap;

    public Inventory(Map<Item, Integer> items) {
        super();
        this.itemsMap = items;
    }

    public Inventory() {
        super();
        this.itemsMap = new HashMap<>();
    }

    public Map<Item, Integer> getItems() {
        return this.itemsMap;
    }

    public Object[][] getItemsTable(){

        Object[][] itemTable = new Object[itemsMap.size()][2];
        Item itemIterado;
        Set<Map.Entry<Item, Integer>> entries = this.itemsMap.entrySet();
        int i = 0;
        for (Map.Entry itemEntry : entries) {
            itemIterado = (Item)itemEntry.getKey();
            itemTable[i][0]=itemIterado.getImage();
            itemTable[i][1]=itemIterado.getName() + "(x" + itemEntry.getValue() +").";
            i++;
        }

        return itemTable;
    }

    public String add(Item item) {
        if(this.itemsMap.containsKey(item)) {
            Integer cantidad = this.itemsMap.get(item);
            this.itemsMap.put(item, ++cantidad);
        } else {
            this.itemsMap.put(item, 1);
        }
        return "Agregaste " + item.getFullDescription() + " al inventario.";
    }

    public boolean add(Map<Item, Integer> itemsForInventory) {
        this.itemsMap.putAll(itemsForInventory);
        return true;
    }

    public boolean add(List<Item> itemsForInventory) {
        for(Item item : itemsForInventory) {
            this.add(item);
        }
        return true;
    }

    public String remove(Item item) {
        if (this.itemsMap.containsKey(item)) {
            this.itemsMap.remove(item);
            return StringUtils.capitalize(item.getFullDescription()) + " ya no esta en tu inventario.";
        }
        return StringUtils.capitalize(item.getFullDescription()) + " no esta en tu inventario.";
    }

    public String remove(Item item, Integer quantity) {
        if (this.itemsMap.containsKey(item)) {
            Integer cantidadActual = this.itemsMap.get(item);
            cantidadActual -= quantity;
            if(cantidadActual <= 0) {
                this.itemsMap.remove(item);
            } else {
                this.itemsMap.replace(item, cantidadActual);
                return StringUtils.capitalize("Ahora tienes (x" + cantidadActual + ") " + item.getFullDescription()) + " en tu inventario.";
            }
        }
        return StringUtils.capitalize(item.getFullDescription()) + " no esta en tu inventario.";
    }

    public String showItems() {
        if (!this.itemsMap.isEmpty()) {
            return "ver_inventario";
        } else {
            return "No tienes items en tu inventario.";
        }
    }

    public boolean hasItem(Item item) {
        return itemsMap.containsKey(item);
    }

    public boolean hasItem(String itemName) {
        return this.itemsMap.keySet()
                .stream()
                .anyMatch(item -> item.getName().equals(itemName));
    }

	@Override
	public Map<String, Inventory> getNombres() {
		Map<String,Inventory> myMap = new HashMap<String,Inventory>();
	    myMap.put("inventario", this);
	    myMap.put("mis items", this);
	    
	    return myMap;	
	}
	
	@Override 
	public Inventory getEntity() {
		return this;
	}

	@Override
	public ActionDTO ver() {
		return new ActionDTO(showItems(), true);
	}

    public void removeItemQuantity(Item item) {
        Integer integer = this.itemsMap.get(item);
        if(integer > 1) {
            this.itemsMap.put(item, --integer);
        } else {
            this.itemsMap.remove(item);
        }
    }

    public void add(Item item, int itemQuantity) {
        if(this.itemsMap.containsKey(item)) {
            Integer cantidad = this.itemsMap.get(item);
            cantidad += itemQuantity;
            this.itemsMap.put(item, cantidad);
        } else {
            this.itemsMap.put(item, itemQuantity);
        }
    }

    public void addItem(Item item) {
        this.add(item, 1);
    }

}
