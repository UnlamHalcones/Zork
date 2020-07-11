package ar.edu.unlam.halcones.entities;

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
        /*this.items = new ArrayList<>();*/
        this.itemsMap = new HashMap<>();
    }

    public Map<Item, Integer> getItems() {
        return this.itemsMap;
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
            String dataInventario = "Tienes los siguientes items en el inventario:\n";
            Set<Map.Entry<Item, Integer>> entries = this.itemsMap.entrySet();
            for (Map.Entry itemEntry : entries) {
                Item key = (Item) itemEntry.getKey();
                Integer value = (Integer) itemEntry.getValue();
                dataInventario = dataInventario.concat("- " + key.getName() + "(x" + value +").\n");
            }
            return dataInventario;
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
	public String ver() {
		return showItems();
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

    /*private List<Item> items;

    public Inventory(List<Item> items) {
        super();
        this.items = items;
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
    public boolean hasItem(String itemName) {
        return items.stream().anyMatch(item -> item.getName().equals(itemName));
    }*/
}
