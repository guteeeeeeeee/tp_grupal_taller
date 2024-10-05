package testeo_grupo_taller;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PasswordErroneaException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloNegocio.Empresa;
import net.bytebuddy.asm.Advice.This;
import modeloDatos.Vehiculo;
import modeloDatos.Auto;
import modeloDatos.ChoferPermanente;
import modeloDatos.Viaje;
import modeloDatos.Chofer;
import static org.mockito.Mockito.*;

public class AgregarPedido {

	Pedido pedido;
	Vehiculo auto;
	Cliente cliente_logeado;
	String nombre_usuario = "jorge123";
	String password = "123";
	String nombre_real = "jorge fernandez";
	
	@BeforeClass
	public static void registro_cliente() {
		try {
			Empresa.getInstance().agregarCliente("jorge123","123","jorge fernandez");
		} catch (UsuarioYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Before
	public void setUp() throws Exception {
		this.auto = new Auto("pda134",4,true);
		
		try { //logea al usuario en el sistema
			Usuario user_logeado = Empresa.getInstance().login(this.nombre_usuario,this.password);
			this.cliente_logeado = (Cliente) user_logeado;
			this.pedido = new Pedido(this.cliente_logeado,3,true,true,10,"ZONA_STANDARD");
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {				
			e.printStackTrace();
		}
	}

	@Test
	public void test_agregar_pedido() {
		//HashMap<String,Cliente> clientes = mock(HashMap.class);
		//when(clientes.get(this.nombre_usuario)).thenReturn(this.cliente);
		//Empresa empresa_mockeada = mock(Empresa.class);
		//when(empresa_mockeada.getUsuarioLogeado()).thenReturn(this.cliente);
		//Empresa.getInstance().setUsuarioLogeado(this.cliente);
		//assertEquals(this.cliente,Empresa.getInstance().getUsuarioLogeado());
		
		try { //agrego el vehiculo disponible
			Empresa.getInstance().agregarVehiculo(auto);
		} catch (VehiculoRepetidoException e) {
			e.printStackTrace();
		}
		try { //agrego el pedido
			Empresa.getInstance().agregarPedido(pedido);
			assertNotNull(Empresa.getInstance().getPedidoDeCliente(this.cliente_logeado));
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
		//no agrego el vehiculo
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
		try { //agrego el vehiculo disponible
			Empresa.getInstance().agregarVehiculo(auto);
		} catch (VehiculoRepetidoException e) {
			e.printStackTrace();
		}
		
		crea_viaje_anterior();
		
		try { //agrego el pedido
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
		try { //agrego el vehiculo disponible
			Empresa.getInstance().agregarVehiculo(auto);
		} catch (VehiculoRepetidoException e) {
			e.printStackTrace();
		}
		
		crea_pedido_anterior();
		
		try { //agrego el pedido
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
		Empresa.getInstance().logout();
		Cliente cliente_nuevo = new Cliente("hasdhasd","asdasd","user test");
		try { //agrego el vehiculo disponible
			Empresa.getInstance().agregarVehiculo(auto);
		} catch (VehiculoRepetidoException e) {
			e.printStackTrace();
		}
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
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().logout();
	}

}
