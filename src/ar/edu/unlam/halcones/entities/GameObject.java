package ar.edu.unlam.halcones.entities;

public class GameObject extends GameEntity {

	private String state;

	public GameObject() {

	}

	public GameObject(String name, String gender, String number, String initialState) {

		super(name, gender, number);
		this.state = initialState;

	}

	public void changeState(String newState) {

		this.state = newState;

	}

	public String getState() {
		return state;
	}
}
