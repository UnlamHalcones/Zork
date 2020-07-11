package ar.edu.unlam.halcones.interprete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GuardadorHistoria {

	private String directoryName = "JuegosAnteriores";
	
	private String fileName;
	
	private String fullPath;
	

	public GuardadorHistoria(String fileName) {
		super();
		this.fileName = fileName+ ".txt";
		this.fullPath = directoryName + File.separator + this.fileName;

		checkFolder();
		File archivo = new File(this.fullPath);
		try {
			archivo.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void agregarEntrada (String input) {
		agregarRegistro ("Comando ingresado: '" + input + "'");
	}
	
	public void agregarSalida (String input) {
		agregarRegistro ("Respuesta: '" + input + "'");
	}

	public void agregarRegistro(String linea) {

		BufferedWriter out;

		try {
			out = new BufferedWriter(new FileWriter(this.fullPath, true));
			out.write(linea + "\n");
			out.close();
		} catch (IOException e) {
			System.out.println("Error al leer el archivo");

		}
	}

	private void checkFolder() {
		File directory = new File(directoryName);
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

}