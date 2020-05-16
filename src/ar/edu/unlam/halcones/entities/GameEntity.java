package ar.edu.unlam.halcones.entities;

import java.util.List;

public abstract class GameEntity {

	private String name;
	private String gender;
	private String number;

	public GameEntity() {
		super();
	}

	public GameEntity(String name, String gender, String number) { // Ingresarle las acciones, puede ser en un vector
		super();
		this.name = name;
		this.gender = gender;
		this.number = number;

	}

}
