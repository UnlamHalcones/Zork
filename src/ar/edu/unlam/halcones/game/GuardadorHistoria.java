package ar.edu.unlam.halcones.game;

public class GuardadorHistoria {

	private String salida = "";
	
	public void agregarEntrada (String input) {
		agregarRegistro ("Comando: '" + input + "'");
	}
	
	public void agregarSalida (String input) {
		agregarRegistro ("-" + input + "'");
	}

	private void agregarRegistro(String linea) {
		salida = salida.concat(linea + System.lineSeparator());
	}

	public String getSalida(){
		return salida;
	}
}