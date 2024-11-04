package modeloDatos.Chofer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.ChoferPermanente;

public class GetSueldoBruto_chofer_permanente {
	double sueldo_basico = 500000;
	
	@Test
	public void test_sin_antiguedad_ni_hijos() {
		ChoferPermanente chofer = new ChoferPermanente("1","a",2024,0);
		assertEquals(this.sueldo_basico,chofer.getSueldoBruto(),0.1);
	}
	
	@Test
	public void test_1_antiguedad_1_hijo() {
		double sueldo = this.sueldo_basico + this.sueldo_basico * 0.05 * 1 + this.sueldo_basico * 0.07 * 1;
		ChoferPermanente chofer = new ChoferPermanente("1","a",2023,1);
		assertEquals(sueldo,chofer.getSueldoBruto(),0.1);
	}
	
	@Test
	public void test_20_antiguedad_2_hijo() {
		double sueldo = this.sueldo_basico + this.sueldo_basico * 0.05 * 20 + this.sueldo_basico * 0.07 * 2;
		ChoferPermanente chofer = new ChoferPermanente("1","a",2004,2);
		assertEquals(sueldo,chofer.getSueldoBruto(),0.1);
	}
	
	@Test
	public void test_21_antiguedad_5_hijo() {
		double sueldo = this.sueldo_basico + this.sueldo_basico * 0.05 * 20 + this.sueldo_basico * 0.07 * 5;
		ChoferPermanente chofer = new ChoferPermanente("1","a",2003,5);
		assertEquals(sueldo,chofer.getSueldoBruto(),0.1);
	}
}
