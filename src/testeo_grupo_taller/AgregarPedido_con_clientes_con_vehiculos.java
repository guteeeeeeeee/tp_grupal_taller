package testeo_grupo_taller;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PasswordErroneaException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;

public class AgregarPedido_con_clientes_con_vehiculos {
	Pedido pedido;
	Vehiculo auto;
	Cliente cliente_logeado;
	
	@Before
	public void setUp() throws Exception {
		String nombre_usuario = "a";
		String password = "test";
		String nombre_real = "user test";
		try {
			Empresa.getInstance().agregarCliente(nombre_usuario,password,nombre_real);
		} catch (UsuarioYaExisteException e) {
			e.printStackTrace();
		}
		this.auto = new Auto("pda134",4,true);
		Empresa.getInstance().agregarVehiculo(auto);
		try { //logea al usuario en el sistema
			 this.cliente_logeado = (Cliente) Empresa.getInstance().login(nombre_usuario,password);
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {				
			e.printStackTrace();
		}
		this.pedido = new Pedido(this.cliente_logeado,3,true,true,10,"ZONA_STANDARD");
	}

	@Test
	public void test_agregar_pedido() { //correctamente
		try {
			assertEquals(0,Empresa.getInstance().getPedidos().size());
			Empresa.getInstance().agregarPedido(this.pedido);
			assertNotNull(Empresa.getInstance().getPedidoDeCliente(this.cliente_logeado));
			assertEquals(1,Empresa.getInstance().getPedidos().size());
		} catch (SinVehiculoParaPedidoException e) {
			fail("exception sin vehiculo disponible");
		} catch (ClienteNoExisteException e) {
			fail("exception no esta registrado el usuario");
		} catch (ClienteConViajePendienteException e) {
			fail("exception el cliente tiene un viaje pendiente");
		} catch (ClienteConPedidoPendienteException e) {
			fail("exception el cliente tiene un pedido pendiente");
		}
	}
	
	@Test
	public void test_sin_vehiculo_para_atender() {
		Pedido pedido_nuevo = new Pedido(this.cliente_logeado,7,true,true,10,"ZONA_STANDARD");
		try { //agrego el pedido
			Empresa.getInstance().agregarPedido(this.pedido);
		} catch (SinVehiculoParaPedidoException e) {
			//esta ok
		} catch (ClienteNoExisteException e) {
			fail("exception no esta registrado el usuario");
		} catch (ClienteConViajePendienteException e) {
			fail("exception el cliente tiene un viaje pendiente");
		} catch (ClienteConPedidoPendienteException e) {
			fail("exception el cliente tiene un pedido pendiente");
		}
	}
	
	@Test
	public void test_cliente_con_viaje_iniciado() {
		crea_viaje_anterior(); //creo un viaje antes
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException e) {
			fail("exception sin vehiculo disponible");
		} catch (ClienteNoExisteException e) {
			fail("exception no esta registrado el usuario");
		} catch (ClienteConViajePendienteException e) {
			//esta ok
		} catch (ClienteConPedidoPendienteException e) {
			fail("exception el cliente tiene un pedido pendiente");
		}
	}
	
	public void crea_viaje_anterior() {
		HashMap<Cliente,Viaje> viajes_iniciados = new HashMap<Cliente,Viaje>();
		Pedido pedido_test = new Pedido(this.cliente_logeado,2,false,true,5,"ZONA_STANDARD");
		Chofer chofer = new ChoferPermanente("213213","marcelo hanson",2020,4);
		Vehiculo auto = new Auto("pda123",3,true);
		Viaje viaje = new Viaje(pedido_test,chofer,auto);
		viajes_iniciados.put(this.cliente_logeado, viaje);
		Empresa.getInstance().setViajesIniciados(viajes_iniciados);
	}
	
	@Test
	public void test_cliente_con_pedido_pendiente() {
		crea_pedido_anterior();
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException e) {
			fail("exception sin vehiculo disponible");
		} catch (ClienteNoExisteException e) {
			fail("exception no esta registrado el usuario");
		} catch (ClienteConViajePendienteException e) {
			fail("exception que esta en viaje");
		} catch (ClienteConPedidoPendienteException e) {
			//esta ok
		}
	}
	
	public void crea_pedido_anterior() {
		HashMap<Cliente,Pedido> pedidos = new HashMap<Cliente,Pedido>();
		Pedido pedido_test = new Pedido(this.cliente_logeado,2,false,true,5,"ZONA_STANDARD");
		pedidos.put(this.cliente_logeado, pedido_test);
		Empresa.getInstance().setPedidos(pedidos);
	}
	
	@Test
	public void test_cliente_no_registrado() {
		Cliente cliente_nuevo = new Cliente("b","123","alter");
		Pedido pedido_nuevo = new Pedido(cliente_nuevo,3,true,true,10,"ZONA_STANDARD");
		try { //agrego el pedido
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException e) {
			fail("exception sin vehiculo disponible");
		} catch (ClienteNoExisteException e) {
			//esta ok
		} catch (ClienteConViajePendienteException e) {
			fail("exception el cliente tiene un viaje pendiente");
		} catch (ClienteConPedidoPendienteException e) {
			fail("exception el cliente tiene un pedido pendiente");
		}
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().logout();
	}

}
