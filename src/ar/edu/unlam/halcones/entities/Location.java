package ar.edu.unlam.halcones.entities;

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

	public Location goTo(Location otherLocation) throws Exception {
		// Valido que halla una connecion a la otra location
		Optional<Connection> connectionOpt = this.connections.stream()
				.filter(connect -> connect.isConnectedTo(otherLocation)).findAny();

		if (!connectionOpt.isPresent()) {
			throw new Exception("No se puede ir en esa direccion");
		}

		// Me fijo si algun npc es un obstaculo para ir a la otra location
		Connection connectionToOtherLocation = connectionOpt.get();
		if (hasObstaclesWith(connectionToOtherLocation)) {
			throw new Exception(connectionToOtherLocation.getMensajeObstaculo());
		}

		return otherLocation;
	}

	private boolean hasObstaclesWith(Connection connection) {
		return this.npcs.contains(connection.getObstacle());
	}

	public String getDescription() {
		return description;
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
		String landscape = description;
	
		for(Place p : places) {
			landscape += p.getInformation();
		}
		
		for(Npc n : npcs) {
			landscape += n.getInformation();
		}
		
		for(Connection c : connections) {
			landscape += c.getInformation();
		}
		
		return landscape;
	}
	
}
