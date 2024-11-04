package modeloDatos.Chofer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import modeloDatos.ChoferPermanente;

public class Getters_chofer {

	Chofer chofer;
	String dni;
	String nombre;
	int anio_ingreso;
	int cant_hijos;
	double sueldo_basico = 500000;
	
	@Before
	public void setUp() throws Exception {
		this.anio_ingreso = 2020;
		this.cant_hijos = 4;
		this.dni = "1";
		this.nombre = "a";
		this.chofer = new ChoferPermanente(this.dni,this.nombre,this.anio_ingreso,this.cant_hijos);
	}

	@Test
	public void test_getters() {
		double sueldo_bruto = this.sueldo_basico + this.sueldo_basico * 0.05 * 4 + this.sueldo_basico * 0.07 * 4;
		assertEquals("1",this.chofer.getDni());
		assertEquals("a",this.chofer.getNombre());
		assertEquals(this.sueldo_basico,this.chofer.getSueldoBasico(),0.1);
		assertEquals(sueldo_bruto,this.chofer.getSueldoBruto(),0.1);
		assertEquals(sueldo_bruto*0.86,this.chofer.getSueldoNeto(),0.1);
	}
	
	@Test
	public void test_set_sueldo_basico() {
		double sueldo_basico_nuevo = 2000;
		double sueldo_bruto_nuevo = sueldo_basico_nuevo + sueldo_basico_nuevo * 0.05 * 4 + sueldo_basico_nuevo * 0.07 * 4;
		this.chofer.setSueldoBasico(sueldo_basico_nuevo);
		assertEquals(sueldo_basico_nuevo,this.chofer.getSueldoBasico(),0.1);
		assertEquals(sueldo_bruto_nuevo,this.chofer.getSueldoBruto(),0.1);
		assertEquals(sueldo_bruto_nuevo*0.86,this.chofer.getSueldoNeto(),0.1);
	}
	
	@After
	public void limpio() {
		this.chofer.setSueldoBasico(this.sueldo_basico);
	}

}
