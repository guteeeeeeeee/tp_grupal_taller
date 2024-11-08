package modelo_Negocios.Empresa.CrearViaje;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import util.Constantes;
import modeloDatos.*;

public class CrearViaje_con_todo {
	
	Cliente user1;
	Cliente user2;
	Vehiculo vehiculo1;
	Vehiculo vehiculo2;
	Vehiculo vehiculo3;
	Chofer chofer1;
	Chofer chofer2;
	
	@Before
	public void setUp() throws Exception {		
		Empresa.getInstance().agregarCliente("a","aaa","user1");
		this.user1 = (Cliente) Empresa.getInstance().login("a","aaa");
		Empresa.getInstance().agregarCliente("b","bbb","user2");
		this.user2 = (Cliente) Empresa.getInstance().login("b","bbb");
		
		this.vehiculo1 = new Auto("aaa111",4,true);
		Empresa.getInstance().agregarVehiculo(this.vehiculo1);
		this.vehiculo2 = new Auto("bbb111",4,true);
		Empresa.getInstance().agregarVehiculo(this.vehiculo2);
		this.vehiculo3 = new Moto("ccc111");
		Empresa.getInstance().agregarVehiculo(this.vehiculo3);
		
		this.chofer1 = new ChoferTemporario("13608188","jorge");
		Empresa.getInstance().agregarChofer(this.chofer1);
		this.chofer2 = new ChoferTemporario("111","raul");
		Empresa.getInstance().agregarChofer(this.chofer2);
				
		crea_viaje_anterior(this.user2);
	}

	@Test
	public void test_creo_viaje() {
		Pedido pedido = new Pedido(this.user1,4,true,true,5,Constantes.ZONA_STANDARD);
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(2,Empresa.getInstance().getVehiculosDesocupados().size());
			assertEquals(1,Empresa.getInstance().getChoferesDesocupados().size());
			Empresa.getInstance().crearViaje(pedido, this.chofer1, this.vehiculo1);
			assertNotNull(Empresa.getInstance().getViajeDeCliente(this.user1));
			assertEquals(1,Empresa.getInstance().getVehiculosDesocupados().size());
			assertEquals(0,Empresa.getInstance().getChoferesDesocupados().size());
			assertEquals(this.chofer1,Empresa.getInstance().getViajesIniciados().get(this.user1).getChofer());
			assertEquals(this.vehiculo1,Empresa.getInstance().getViajesIniciados().get(this.user1).getVehiculo());
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
	public void test_creo_viaje_pedido_inexistente() {
		Pedido pedido = new Pedido(this.user1,4,true,true,5,Constantes.ZONA_STANDARD);
		Pedido pedido2 = new Pedido(this.user1,5,false,false,10,Constantes.ZONA_STANDARD);
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Empresa.getInstance().crearViaje(pedido2, this.chofer1, this.vehiculo1);
			fail("no tendria que haber creado el viaje ya que el pedido no existe");
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
	public void test_creo_viaje_cliente_viaje_pendiente() {
		Pedido pedido = new Pedido(this.user2,4,true,true,5,Constantes.ZONA_STANDARD);
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Viaje viaje_iniciado_user2 = Empresa.getInstance().getViajesIniciados().get(this.user2);
			assertEquals(this.chofer2,viaje_iniciado_user2.getChofer());
			assertEquals(this.vehiculo2,viaje_iniciado_user2.getVehiculo());
			Empresa.getInstance().crearViaje(pedido, this.chofer1, this.vehiculo1);
			fail("no tendria que haber creado el viaje, el usuario esta en viaje");
		} catch (PedidoInexistenteException e) {
			fail("el pedido no existe");
		} catch (ChoferNoDisponibleException e) {
			fail("el chofer no esta disponible");
		} catch (VehiculoNoDisponibleException e) {
			fail("el vehiculo no esta disponible");
		} catch (VehiculoNoValidoException e) {
			fail("el vehiculo no esta en el hashmap de vehiculos");
		} catch (ClienteConViajePendienteException e) {
			//esta ok
		}
	}
	
	@Test
	public void test_creo_viaje_chofer_no_disponible() {
		Pedido pedido = new Pedido(this.user1,4,true,true,5,Constantes.ZONA_STANDARD);
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Viaje viaje_iniciado_user2 = Empresa.getInstance().getViajesIniciados().get(this.user2);
			assertEquals(this.chofer2,viaje_iniciado_user2.getChofer());
			Empresa.getInstance().crearViaje(pedido, this.chofer2, this.vehiculo1);
			fail("no tendria que haber creado el viaje, el chofer esta en viaje");
		} catch (PedidoInexistenteException e) {
			fail("el pedido no existe");
		} catch (ChoferNoDisponibleException e) {
			//esta ok
		} catch (VehiculoNoDisponibleException e) {
			fail("el vehiculo no esta disponible");
		} catch (VehiculoNoValidoException e) {
			fail("el vehiculo no esta en el hashmap de vehiculos");
		} catch (ClienteConViajePendienteException e) {
			fail("el cliente esta en viaje");
		}
	}
	
