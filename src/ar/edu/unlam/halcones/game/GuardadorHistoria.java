package ar.edu.unlam.halcones.game;

public class GuardadorHistoria {

	private String salida = "";
	
	public void agregarEntrada (String input) {
		agregarRegistro (input);
	}
	
	public void agregarSalida (String input) {
		agregarRegistro ("-" + input + "'");
	}

	private void agregarRegistro(String linea) {
		salida = salida.concat(linea + System.lineSeparator() + System.lineSeparator());
	}

	public String getSalida(){
		return salida;
	}
}