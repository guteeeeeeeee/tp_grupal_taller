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
import util.Mensajes;
import vista.IVista;
import vista.Ventana;

public class registro_y_login_erroneo {

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
	public void test_registro_y_logeo_password_incorrecta() {
		when(vista_mock.getRegUsserName()).thenReturn(user_name);
		when(vista_mock.getRegPassword()).thenReturn(password);
		when(vista_mock.getRegConfirmPassword()).thenReturn(password);
		when(vista_mock.getRegNombreReal()).thenReturn(nombre_real);
		
		assertEquals(0,Empresa.getInstance().getClientes().size());
		this.controlador.registrar();
		assertEquals(1,Empresa.getInstance().getClientes().size());

		when(vista_mock.getUsserName()).thenReturn(user_name);
		when(vista_mock.getPassword()).thenReturn("otra");
		
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		this.controlador.login();
		assertEquals("no dice que la password es incorrecta",Mensajes.PASS_ERRONEO.getValor(),this.op.getMensaje());
		assertNull(Empresa.getInstance().getUsuarioLogeado());
	}
	
	@Test
	public void test_registro_y_logeo_username_incorrecto() {
		when(vista_mock.getRegUsserName()).thenReturn(user_name);
		when(vista_mock.getRegPassword()).thenReturn(password);
		when(vista_mock.getRegConfirmPassword()).thenReturn(password);
		when(vista_mock.getRegNombreReal()).thenReturn(nombre_real);
		
		assertEquals(0,Empresa.getInstance().getClientes().size());
		this.controlador.registrar();
		assertEquals(1,Empresa.getInstance().getClientes().size());

		when(vista_mock.getUsserName()).thenReturn("no_estoy");
		when(vista_mock.getPassword()).thenReturn(password);
		
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		this.controlador.login();
		assertEquals("no dice que el username no esta registado",Mensajes.USUARIO_DESCONOCIDO.getValor(),this.op.getMensaje());
		assertNull(Empresa.getInstance().getUsuarioLogeado());
	}

	@After
	public void limpio() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
	}
}
