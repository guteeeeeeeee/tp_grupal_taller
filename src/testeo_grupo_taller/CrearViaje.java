package testeo_grupo_taller;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ClienteConViajePendienteException;
import excepciones.PedidoInexistenteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;

public class CrearViaje {
	
	Pedido pedido;
	Vehiculo vehiculo;
	Chofer chofer;
	Cliente cliente_logeado;
	String nombre_usuario;
	String password;
	String nombre_real;
	
	@BeforeClass
	public static void setea() {
		try {
			Empresa.getInstance().agregarCliente("jorge123","123","jorge fernandez");
		} catch (UsuarioYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Before
	public void setUp() throws Exception {
		this.nombre_usuario = "jorge123";
		this.password = "123";
		this.nombre_real = "jorge fernandez";
		
		this.vehiculo = new Auto("dasdad",3,true);
		this.chofer = new ChoferTemporario("213213","fernando");
		Cliente cliente = new Cliente(this.nombre_usuario,this.password,this.nombre_real);
		
		Usuario user_logeado = Empresa.getInstance().login(this.nombre_usuario,this.password);
		this.cliente_logeado = (Cliente) user_logeado;
		this.pedido = new Pedido(this.cliente_logeado,3,true,true,10,"ZONA_STANDARD");
		Empresa.getInstance().agregarChofer(this.chofer);
		Empresa.getInstance().agregarVehiculo(this.vehiculo);
		Empresa.getInstance().agregarPedido(this.pedido);
	}

	@Test
	public void test_creo_viaje() {
		try {
			Empresa.getInstance().crearViaje(this.pedido, this.chofer, this.vehiculo);
			assertNotNull(Empresa.getInstance().getViajeDeCliente(cliente_logeado));
		} catch (PedidoInexistenteException e) {
			fail("el pedido no esta en el hashmap de pedidos");
		} catch (ChoferNoDisponibleException e) {
			fail("el chofer no esta disponible");
		} catch (VehiculoNoDisponibleException e) {
			fail("el vehiculo no esta disponible");
		} catch (VehiculoNoValidoException e) {
			fail("el vehiculo no esta en el hashmap de vehiculos");
		} catch (ClienteConViajePendienteException e) {
			fail("el cliente esta realizando un viaje");
		}
	}
	
	@Test
	public void test_creo_viaje_con_pedido_inexistente() {
		Empresa.getInstance().getPedidos().clear();
		try {
			Empresa.getInstance().crearViaje(this.pedido, this.chofer, this.vehiculo);
			fail("crea el viaje con pedido inexistente");
		} catch (PedidoInexistenteException e) {
			//esta ok
		} catch (ChoferNoDisponibleException e) {
			fail("el chofer no esta disponible");
		} catch (VehiculoNoDisponibleException e) {
			fail("el vehiculo no esta disponible");
		} catch (VehiculoNoValidoException e) {
			fail("el vehiculo no esta en el hashmap de vehiculos");
		} catch (ClienteConViajePendienteException e) {
			fail("el cliente esta realizando un viaje");
		}
	}
	
	@Test
	public void test_creo_viaje_con_chofer_inexistente() {
		Empresa.getInstance().getChoferes().clear();
		try {
			Empresa.getInstance().crearViaje(this.pedido, this.chofer, this.vehiculo);
			fail("crea el viaje con chofer inexistente");
		} catch (PedidoInexistenteException e) {
			fail("el pedido es inexistente");
		} catch (ChoferNoDisponibleException e) {
			//esta ok
		} catch (VehiculoNoDisponibleException e) {
			fail("el vehiculo no esta disponible");
		} catch (VehiculoNoValidoException e) {
			fail("el vehiculo no esta en el hashmap de vehiculos");
		} catch (ClienteConViajePendienteException e) {
			fail("el cliente esta realizando un viaje");
		}
	}
	
	@Test
	public void test_creo_viaje_con_vehiculo_inexistente() {
		Empresa.getInstance().getVehiculos().clear();
		try {
			Empresa.getInstance().crearViaje(this.pedido, this.chofer, this.vehiculo);
			fail("crea el viaje con vehiculo inexistente");
		} catch (PedidoInexistenteException e) {
			fail("el pedido es inexistente");
		} catch (ChoferNoDisponibleException e) {
			fail("el chofer no esta disponible");
		} catch (VehiculoNoDisponibleException e) {
			//esta ok
		} catch (VehiculoNoValidoException e) {
			fail("el vehiculo no esta en el hashmap de vehiculos");
		} catch (ClienteConViajePendienteException e) {
			fail("el cliente esta realizando un viaje");
		}
	}
	
	@Test
	public void test_creo_viaje_con_vehiculo_no_satisface() {
		Empresa.getInstance().getVehiculos().clear();
		Vehiculo vehiculo_no_cumple = new Moto("ada567");
		try {
			Empresa.getInstance().agregarVehiculo(vehiculo_no_cumple);
		} catch (VehiculoRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Empresa.getInstance().crearViaje(this.pedido, this.chofer, vehiculo_no_cumple);
			fail("crea el viaje con vehiculo que no cumple con los requisitos");
		} catch (PedidoInexistenteException e) {
			fail("el pedido es inexistente");
		} catch (ChoferNoDisponibleException e) {
			fail("el chofer no esta disponible");
		} catch (VehiculoNoDisponibleException e) {
			fail("el vehiculo no esta disponible actualmente");
		} catch (VehiculoNoValidoException e) {
			//esta ok
		} catch (ClienteConViajePendienteException e) {
			fail("el cliente esta realizando un viaje");
		}
	}
	
	@Test
	public void test_cliente_realizando_viaje() {
		crea_viaje_anterior();
		try {
			Empresa.getInstance().crearViaje(this.pedido, this.chofer, this.vehiculo);
			fail("crea el viaje con vehiculo que no cumple con los requisitos");
		} catch (PedidoInexistenteException e) {
			fail("el pedido es inexistente");
		} catch (ChoferNoDisponibleException e) {
			fail("el chofer no esta disponible");
		} catch (VehiculoNoDisponibleException e) {
			fail("el vehiculo no esta disponible actualmente");
		} catch (VehiculoNoValidoException e) {
			fail("vehiculo no cumple con los requisitos");
		} catch (ClienteConViajePendienteException e) {
			//esta ok
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
	
	@After
	public void limpio() {
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getPedidos().clear();
	}

}
