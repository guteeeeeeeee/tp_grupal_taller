package integracion.admin_login_y_agrega_chofer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
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

public class admin_login_y_agrega_chofer {

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
	public void test_login_y_agrega_chofer_temporario() {
		when(vista_mock.getUsserName()).thenReturn("admin");
		when(vista_mock.getPassword()).thenReturn("admin");

		this.controlador.login(); //me logeo como admin
		
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer);
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.auto);
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido);
		
		String dni = "12345";
		String nombre = "jorge";
		String tipo = Constantes.TEMPORARIO; //agrego temporario
		
		when(this.vista_mock.getTipoChofer()).thenReturn(tipo);
		when(this.vista_mock.getNombreChofer()).thenReturn(nombre);
		when(this.vista_mock.getDNIChofer()).thenReturn(dni);
		
		assertEquals(1,Empresa.getInstance().getChoferes().size());
		this.controlador.nuevoChofer();
		assertEquals(2,Empresa.getInstance().getChoferes().size());
		assertEquals(dni,Empresa.getInstance().getChoferes().get(dni).getDni());
		assertEquals(nombre,Empresa.getInstance().getChoferes().get(dni).getNombre());
	}
	
	@Test
	public void test_login_y_agrega_chofer_permanente() {
		when(vista_mock.getUsserName()).thenReturn("admin");
		when(vista_mock.getPassword()).thenReturn("admin");

		this.controlador.login(); //me logeo como admin
		
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer);
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.auto);
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido);
		
		String dni = "12345";
		String nombre = "jorge";
		int anio = 2020;
		int cant_hijos = 4;
		String tipo = Constantes.PERMANENTE;
		
		when(this.vista_mock.getTipoChofer()).thenReturn(tipo);
		when(this.vista_mock.getNombreChofer()).thenReturn(nombre);
		when(this.vista_mock.getDNIChofer()).thenReturn(dni);
		when(this.vista_mock.getAnioChofer()).thenReturn(anio);
		when(this.vista_mock.getHijosChofer()).thenReturn(cant_hijos);
		
		assertEquals(1,Empresa.getInstance().getChoferes().size());
		this.controlador.nuevoChofer();
		assertEquals(2,Empresa.getInstance().getChoferes().size());
		assertEquals(dni,Empresa.getInstance().getChoferes().get(dni).getDni());
		assertEquals(nombre,Empresa.getInstance().getChoferes().get(dni).getNombre());
		assertEquals(anio,((ChoferPermanente)Empresa.getInstance().getChoferes().get(dni)).getAnioIngreso());
		assertEquals(cant_hijos,((ChoferPermanente)Empresa.getInstance().getChoferes().get(dni)).getCantidadHijos());
	}
	
	@Test
	public void test_login_y_agrega_chofer_repetido() {
		when(vista_mock.getUsserName()).thenReturn("admin");
		when(vista_mock.getPassword()).thenReturn("admin");

		this.controlador.login(); //me logeo como admin
		
		String dni = this.chofer.getDni();
		String nombre = "jorge";
		int anio = 2020;
		int cant_hijos = 4;
		String tipo = Constantes.PERMANENTE;
		
		when(this.vista_mock.getTipoChofer()).thenReturn(tipo);
		when(this.vista_mock.getNombreChofer()).thenReturn(nombre);
		when(this.vista_mock.getDNIChofer()).thenReturn(dni);
		when(this.vista_mock.getAnioChofer()).thenReturn(anio);
		when(this.vista_mock.getHijosChofer()).thenReturn(cant_hijos);
		
		assertEquals(1,Empresa.getInstance().getChoferes().size());
		this.controlador.nuevoChofer();
		assertEquals("no dice que el chofer ya esta registrado",Mensajes.CHOFER_YA_REGISTRADO.getValor(),this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getChoferes().size());
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
