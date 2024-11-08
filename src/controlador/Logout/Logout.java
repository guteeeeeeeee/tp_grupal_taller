package controlador.Logout;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloNegocio.Empresa;
import testeo_gui.FalsoOptionPane;

public class Logout {

	Controlador controlador;
	String file_name_nuevo;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
		this.op = new FalsoOptionPane();
		this.controlador.getVista().setOptionPane(this.op);
		this.file_name_nuevo = "./nueva/asdasd/dasd/asd/archivo.bin";
		this.controlador.setFileName(file_name_nuevo);
		
		String nombre_usuario = "patita";
		String password = "ddd";
		Empresa.getInstance().agregarCliente(nombre_usuario, password, file_name_nuevo);
		Empresa.getInstance().login(nombre_usuario, password);
	}
	
	@Test
	public void logout_no_puede_escribir() {
		assertNotNull(Empresa.getInstance().getUsuarioLogeado());
		this.controlador.logout();
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		assertTrue(this.op.getMensaje().contains("(No such file")); //testeo que llame al metodo escribir()
	}

	@After
	public void limpiar() {
		Empresa.getInstance().getClientes().clear();
	}
}
