package ar.edu.unlam.halcones.entities;

public abstract class GameEntity {

	private String name;
	private String gender;
	private String number;

	public GameEntity() {
		super();
	}

	public GameEntity(String name, String gender, String number) {
		super();
		this.name = name;
		this.gender = gender;
		this.number = number;
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
	
	public String getFullDescription() {
		String article = "";
		
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
	
	public String getInformation(){
		return name;
	}
}
