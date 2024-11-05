package controlador.NuevoViaje;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.*;
import modeloNegocio.Empresa;
import testeo_gui.FalsoOptionPane;
import vista.IVista;
import vista.Ventana;

public class NuevoViaje_cliente_en_viaje {

	IVista vista_mock;
	Cliente user_logeado;
	Pedido pedido;
	Chofer chofer;
	Vehiculo moto;
	Controlador controlador;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.vista_mock = mock(Ventana.class);
		Empresa.getInstance().agregarCliente("alfa", "aaa", "alfonso");
		this.user_logeado = (Cliente) Empresa.getInstance().login("alfa","aaa");
		this.chofer = new ChoferTemporario("12345","chofer");
		Chofer chofer_aux = new ChoferTemporario("789","pepe");
		Empresa.getInstance().agregarChofer(chofer_aux);
		Empresa.getInstance().agregarChofer(chofer);
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer); //getChoferSeleccionado
		this.moto = new Moto("mmm111");
		Auto auto_aux = new Auto("aaa111",4,true);
		Empresa.getInstance().agregarVehiculo(auto_aux);
		Empresa.getInstance().agregarVehiculo(this.moto);
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.moto); //getVehiculoDisponibleSeleccionado
		this.pedido = new Pedido(this.user_logeado,1,false,false,5,"ZONA_STANDARD");
		Empresa.getInstance().agregarPedido(pedido);
		Pedido pedido_aux = new Pedido(this.user_logeado,2,false,false,10,"ZONA_PELIGROSA");
		
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido); //getPedidoSeleccionado
		
		HashMap<Cliente,Viaje> viajes = new HashMap<Cliente,Viaje>();
		Viaje viaje = new Viaje(pedido_aux,chofer_aux,auto_aux);
		viajes.put(user_logeado, viaje);
		Empresa.getInstance().setViajesIniciados(viajes);
		//Empresa.getInstance().crearViaje(pedido_aux, chofer_aux, auto_aux);
		
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(this.vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_nuevo_viaje_cliente_en_viaje() {
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje();
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		assertEquals("Cliente con viaje pendiente",this.op.getMensaje());
	}

}
