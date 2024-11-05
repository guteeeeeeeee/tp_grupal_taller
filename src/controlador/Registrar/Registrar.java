package controlador.Registrar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import excepciones.SinViajesException;
import modeloNegocio.Empresa;
import testeo_gui.FalsoOptionPane;
import vista.IVista;
import vista.Ventana;

public class Registrar {

	Controlador controlador;
	IVista vista_mock;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("pepe123", "123", "user test");
		
		this.controlador = new Controlador();
		this.vista_mock = mock(Ventana.class);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_registrar_correcto() {
		String user_name = "jorge123";
		String password = "12345";
		String nombre_real = "jorge alvarez";
		
		when(vista_mock.getRegUsserName()).thenReturn(user_name);
		when(vista_mock.getRegPassword()).thenReturn(password);
		when(vista_mock.getRegConfirmPassword()).thenReturn(password);
		when(vista_mock.getRegNombreReal()).thenReturn(nombre_real);
		
		assertEquals(1,Empresa.getInstance().getClientes().size());
		this.controlador.registrar();
		assertEquals(2,Empresa.getInstance().getClientes().size());
		assertEquals(user_name,Empresa.getInstance().getClientes().get(user_name).getNombreUsuario());
		assertEquals(password,Empresa.getInstance().getClientes().get(user_name).getPass());
		assertEquals(nombre_real,Empresa.getInstance().getClientes().get(user_name).getNombreReal());
	}
	
	@Test
	public void test_registrar_pass_confirm_no_coinciden() {
		String user_name = "jorge123";
		String password = "12345";
		String confirm = "ababab";
		String nombre_real = "jorge alvarez";
		
		when(vista_mock.getRegUsserName()).thenReturn(user_name);
		when(vista_mock.getRegPassword()).thenReturn(password);
		when(vista_mock.getRegConfirmPassword()).thenReturn(confirm);
		when(vista_mock.getRegNombreReal()).thenReturn(nombre_real);
		
		assertEquals(1,Empresa.getInstance().getClientes().size());
		this.controlador.registrar();
		assertEquals("La contrasena y su confirmacion no coinciden",this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getClientes().size());
	}
	
	@Test
	public void test_registrar_repetido() throws SinViajesException {
		String user_name = "pepe123";
		String password = "12345";
		String nombre_real = "jorge alvarez";
		
		when(vista_mock.getRegUsserName()).thenReturn(user_name);
		when(vista_mock.getRegPassword()).thenReturn(password);
		when(vista_mock.getRegConfirmPassword()).thenReturn(password);
		when(vista_mock.getRegNombreReal()).thenReturn(nombre_real);
		
		assertEquals(1,Empresa.getInstance().getClientes().size());
		this.controlador.registrar();
		assertEquals("Usuario repetido",this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getClientes().size());
	}
	
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
	}

}
