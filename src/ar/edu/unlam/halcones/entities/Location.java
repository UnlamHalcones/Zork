package ar.edu.unlam.halcones.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Location extends GameEntity {
	private String description;
	private List<Place> places;
	private List<Npc> npcs;
	private List<Connection> connections;

	public Location() {
		super();
	}
	
	public Location(String name) {
		super(name);
	}

	public Location(String name, String gender, String number) {
		super(name, gender, number);
	}

	public Location(String description, List<Place> places, List<Npc> npcs, List<Connection> connections) {
		super();
		this.description = description;
		this.places = places;
		this.npcs = npcs;
		this.connections = connections;
	}

	public Location(String name, String gender, String number, String description, List<Place> places, List<Npc> npcs) {
		super(name, gender, number);
		this.description = description;
		this.places = places;
		this.npcs = npcs;
	}

	public String goTo(Location otherLocation) {
		// Valido que halla una connecion a la otra location
		Optional<Connection> connectionOpt = this.connections.stream()
				.filter(connect -> connect.isConnectedTo(otherLocation)).findAny();

		// TODO Si no puedo ir en esa direccion puedo devolver la misma location (this)
		if (!connectionOpt.isPresent()) {
			System.err.println("No se puede ir en esa direccion");
			return "No se puede ir en esa direccion";
		}

		// Me fijo si algun npc es un obstaculo para ir a la otra location
		Connection connectionToOtherLocation = connectionOpt.get();
		// TODO Si hay un obstaculo tambien devuelvo la misma location, pero como digo que el problema es el obstaculo?
		if (hasObstaclesWith(connectionToOtherLocation)) {
			System.err.println(connectionToOtherLocation.getMensajeObstaculo());
			return connectionToOtherLocation.getMensajeObstaculo();
		}

		return "OK";
	}

	private boolean hasObstaclesWith(Connection connection) {
		return this.npcs.contains(connection.getObstacle());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connections == null) ? 0 : connections.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((npcs == null) ? 0 : npcs.hashCode());
		result = prime * result + ((places == null) ? 0 : places.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (connections == null) {
			if (other.connections != null)
				return false;
		} else if (!connections.equals(other.connections))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (npcs == null) {
			if (other.npcs != null)
				return false;
		} else if (!npcs.equals(other.npcs))
			return false;
		if (places == null) {
			if (other.places != null)
				return false;
		} else if (!places.equals(other.places))
			return false;
		return true;
	}

	@Override
	public String getInformation() {
		String landscape = description + '.';
	
		if(!places.isEmpty())
			for(Place p : places) {
				landscape += p.getInformation();
			}
		
		if(!npcs.isEmpty()) {
			landscape += " Hay " + getFullInformationQty(npcs);
		}
		
		if(!connections.isEmpty())
			for(Connection c : connections) {
				landscape += c.getInformation();
			}
		
		return landscape;
	}

	public boolean isItemInLocation(Item item) {
		// Retorno true si el item se encuentra en algun place de la location
		return places.stream().filter(place -> place.isItemInPlace(item)).findAny().isPresent();
	}
	
	public boolean isItemInLocation(Item item, Place place) {
		// Retorno true si el item se encuentra en algun place de la location
		if(!places.contains(place)) {
			return false;
		}
		Optional<Place> desiredPlaceOpt = places.stream().filter(p -> p.equals(place)).findAny();
		if(!desiredPlaceOpt.isPresent()) {
			return false;
		}
		Place desiredPlace = desiredPlaceOpt.get();
		return desiredPlace.isItemInPlace(item);
	}

	public boolean isNpcInLocation(Npc npc) {
		return this.npcs.contains(npc);
	}

	public boolean addConnection(Connection connection) {
		if(this.connections == null) {
			this.connections = new LinkedList<>();
		}
		return this.connections.add(connection);
	}
}
