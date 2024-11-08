package controlador.Getters_setters;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloNegocio.Empresa;
import persistencia.*;
import vista.IVista;
import util.Constantes;
import util.Mensajes;
import vista.Ventana;

public class Getters_setters {

	IVista vista;
	Controlador controlador;
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
	}

	@Test
	public void test_getVista() {
		assertNotNull(this.controlador.getVista());
	}
	
	@Test
	public void test_setVista() {
		IVista vista_nueva = new Ventana();
		this.controlador.setVista(vista_nueva);
		assertEquals(vista_nueva,this.controlador.getVista());
	}
	
	@Test
	public void test_getPersistencia() {
		assertNotNull(this.controlador.getPersistencia());
	}
	
	@Test
	public void test_setPersistencia() {
		IPersistencia<Serializable> persistencia_nueva = new PersistenciaBIN();
		this.controlador.setPersistencia(persistencia_nueva);
		assertEquals(persistencia_nueva,this.controlador.getPersistencia());
	}
	
	@Test
	public void test_getFileName() {
		assertNotNull("empresa.bin",this.controlador.getFileName());
	}
	
	@Test
	public void test_setFileName() {
		String file_name_nuevo = "salida_test.txt";
		this.controlador.setFileName(file_name_nuevo);
		assertEquals(file_name_nuevo,this.controlador.getFileName());
	}
	
}
