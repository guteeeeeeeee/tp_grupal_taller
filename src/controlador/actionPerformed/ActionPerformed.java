package controlador.actionPerformed;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PasswordErroneaException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloNegocio.Empresa;
import modeloDatos.*;
import testeo_gui.ayuda.FalsoOptionPane;
import testeo_gui.ayuda.TestUtils;
import util.Constantes;
import util.Mensajes;
import vista.IVista;
import vista.Ventana;

public class ActionPerformed {

	Controlador controlador;
	IVista vista_mock;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
		this.vista_mock = mock(Ventana.class);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_registrar() {
		String user_name = "jorge123";
		String password = "12345";
		String nombre_real = "jorge alvarez";
		
		when(vista_mock.getRegUsserName()).thenReturn(user_name);
		when(vista_mock.getRegPassword()).thenReturn(password);
		when(vista_mock.getRegConfirmPassword()).thenReturn(password);
		when(vista_mock.getRegNombreReal()).thenReturn(nombre_real);
		
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn(Constantes.REG_BUTTON_REGISTRAR);
		
		assertEquals(0,Empresa.getInstance().getClientes().size());
		this.controlador.actionPerformed(mockEvent);
		assertNotNull(Empresa.getInstance().getClientes().get(user_name));
	}
	
	@Test
	public void test_login() throws UsuarioYaExisteException {
		String user_name = "pepe123";
		String password = "123";
		
		Empresa.getInstance().agregarCliente(user_name, password, "juan carlos");
		
		when(vista_mock.getUsserName()).thenReturn(user_name);
		when(vista_mock.getPassword()).thenReturn(password);
		
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn(Constantes.LOGIN);
		
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		this.controlador.actionPerformed(mockEvent);
		assertNotNull(Empresa.getInstance().getUsuarioLogeado().getNombreUsuario()); // se logea
	}
	
	@Test
	public void test_nuevo_pedido() throws UsuarioYaExisteException, UsuarioNoExisteException, PasswordErroneaException, ChoferRepetidoException, VehiculoRepetidoException {
		int cant_pax = 3;
		boolean mascota = true;
		boolean baul = true;
		int cant_km = 5;
		String tipo_zona = "ZONA_PELIGROSA";
		
		Empresa.getInstance().agregarCliente("pepe123", "123", "pepe");
		Cliente usuario_actual = (Cliente) Empresa.getInstance().login("pepe123", "123");
		Chofer chofer = new ChoferTemporario("777","fernando");
		Empresa.getInstance().agregarChofer(chofer);
		Vehiculo combi = new Combi("tty544",10,true);
		Empresa.getInstance().agregarVehiculo(combi);
		
		when(this.vista_mock.getCantidadPax()).thenReturn(cant_pax);
		when(this.vista_mock.isPedidoConMascota()).thenReturn(mascota);
		when(this.vista_mock.isPedidoConBaul()).thenReturn(baul);
		when(this.vista_mock.getCantKm()).thenReturn(cant_km);
		when(this.vista_mock.getTipoZona()).thenReturn(tipo_zona);
		
		assertEquals(0,Empresa.getInstance().getPedidos().size()); 
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn(Constantes.NUEVO_PEDIDO);
		this.controlador.actionPerformed(mockEvent);
		assertNotNull(Empresa.getInstance().getPedidoDeCliente(usuario_actual));
	}
	
	@Test
	public void test_nuevo_chofer() {
		String dni = "12345";
		String nombre = "jorge";
		String tipo = "TEMPORARIO";
		
		when(this.vista_mock.getTipoChofer()).thenReturn(tipo);
		when(this.vista_mock.getNombreChofer()).thenReturn(nombre);
		when(this.vista_mock.getDNIChofer()).thenReturn(dni);
		
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn(Constantes.NUEVO_CHOFER);
		
		assertNull(Empresa.getInstance().getChoferes().get(dni));
		this.controlador.actionPerformed(mockEvent);
		assertNotNull(Empresa.getInstance().getChoferes().get(dni));
	}
	
	@Test
	public void test_nuevo_vehiculo() {
		String patente = "mmm111";
		String tipo = "MOTO";
		
		when(this.vista_mock.getTipoVehiculo()).thenReturn(tipo);
		when(this.vista_mock.getPatente()).thenReturn(patente);
		
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn(Constantes.NUEVO_VEHICULO);
		
		assertEquals(0,Empresa.getInstance().getVehiculos().size());
		this.controlador.actionPerformed(mockEvent);
		assertNotNull(Empresa.getInstance().getVehiculos().get(patente));
	}
	
	@Test
	public void test_nuevo_viaje() throws UsuarioYaExisteException, ChoferRepetidoException, VehiculoRepetidoException, UsuarioNoExisteException, PasswordErroneaException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException {
		Empresa.getInstance().agregarCliente("alfa", "aaa", "alfonso");
		
		Chofer chofer = new ChoferTemporario("12345","chofer");
		Empresa.getInstance().agregarChofer(chofer);

		Vehiculo moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		
		Cliente user = (Cliente) Empresa.getInstance().login("alfa","aaa");
		
		Pedido pedido = new Pedido(user,1,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido);
		
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn(Constantes.NUEVO_VIAJE);
		when(this.vista_mock.getOptionPane()).thenReturn(op);
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(chofer); //getChoferSeleccionado
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(moto); //getVehiculoDisponibleSeleccionado
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(pedido); //getPedidoSeleccionado
		
		assertEquals(0,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.actionPerformed(mockEvent);
		assertNotNull(Empresa.getInstance().getViajesIniciados().get(user));
	}
	
	@Test
	public void test_no_existe() throws UsuarioYaExisteException, ChoferRepetidoException, VehiculoRepetidoException, UsuarioNoExisteException, PasswordErroneaException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException {
		Empresa.getInstance().agregarCliente("alfa", "aaa", "alfonso");
		
		Chofer chofer = new ChoferTemporario("12345","chofer");
		Empresa.getInstance().agregarChofer(chofer);

		Vehiculo moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		
		Cliente user = (Cliente) Empresa.getInstance().login("alfa","aaa");
		
		Pedido pedido = new Pedido(user,1,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido);
		
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn("funcion_no_existe");
		when(this.vista_mock.getOptionPane()).thenReturn(op);
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(chofer); //getChoferSeleccionado
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(moto); //getVehiculoDisponibleSeleccionado
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(pedido); //getPedidoSeleccionado
		
		assertNotNull(Empresa.getInstance().getUsuarioLogeado());
		assertEquals(1,Empresa.getInstance().getPedidos().size());
		assertEquals(1,Empresa.getInstance().getChoferes().size());
		assertEquals(1,Empresa.getInstance().getVehiculos().size());
		assertEquals(0,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.actionPerformed(mockEvent);
		//que no cambie nada
		assertNotNull(Empresa.getInstance().getUsuarioLogeado());
		assertEquals(1,Empresa.getInstance().getPedidos().size());
		assertEquals(1,Empresa.getInstance().getChoferes().size());
		assertEquals(1,Empresa.getInstance().getVehiculos().size());
		assertEquals(0,Empresa.getInstance().getViajesIniciados().size());
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
	}

}
