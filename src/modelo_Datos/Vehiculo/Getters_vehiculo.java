package modelo_Datos.Vehiculo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class Getters_vehiculo {

	@Test
	public void test_moto() {
		Vehiculo moto = new Moto("mmm111");
		assertEquals("mmm111",moto.getPatente());
		assertEquals(1,moto.getCantidadPlazas());
	}
	@Test
	public void test_auto() {
		Vehiculo auto = new Auto("aaa111",4,true);
		assertEquals("aaa111",auto.getPatente());
		assertEquals(4,auto.getCantidadPlazas());
		assertTrue(auto.isMascota());
	}
	@Test
	public void test_combi() {
		Vehiculo combi = new Combi("ccc111",10,true);
		assertEquals("ccc111",combi.getPatente());
		assertEquals(10,combi.getCantidadPlazas());
		assertTrue(combi.isMascota());
	}
}
