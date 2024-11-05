package controlador.Vista;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import vista.*;

public class Vista_getters_setters {

	Controlador controlador;
	
	//Por defecto inicializa el atributo vista (de tipo IVista) con un objeto de tipo Ventana y la hace visible.
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
	}

	@Test
	public void test_get_vista() {
		assertNotNull(this.controlador.getVista());
	}
	
	@Test
	public void test_set_vista() {
		IVista nueva_vista = new Ventana();
		assertNotEquals(nueva_vista,this.controlador.getVista());
		this.controlador.setVista(nueva_vista);
		assertEquals(nueva_vista,this.controlador.getVista());
	}
}
