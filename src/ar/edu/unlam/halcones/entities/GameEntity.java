package ar.edu.unlam.halcones.entities;

import java.util.List;

public abstract class GameEntity {

	private String name;
	private String gender;
	private String number;
	protected String status;
	private String state;

	public GameEntity() {
		super();
	}

	public GameEntity(String name) {
		super();
		this.name = name;
	}

	public GameEntity(String name, String gender, String number) {
		super();
		this.name = name;
		this.gender = gender;
		this.number = number;
		// Todos las entidades empiezan en estado normal. A efectuarse un trigger, el
		// estado cambia
		this.status = "normal";
	}

	public GameEntity(String name, String state) { // Only test
		super();
		this.name = name;
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFullDescription() {
		String article = "";

		// Esto se puede poner en menos IFs, pero así queda mas legible
		if (gender == "male" && number == "singular")
			article = "el";

		if (gender == "female" && number == "singular")
			article = "la";

		if (gender == "male" && number == "plural")
			article = "los";

		if (gender == "female" && number == "plural")
			article = "las";

		return article + " " + this.name;

	}

	public String getFullDescriptionQty() {
		String article = "";

		if (gender == "male" && number == "singular")
			article = "un";
		if (gender == "female" && number == "singular")
			article = "una";
		if (gender == "male" && number == "plural")
			article = "unos";
		if (gender == "female" && number == "plural")
			article = "unas";

		return article + " " + this.name;
	}

	// TODO: DEBERIAMOS USAR ESTOS METODOS EN GAMEENTITY? ME REFIERO CONVERSORES A
	// TEXTOS. REVISAR NOMBRES DE METODOS.
	public String getFullInformationQty(List<? extends GameEntity> list) {
		StringBuilder info = new StringBuilder("");

		for (GameEntity l : list) {
			// INFO. LA LISTA TIENE +1 ELEMENTO Y ES LA ULTIMA ITERACION.
			if (list.size() > 1 && l == list.get(list.size() - 1)) {
				info.setCharAt(info.lastIndexOf(","), ' ');
				info.append("y " + l.getFullDescriptionQty() + '.');
			} else if (list.size() > 1) {
				info.append(l.getFullDescriptionQty() + ", ");
			} else {
				info.append(l.getFullDescriptionQty() + '.');
			}
		}
		return info.toString();
	}

	public String getInformation() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		GameEntity other = (GameEntity) obj;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

}
