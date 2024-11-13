package integracion.admin_login_y_agrega_vehiculo;

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

public class admin_login_y_agrega_vehiculo {

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
	public void test_login_y_agrega_vehiculo() {
		when(vista_mock.getUsserName()).thenReturn("admin");
		when(vista_mock.getPassword()).thenReturn("admin");

		this.controlador.login(); //me logeo como admin
		
		String patente = "nna122";
		String tipo = Constantes.AUTO;
		when(this.vista_mock.getTipoVehiculo()).thenReturn(tipo);
		when(this.vista_mock.getPatente()).thenReturn(patente);
		when(this.vista_mock.getPlazas()).thenReturn(4);
		when(this.vista_mock.isPedidoConMascota()).thenReturn(false);
		
		assertEquals(2,Empresa.getInstance().getVehiculos().size());
		this.controlador.nuevoVehiculo();
		assertEquals(3,Empresa.getInstance().getVehiculos().size());
		assertEquals(patente,Empresa.getInstance().getVehiculos().get(patente).getPatente());
		assertEquals(4,Empresa.getInstance().getVehiculos().get(patente).getCantidadPlazas());
		assertEquals(false,Empresa.getInstance().getVehiculos().get(patente).isMascota());
	}
	
	@Test
	public void test_login_y_agrega_vehiculo_patente_repetida() {
		when(vista_mock.getUsserName()).thenReturn("admin");
		when(vista_mock.getPassword()).thenReturn("admin");

		this.controlador.login(); //me logeo como admin
		
		String patente = this.moto.getPatente();
		String tipo = Constantes.COMBI;
		
		when(this.vista_mock.getTipoVehiculo()).thenReturn(tipo);
		when(this.vista_mock.getPatente()).thenReturn(patente);
		when(this.vista_mock.getPlazas()).thenReturn(10);
		when(this.vista_mock.isPedidoConMascota()).thenReturn(false);
		
		assertEquals(2,Empresa.getInstance().getVehiculos().size());
		this.controlador.nuevoVehiculo();
		assertEquals("no dice que el Vehiculo ya esta Registrado",Mensajes.VEHICULO_YA_REGISTRADO.getValor(),this.op.getMensaje());
		assertEquals(2,Empresa.getInstance().getVehiculos().size());
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
