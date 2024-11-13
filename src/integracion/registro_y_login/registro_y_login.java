package integracion.registro_y_login;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import vista.IVista;
import vista.Ventana;

public class registro_y_login {

	Controlador controlador;
	IVista vista_mock;
	FalsoOptionPane op;
	String user_name = "jorge123";
	String password = "12345";
	String nombre_real = "jorge alvarez";	
	
	@Before
	public void setUp() throws Exception {		
		this.controlador = new Controlador();
		this.vista_mock = mock(Ventana.class);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_registro_y_logeo() {
		when(vista_mock.getRegUsserName()).thenReturn(user_name);
		when(vista_mock.getRegPassword()).thenReturn(password);
		when(vista_mock.getRegConfirmPassword()).thenReturn(password);
		when(vista_mock.getRegNombreReal()).thenReturn(nombre_real);
		
		assertEquals(0,Empresa.getInstance().getClientes().size());
		this.controlador.registrar();
		assertEquals(1,Empresa.getInstance().getClientes().size());

		when(vista_mock.getUsserName()).thenReturn(user_name);
		when(vista_mock.getPassword()).thenReturn(password);
		
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		this.controlador.login();
		assertNotNull(Empresa.getInstance().getUsuarioLogeado());
		assertEquals(user_name,Empresa.getInstance().getUsuarioLogeado().getNombreUsuario());
		assertEquals(password,Empresa.getInstance().getUsuarioLogeado().getPass());
	}
	
	@Test
	public void test_registro_y_logeo_username_admin() {
		when(vista_mock.getRegUsserName()).thenReturn("admin");
		when(vista_mock.getRegPassword()).thenReturn(password);
		when(vista_mock.getRegConfirmPassword()).thenReturn(password);
		when(vista_mock.getRegNombreReal()).thenReturn(nombre_real);
		
		assertEquals(0,Empresa.getInstance().getClientes().size());
		this.controlador.registrar();
		assertEquals(1,Empresa.getInstance().getClientes().size());

		when(vista_mock.getUsserName()).thenReturn("admin");
		when(vista_mock.getPassword()).thenReturn(password);
		
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		this.controlador.login();
		assertNotNull(Empresa.getInstance().getUsuarioLogeado());
		assertFalse("un cliente con username admin se logea como admin",Empresa.getInstance().isAdmin());
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
	}

}