	@Test
	public void test_creo_viaje_chofer_no_registrado() {
		Chofer chofer_no_registrado = new ChoferTemporario("333","jorge");
		Pedido pedido = new Pedido(this.user1,4,true,true,5,Constantes.ZONA_STANDARD);
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Empresa.getInstance().crearViaje(pedido, chofer_no_registrado, this.vehiculo1);
			fail("no tendria que haber creado el viaje, el chofer no esta registrado");
		} catch (PedidoInexistenteException e) {
			fail("el pedido no existe");
		} catch (ChoferNoDisponibleException e) {
			//esta ok
		} catch (VehiculoNoDisponibleException e) {
			fail("el vehiculo no esta disponible");
		} catch (VehiculoNoValidoException e) {
			fail("el vehiculo no esta en el hashmap de vehiculos");
		} catch (ClienteConViajePendienteException e) {
			fail("el cliente esta en viaje");
		}
	}
	
	@Test
	public void test_creo_viaje_vehiculo_no_disponible() {
		Pedido pedido = new Pedido(this.user1,4,true,true,5,Constantes.ZONA_STANDARD);
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Viaje viaje_iniciado_user2 = Empresa.getInstance().getViajesIniciados().get(this.user2);
			assertEquals(this.vehiculo2,viaje_iniciado_user2.getVehiculo());
			Empresa.getInstance().crearViaje(pedido, this.chofer1, this.vehiculo2);
			fail("no tendria que haber creado el viaje, el vehiculo no esta registrado");
		} catch (PedidoInexistenteException e) {
			fail("el pedido no existe");
		} catch (ChoferNoDisponibleException e) {
			fail("el chofer no esta disponible");
		} catch (VehiculoNoDisponibleException e) {
			//esta ok
		} catch (VehiculoNoValidoException e) {
			fail("el vehiculo no esta en el hashmap de vehiculos");
		} catch (ClienteConViajePendienteException e) {
			fail("el cliente esta en viaje");
		}
	}
	
	@Test
	public void test_creo_viaje_vehiculo_no_valido() {
		Pedido pedido = new Pedido(this.user1,4,true,true,5,Constantes.ZONA_STANDARD);
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Empresa.getInstance().crearViaje(pedido, this.chofer1, this.vehiculo3);
			fail("no tendria que haber creado el viaje, el vehiculo no es valido");
		} catch (PedidoInexistenteException e) {
			fail("el pedido no existe");
		} catch (ChoferNoDisponibleException e) {
			fail("el chofer no esta disponible");
		} catch (VehiculoNoDisponibleException e) {
			fail("el vehiculo esta en viaje");
		} catch (VehiculoNoValidoException e) {
			//esta ok
		} catch (ClienteConViajePendienteException e) {
			fail("el cliente esta en viaje");
		}
	}
	
	@Test
	public void test_creo_viaje_vehiculo_no_registrado() {
		Vehiculo vehiculo_no_registrado = new Auto("zzz",4,true);
		Pedido pedido = new Pedido(this.user1,4,true,true,5,Constantes.ZONA_STANDARD);
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Empresa.getInstance().crearViaje(pedido, this.chofer1, vehiculo_no_registrado);
			fail("no tendria que haber creado el viaje, el vehiculo no esta registrado");
		} catch (PedidoInexistenteException e) {
			fail("el pedido no existe");
		} catch (ChoferNoDisponibleException e) {
			fail("el chofer no esta disponible");
		} catch (VehiculoNoDisponibleException e) {
			//esta ok
		} catch (VehiculoNoValidoException e) {
			fail("el vehiculo no es valido");
		} catch (ClienteConViajePendienteException e) {
			fail("el cliente esta en viaje");
		}
	}
	
	public void crea_viaje_anterior(Cliente cliente) {
		Pedido pedido_test = new Pedido(cliente,4,false,false,5,Constantes.ZONA_STANDARD);
		try {
			Empresa.getInstance().agregarPedido(pedido_test);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Empresa.getInstance().crearViaje(pedido_test, chofer2, vehiculo2);
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
		Empresa.getInstance().getChoferesDesocupados().clear();
	}

}
