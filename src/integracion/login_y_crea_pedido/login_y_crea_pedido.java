package integracion.login_y_crea_pedido;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Auto;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Chofer;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import util.Constantes;
import util.Mensajes;
import vista.IVista;
import vista.Ventana;

public class login_y_crea_pedido {

	Controlador controlador;
	IVista vista_mock;
	FalsoOptionPane op;
	Pedido pedido;
	Chofer chofer;
	Vehiculo moto;
	
	@Before
	public void setUp() throws Exception {
		Vehiculo auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarVehiculo(auto);
		this.moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		
		this.chofer = new ChoferTemporario("777","chofer temporario");
		Empresa.getInstance().agregarChofer(chofer);
		
		Empresa.getInstance().agregarCliente("pepe123", "123", "pepe");
		Empresa.getInstance().agregarCliente("alfa", "123", "alfa");
		Empresa.getInstance().agregarCliente("viajante3", "123", "viajante");
		
		Cliente viajante = (Cliente) Empresa.getInstance().login("alfa", "123");
		Empresa.getInstance().logout();
		
		this.pedido = new Pedido(viajante,1,false,false,5,Constantes.ZONA_PELIGROSA);
		Empresa.getInstance().agregarPedido(pedido);
		
		this.vista_mock = mock(Ventana.class);
		
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(this.vista_mock.getOptionPane()).thenReturn(op);
	}
	
	@Test
	public void test_login_y_agrego_pedido() {
		when(vista_mock.getUsserName()).thenReturn("pepe123");
		when(vista_mock.getPassword()).thenReturn("123");

		this.controlador.login();
		Cliente usuario_actual = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		
		int cant_pax = 2;
		boolean mascota = false;
		boolean baul = false;
		int cant_km = 10;
		String tipo_zona = Constantes.ZONA_STANDARD;
		
		when(this.vista_mock.getCantidadPax()).thenReturn(cant_pax);
		when(this.vista_mock.isPedidoConMascota()).thenReturn(mascota);
		when(this.vista_mock.isPedidoConBaul()).thenReturn(baul);
		when(this.vista_mock.getCantKm()).thenReturn(cant_km);
		when(this.vista_mock.getTipoZona()).thenReturn(tipo_zona);
		
		assertEquals(1,Empresa.getInstance().getPedidos().size());
		this.controlador.nuevoPedido();
		assertEquals(2,Empresa.getInstance().getPedidos().size());
		assertEquals(usuario_actual.getNombreUsuario(),Empresa.getInstance().getPedidos().get(usuario_actual).getCliente().getNombreUsuario());
		assertEquals(usuario_actual.getPass(),Empresa.getInstance().getPedidos().get(usuario_actual).getCliente().getPass());
	}
	
	@Test
	public void agregar_pedido_ningun_vehiculo_satisface() throws UsuarioNoExisteException, PasswordErroneaException {
		when(vista_mock.getUsserName()).thenReturn("pepe123");
		when(vista_mock.getPassword()).thenReturn("123");

		this.controlador.login();
		Cliente user_actual = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		
		int cant_pax = 4;
		boolean mascota = true;
		boolean baul = true;
		int cant_km = 5;
		String tipo_zona = Constantes.ZONA_PELIGROSA;
		
		when(this.vista_mock.getCantidadPax()).thenReturn(cant_pax);
		when(this.vista_mock.isPedidoConMascota()).thenReturn(mascota);
		when(this.vista_mock.isPedidoConBaul()).thenReturn(baul);
		when(this.vista_mock.getCantKm()).thenReturn(cant_km);
		when(this.vista_mock.getTipoZona()).thenReturn(tipo_zona);
		
		assertEquals(1,Empresa.getInstance().getPedidos().size());
		this.controlador.nuevoPedido();
		assertEquals("no dice que ningun vehiculo puede satisfacer el pedido",Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(),this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getPedidos().size());
	}
	/*
	@Test
	public void agregar_pedido_pedido_pendiente() throws UsuarioNoExisteException, PasswordErroneaException {
		when(vista_mock.getUsserName()).thenReturn("alfa");
		when(vista_mock.getPassword()).thenReturn("123");

		this.controlador.login();
		Cliente user_actual = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		
		int cant_pax = 4;
		boolean mascota = true;
		boolean baul = true;
		int cant_km = 5;
		String tipo_zona = Constantes.ZONA_PELIGROSA;
		
		when(this.vista_mock.getCantidadPax()).thenReturn(cant_pax);
		when(this.vista_mock.isPedidoConMascota()).thenReturn(mascota);
		when(this.vista_mock.isPedidoConBaul()).thenReturn(baul);
		when(this.vista_mock.getCantKm()).thenReturn(cant_km);
		when(this.vista_mock.getTipoZona()).thenReturn(tipo_zona);
		
		assertEquals(1,Empresa.getInstance().getPedidos().size());
		this.controlador.nuevoPedido();
		assertEquals("no dice que el cliente tiene pedido pendiente",Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor(),this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getPedidos().size());
	}*/

	/*usando el panel de cliente no se puede dar el caso de que el cliente tenga un pedido o viaje iniciado, o el cliente no este registrado*/

	@After
	public void limpio() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
		Empresa.getInstance().getChoferesDesocupados().clear();
	}

}
