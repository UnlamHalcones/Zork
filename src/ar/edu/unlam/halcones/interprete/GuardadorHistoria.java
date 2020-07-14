package ar.edu.unlam.halcones.interprete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GuardadorHistoria {

	
	private String salida = "";
	
	public void agregarEntrada (String input) {
		agregarRegistro ("Comando: '" + input + "'");
	}
	
	public void agregarSalida (String input) {
		agregarRegistro ("-" + input + "'");
	}

	public void agregarRegistro(String linea) {
		
		salida.concat(linea + System.lineSeparator());
	}

	public String getSalida(){
		return salida;
	}
}