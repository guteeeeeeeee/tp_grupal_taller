package modelo_Datos.Chofer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.ChoferPermanente;

public class GetSueldoBruto_chofer_permanente {
	double sueldo_basico = 500000;
	
	@Test
	public void test_1900_2_hijos() {
		double sueldo = this.sueldo_basico + this.sueldo_basico * 0.05 * 20 + this.sueldo_basico * 0.07 * 2;
		ChoferPermanente chofer = new ChoferPermanente("1","a",1900,2);
		assertEquals(sueldo,chofer.getSueldoBruto(),0.1);
	}
	
	@Test
	public void test_2024_0_hijos() {
		double sueldo = this.sueldo_basico + this.sueldo_basico * 0.05 * 0 + this.sueldo_basico * 0.07 * 0;
		ChoferPermanente chofer = new ChoferPermanente("1","a",2024,0);
		assertEquals(sueldo,chofer.getSueldoBruto(),0.1);
	}
	
	@Test
	public void test_3000_1_hijo() {
		double sueldo = this.sueldo_basico + this.sueldo_basico * 0.05 * 0 + this.sueldo_basico * 0.07 * 1;
		ChoferPermanente chofer = new ChoferPermanente("1","a",3000,1);
		assertEquals(sueldo,chofer.getSueldoBruto(),0.1);
	}
	
}
