package ar.edu.unlam.halcones.archivo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LectorDiccionarioCSV {

	
	public static Map<String, String> leerDiccionario() {

        String csvFile = "DiccionarioZork.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        
        Map<String, String> verbos = new HashMap<String, String>();
        
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                String[] linea_spliteada = line.split(cvsSplitBy);
                
                for (int i=0; i<linea_spliteada.length;i++) {
                	
                	verbos.put(linea_spliteada[0], linea_spliteada[i]);
                	System.out.println(linea_spliteada[0]);
                	System.out.println(linea_spliteada[i]);
                	System.out.println("----------------");
                	
                }

            }
            
            System.out.println(verbos);
            
            return verbos;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return verbos;
        
       
    }


	
	
}
