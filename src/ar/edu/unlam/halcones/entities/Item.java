package ar.edu.unlam.halcones.entities;

public class Item extends GameObject {

	private int usos;
	private boolean enInventario;

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(String name, String gender, String number, String initialState) {
		super(name, gender, number, initialState);
		// TODO Auto-generated constructor stub
	}

	public boolean usarObjeto() { // retorna si el objeto tiene usos o no

		if (this.usos == 0)
			return false;
		else
			this.usos--;
		return true;

	}

	public boolean esValido() {

		if (this.usos == 0)
			return false;
		else
			return true;

	}

	public boolean getEnInventario() {
		return this.enInventario;
	}

	public void setEnInventario(boolean enInventario) {
		this.enInventario = enInventario;
	}


	
	// -------------------------------	 geters y seters --------------------
	
	public int getUsos() {
		return usos;
	}

	public void setUsos(int usos) {
		this.usos = usos;
	}
}
