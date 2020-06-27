package ar.edu.unlam.halcones.entities;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class PlaceTests {

	Place place;
	
	@Before
	public void setUp() {
		List<Item> itemList = Arrays.asList(new Item("item1", "gender1", "number1") ,
				new Item("item2", "gender2", "number2"));
		place = new Place(itemList);
	}
	
	@Test
	public void queExistaItem() {
		Assert.assertTrue(place.isItemInPlace(new Item("item1","gender1","number1")));
	}
	
	@Test
	public void queNoExistaItem() {
		Assert.assertFalse(place.isItemInPlace(new Item("item3","gender3","number3")));
	}
}
