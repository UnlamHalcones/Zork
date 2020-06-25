package ar.edu.unlam.halcones.entities;

import java.util.Map;

public interface INombrable<TEntity> {
	public Map<String, TEntity> getNombres ();
}
