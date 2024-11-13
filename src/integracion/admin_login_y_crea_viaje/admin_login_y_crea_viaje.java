package integracion.admin_login_y_crea_viaje;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import util.Constantes;
import util.Mensajes;
import vista.IVista;
import vista.Ventana;

public class admin_login_y_crea_viaje {

	Controlador controlador;
	IVista vista_mock;
	FalsoOptionPane op;
	String user_name = "jorge123";
	String password = "12345";
	String nombre_real = "jorge alvarez";	
	Chofer chofer;
	Vehiculo moto,auto;
	Pedido pedido;
	Cliente cliente;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente(user_name, password, nombre_real);
		this.cliente = (Cliente) Empresa.getInstance().login(user_name, password);
		Empresa.getInstance().logout();
		
		this.auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarVehiculo(auto);
		this.moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		
		this.chofer = new ChoferTemporario("777","chofer temporario");
		Empresa.getInstance().agregarChofer(chofer);
		
		this.pedido = new Pedido(cliente,1,false,false,10,Constantes.ZONA_PELIGROSA);
		Empresa.getInstance().agregarPedido(pedido);
		
		this.controlador = new Controlador();
		this.vista_mock = mock(Ventana.class);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_login_y_crea_viaje() {
		when(vista_mock.getUsserName()).thenReturn("admin");
		when(vista_mock.getPassword()).thenReturn("admin");

		this.controlador.login(); //me logeo como admin
		
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer);
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.auto);
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido);
		
		assertEquals(0,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje(); //creo el viaje con el pedido ya ingresado por un cliente
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		assertEquals(this.chofer,Empresa.getInstance().getViajesIniciados().get(this.cliente).getChofer());
		assertEquals(this.pedido,Empresa.getInstance().getViajesIniciados().get(this.cliente).getPedido());
		assertEquals(this.auto,Empresa.getInstance().getViajesIniciados().get(this.cliente).getVehiculo());
	}
	
	@Test
	public void test_login_y_crea_viaje_pedido_inexistente() {
		Pedido pedido_inexistente = new Pedido(this.cliente,1,false,false,10,Constantes.ZONA_PELIGROSA);
		when(vista_mock.getUsserName()).thenReturn("admin");
		when(vista_mock.getPassword()).thenReturn("admin");

		this.controlador.login(); //me logeo como admin
		
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer);
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.auto);
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(pedido_inexistente);
		
		assertEquals(0,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje(); //creo el viaje con pedido inexistente
		assertEquals(0,Empresa.getInstance().getViajesIniciados().size());
		assertEquals("no dice que El Pedido no figura en la lista",Mensajes.PEDIDO_INEXISTENTE.getValor(),this.op.getMensaje());
	}
	
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
