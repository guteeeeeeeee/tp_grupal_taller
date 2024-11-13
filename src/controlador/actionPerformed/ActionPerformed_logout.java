package controlador.actionPerformed;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import modeloDatos.Administrador;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import util.Constantes;
import util.Mensajes;
import vista.IVista;
import vista.Ventana;

public class ActionPerformed_logout {

	Controlador controlador;
	IVista vista_mock;
	Chofer chofer;
	Vehiculo moto;
	Pedido pedido;
	Cliente user;
	String nombre_usuario;
	String password;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.vista_mock = mock(Ventana.class);
		this.nombre_usuario = "alfa";
		this.password = "alfa123";
		Empresa.getInstance().agregarCliente(nombre_usuario, password, "alfonso");
		
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(this.vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_logout_cliente() throws UsuarioNoExisteException, PasswordErroneaException {
		Cliente log_sistema;
		Cliente logeado = (Cliente) Empresa.getInstance().login(nombre_usuario, password);
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn(Constantes.CERRAR_SESION_CLIENTE);
		
		log_sistema = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		assertEquals(logeado.getNombreUsuario(),log_sistema.getNombreUsuario());
		this.controlador.actionPerformed(mockEvent);
		log_sistema = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		assertNull(log_sistema);
	}
	
	@Test
	public void test_logout_admin() throws UsuarioNoExisteException, PasswordErroneaException {
		Administrador admin_logeado = (Administrador) Empresa.getInstance().login("admin", "admin");
		ActionEvent mockEvent = mock(ActionEvent.class);
		Administrador logeado = (Administrador) Empresa.getInstance().getUsuarioLogeado();
		when(mockEvent.getActionCommand()).thenReturn(Constantes.CERRAR_SESION_ADMIN);
		
		assertEquals(admin_logeado.getNombreUsuario(),logeado.getNombreUsuario());
		this.controlador.actionPerformed(mockEvent);
		logeado = (Administrador) Empresa.getInstance().getUsuarioLogeado();
		assertNull(logeado);
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
	}

}
