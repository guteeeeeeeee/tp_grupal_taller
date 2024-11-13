package integracion.admin_login_y_crea_viaje;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import excepciones.ChoferRepetidoException;
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

public class admin_login_y_crea_viaje_con_viaje {

	Controlador controlador;
	IVista vista_mock;
	FalsoOptionPane op;
	String user_name = "jorge123";
	String password = "12345";
	String nombre_real = "jorge alvarez";	
	Chofer chofer;
	Vehiculo moto,auto;
	Pedido pedido1,pedido2;
	Cliente cliente;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente(user_name, password, nombre_real);
		this.cliente = (Cliente) Empresa.getInstance().login(user_name, password);
		Empresa.getInstance().logout();
		
		Empresa.getInstance().agregarCliente("en_viaje", "111", "viajero");
		Cliente viajero = (Cliente) Empresa.getInstance().login("en_viaje", "111");
		Empresa.getInstance().logout();
		
		this.auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarVehiculo(auto);
		this.moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		
		this.chofer = new ChoferTemporario("777","chofer temporario");
		Empresa.getInstance().agregarChofer(chofer);
		
		this.pedido1 = new Pedido(cliente,1,false,false,10,Constantes.ZONA_PELIGROSA);
		Empresa.getInstance().agregarPedido(pedido1);
		this.pedido2 = new Pedido(viajero,1,false,false,10,Constantes.ZONA_PELIGROSA);
		Empresa.getInstance().agregarPedido(pedido2);
		
		Empresa.getInstance().crearViaje(pedido1, chofer, auto);
		
		this.controlador = new Controlador();
		this.vista_mock = mock(Ventana.class);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_login_y_crea_viaje_chofer_no_disponible() {
		when(vista_mock.getUsserName()).thenReturn("admin");
		when(vista_mock.getPassword()).thenReturn("admin");

		this.controlador.login(); //me logeo como admin
		
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer);
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.moto);
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido2);
		
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje(); //creo el viaje con chofer no disponible
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		assertEquals("no dice que el chofer no esta disponible",Mensajes.CHOFER_NO_DISPONIBLE.getValor(),this.op.getMensaje());
	}
	
	@Test
	public void test_login_y_crea_viaje_vehiculo_no_disponible() throws ChoferRepetidoException {
		Chofer chofer_nuevo = new ChoferTemporario("1111","otro chofer");
		Empresa.getInstance().agregarChofer(chofer_nuevo);
		
		when(vista_mock.getUsserName()).thenReturn("admin");
		when(vista_mock.getPassword()).thenReturn("admin");

		this.controlador.login(); //me logeo como admin
		
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(chofer_nuevo);
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.auto);
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido2);
		
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje(); //creo el viaje con vehiculo no disponible
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		assertEquals("no dice que el vehiculo no esta disponible",Mensajes.VEHICULO_NO_DISPONIBLE.getValor(),this.op.getMensaje());
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
