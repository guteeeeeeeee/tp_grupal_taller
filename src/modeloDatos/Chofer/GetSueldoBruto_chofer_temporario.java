package modeloDatos.Chofer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;

public class GetSueldoBruto_chofer_temporario {

	double sueldo_basico = 500000;
	
	@Test
	public void test_sin_antiguedad_ni_hijos() {
		ChoferTemporario chofer = new ChoferTemporario("1","a");
		assertEquals(this.sueldo_basico,chofer.getSueldoBruto(),0.1);
	}

}
