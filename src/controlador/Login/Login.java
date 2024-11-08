package controlador.Login;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.SinViajesException;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import util.Constantes;
import util.Mensajes;
import testeo_gui.FalsoOptionPane;
import vista.IVista;
import vista.Ventana;

public class Login {

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
	public void test_login_correcto() throws SinViajesException {
		String user_name = "pepe123";
		String password = "123";
		when(vista_mock.getUsserName()).thenReturn(user_name);
		when(vista_mock.getPassword()).thenReturn(password);
		
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		this.controlador.login();
		assertEquals(user_name,Empresa.getInstance().getUsuarioLogeado().getNombreUsuario());
		assertEquals(password,Empresa.getInstance().getUsuarioLogeado().getPass());
	}
	
	@Test
	public void test_login_password_incorrecta() throws SinViajesException {
		String user_name = "pepe123";
		String password = "abc";
		when(vista_mock.getUsserName()).thenReturn(user_name);
		when(vista_mock.getPassword()).thenReturn(password);
		
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		this.controlador.login();
		assertEquals("no dice que la password es incorrecta",Mensajes.PASS_ERRONEO.getValor(),this.op.getMensaje());
		assertNull(Empresa.getInstance().getUsuarioLogeado());
	}
	
	@Test
	public void test_login_usuario_inexistente() throws SinViajesException {
		String user_name = "aaa";
		String password = "123";
		when(vista_mock.getUsserName()).thenReturn(user_name);
		when(vista_mock.getPassword()).thenReturn(password);
		
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		this.controlador.login();
		assertEquals("no dice que el usuario es inexistente",Mensajes.USUARIO_DESCONOCIDO.getValor(),this.op.getMensaje());
		assertNull(Empresa.getInstance().getUsuarioLogeado());
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
	}

}
