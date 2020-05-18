package ar.edu.unlam.halcones.entities;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionTests {

	Connection connection;
	
	@Test
	public void queTieneConeccionConUnaLocation() {
		Location location = new Location("TABERNA", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		connection = new Connection("south", location, null);

		Assert.assertTrue(connection.isConnectedTo(location));
	}
	
	@Test
	public void queNOTieneConeccionConUnaLocation() {
		Location location = new Location("TABERNA", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());
		Location otherLocation = new Location("MUELLE", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());
		
		connection = new Connection("south", location, null);
		
		Assert.assertFalse(connection.isConnectedTo(otherLocation));
	}
	
	@Test
	public void queDevuelveElMensajeDelObstaculo() {
		Location location = new Location("TABERNA", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());
		
		Trigger trigger = new Trigger("item", "rociador", "on trigger", "after trigger");
		
		final String descripcionNpc = "Pirata fanstasma descripcion";
		Npc npc = new Npc(descripcionNpc,
				"Pirata fantasma", Arrays.asList(trigger));
		
		connection = new Connection("south", location, npc);
		
		Assert.assertEquals(descripcionNpc, connection.getMensajeObstaculo());
	}
}
