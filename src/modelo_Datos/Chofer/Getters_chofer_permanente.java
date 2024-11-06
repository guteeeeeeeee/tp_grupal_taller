package modelo_Datos.Chofer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.ChoferPermanente;

public class Getters_chofer_permanente {

	ChoferPermanente chofer;
	int anio_ingreso;
	int cant_hijos;
	
	@Before
	public void setUp() throws Exception {
		this.anio_ingreso = 1900;
		this.cant_hijos = 4;
		this.chofer = new ChoferPermanente("1","a",this.anio_ingreso,this.cant_hijos);
	}

	@Test
	public void test_getters() {
		int antiguedad = 2024 - this.anio_ingreso;
		assertEquals(this.anio_ingreso,this.chofer.getAnioIngreso());
		assertEquals(antiguedad,this.chofer.getAntiguedad());
		assertEquals(this.cant_hijos,this.chofer.getCantidadHijos());
	}
	
	@Test
	public void test_set_cant_hijos() {
		int cant_hijos_nuevo = 1;
		assertEquals(this.cant_hijos,this.chofer.getCantidadHijos());
		this.chofer.setCantidadHijos(cant_hijos_nuevo);
		assertEquals(cant_hijos_nuevo,this.chofer.getCantidadHijos());
	}

}
