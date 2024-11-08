package modelo_Negocios.Empresa.Getters_Setters;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import modeloNegocio.Empresa;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.ChoferPermanente;

public class Empresa_getTotalSalarios {

	@Test
	public void getTotalSalarios_sin_choferes() {
		assertEquals(0,Empresa.getInstance().getTotalSalarios(),0.1);
	}
	
	@Test
	public void getTotalSalarios_temporario() throws ChoferRepetidoException {
		Chofer choferTemp = new ChoferTemporario("111","hugo");
		Empresa.getInstance().agregarChofer(choferTemp);
		assertEquals(430000,Empresa.getInstance().getTotalSalarios(),0.1);
	}
	
	@Test
	public void getTotalSalarios_permanente() throws ChoferRepetidoException {
		Chofer choferPerm = new ChoferPermanente("111","hugo",2019,2);
		Empresa.getInstance().agregarChofer(choferPerm);
		assertEquals(597700,Empresa.getInstance().getTotalSalarios(),0.1);
	}
	
	@Test
	public void getTotalSalarios_temporario_y_permanente() throws ChoferRepetidoException {
		Chofer choferTemp = new ChoferTemporario("111","hugo");
		Empresa.getInstance().agregarChofer(choferTemp);
		Chofer choferPerm = new ChoferPermanente("222","juan",2019,2);
		Empresa.getInstance().agregarChofer(choferPerm);
		assertEquals(430000+597700,Empresa.getInstance().getTotalSalarios(),0.1);
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().getChoferes().clear();
	}

}
