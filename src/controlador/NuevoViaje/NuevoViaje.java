package controlador.NuevoViaje;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.*;
import modeloNegocio.Empresa;
import testeo_gui.FalsoOptionPane;
import util.Constantes;
import util.Mensajes;
import vista.*;

public class NuevoViaje {

	IVista vista_mock;
	Chofer chofer1,chofer2,chofer3;
	Vehiculo moto,auto,combi;
	Controlador controlador;
	Pedido pedido1,pedido2;
	Cliente user1,user2,user3;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.vista_mock = mock(Ventana.class);
		Empresa.getInstance().agregarCliente("alfa", "aaa", "alfonso");
		Empresa.getInstance().agregarCliente("teo", "aaa", "teo");
		Empresa.getInstance().agregarCliente("ibu", "aaa", "ibuprofeno");
		
		this.chofer1 = new ChoferTemporario("12345","chofer");
		Empresa.getInstance().agregarChofer(this.chofer1);
		this.chofer2 = new ChoferTemporario("777","chofer 2");
		Empresa.getInstance().agregarChofer(this.chofer2);
		this.chofer3 = new ChoferTemporario("999","chofer 3");
		Empresa.getInstance().agregarChofer(this.chofer3);
		this.moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(this.moto);
		this.auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarVehiculo(auto);
		this.combi = new Combi("ccc111",10,true);
		Empresa.getInstance().agregarVehiculo(combi);
		
		this.user1 = (Cliente) Empresa.getInstance().login("ibu","aaa");
		this.pedido1 = new Pedido(this.user1,1,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(this.pedido1);
		this.user2 = (Cliente) Empresa.getInstance().login("teo","aaa");
		this.pedido2 = new Pedido(this.user2,3,true,false,10,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(this.pedido2);
		this.user3 = (Cliente) Empresa.getInstance().login("alfa","aaa");
		
		Empresa.getInstance().crearViaje(pedido1, chofer1, combi);
		
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(this.vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_nuevo_viaje_correcto() {
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer2); //getChoferSeleccionado
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.auto); //getVehiculoDisponibleSeleccionado
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido2); //getPedidoSeleccionado
		
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje();
		assertEquals(2,Empresa.getInstance().getViajesIniciados().size());
		assertEquals(this.chofer2,Empresa.getInstance().getViajesIniciados().get(this.user2).getChofer());
		assertEquals(this.pedido2,Empresa.getInstance().getViajesIniciados().get(this.user2).getPedido());
		assertEquals(this.auto,Empresa.getInstance().getViajesIniciados().get(this.user2).getVehiculo());
	}
	
	@Test
	public void test_nuevo_viaje_vehiculo_no_puede_satisfacer() {
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer2); //getChoferSeleccionado
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.moto); //getVehiculoDisponibleSeleccionado
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido2); //getPedidoSeleccionado
		
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje();
		assertEquals("no dice que El vehiculo no puede atender el pedido",Mensajes.VEHICULO_NO_VALIDO.getValor(),this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
	}
	
	@Test
	public void test_nuevo_viaje_pedido_inexistente() {
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer2); //getChoferSeleccionado
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.moto); //getVehiculoDisponibleSeleccionado
		Pedido pedido_inexistente = new Pedido(this.user2,1,false,false,10,Constantes.ZONA_STANDARD);
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(pedido_inexistente); //getPedidoSeleccionado
		
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje();
		assertEquals("no dice que El Pedido no figura en la lista",Mensajes.PEDIDO_INEXISTENTE.getValor(),this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
	}
	
	@Test
	public void test_nuevo_viaje_chofer_no_disponible() {
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer1); //getChoferSeleccionado
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.auto); //getVehiculoDisponibleSeleccionado
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido2); //getPedidoSeleccionado
		
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje();
		assertEquals("no dice que El chofer no esta disponible",Mensajes.CHOFER_NO_DISPONIBLE.getValor(),this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
	}
	
	@Test
	public void test_nuevo_viaje_vehiculo_no_disponible() {
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer2); //getChoferSeleccionado
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.combi); //getVehiculoDisponibleSeleccionado
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido2); //getPedidoSeleccionado
		
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje();
		assertEquals("no dice que El vehiculo no esta disponible",Mensajes.VEHICULO_NO_DISPONIBLE.getValor(),this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
	}
	
	@Test
	public void test_nuevo_viaje_cliente_en_viaje() {
		HashMap<Cliente,Pedido> pedidos_aux = new HashMap<Cliente,Pedido>();
		Pedido pedido_aux = new Pedido(user2,1,false,false,5,Constantes.ZONA_PELIGROSA);
		pedidos_aux.put(user1, pedido1);
		pedidos_aux.put(user2, pedido_aux);
		
		Empresa.getInstance().setPedidos(pedidos_aux);
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer2); //getChoferSeleccionado
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.auto); //getVehiculoDisponibleSeleccionado
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(pedido_aux); //getPedidoSeleccionado
		
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje();
		assertEquals("no dice que el Cliente tiene viaje pendiente",Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor(),this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
	}

}
