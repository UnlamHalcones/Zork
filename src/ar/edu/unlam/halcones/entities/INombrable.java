package ar.edu.unlam.halcones.entities;

import java.util.Map;

public interface INombrable<T> {
	Map<String, T> getNombres ();
	
	T getEntity();
	
	ActionDTO ver();
}
