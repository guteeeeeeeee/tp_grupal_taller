package integracion.login_crea_pedido_y_termina_viaje;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.SinViajesException;
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
import vista.IVista;
import vista.Ventana;

public class login_crea_pedido_y_termina_viaje {

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
		
		this.controlador = new Controlador();
		this.vista_mock = mock(Ventana.class);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_login_creo_pedido_y_termino_viaje() throws SinViajesException {
		when(vista_mock.getUsserName()).thenReturn(user_name);
		when(vista_mock.getPassword()).thenReturn(password);

		this.controlador.login();
		Cliente user_actual = (Cliente) Empresa.getInstance().getUsuarioLogeado(); //me logeo como cliente
		
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
		
		assertEquals(0,Empresa.getInstance().getPedidos().size());
		this.controlador.nuevoPedido(); //creo el pedido
		assertEquals(1,Empresa.getInstance().getPedidos().size());
		Pedido pedido_agregado = Empresa.getInstance().getPedidoDeCliente(user_actual);
		
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer);
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.auto);
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(pedido_agregado);
		
		assertEquals(0,Empresa.getInstance().getViajesIniciados().size());
		this.controlador.nuevoViaje(); //creo el viaje con el pedido ya ingresado por un cliente
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		
		int calificacion = 4;
		when(vista_mock.getCalificacion()).thenReturn(calificacion);
		
		this.controlador.calificarPagar();
		assertEquals(0,Empresa.getInstance().getViajesIniciados().size());
		assertEquals(1,Empresa.getInstance().getViajesTerminados().size());
		assertEquals(calificacion,Empresa.getInstance().calificacionDeChofer(this.chofer),0.1);
	}
	/*usando el panel de admin no se puede dar el caso de que pedido no exista, el chofer o vehiculo no este disponible, el vehiculo no sea valido o
	 * el cliente este realizando ya un viaje*/
	
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
