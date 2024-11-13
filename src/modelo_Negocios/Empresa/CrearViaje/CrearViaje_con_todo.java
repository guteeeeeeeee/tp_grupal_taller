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
	Pedido pedido1;
	
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
				
		this.pedido1 = new Pedido(this.user1,4,true,true,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido1);
		
		Pedido pedido2 = new Pedido(this.user2,4,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido2);
		Empresa.getInstance().crearViaje(pedido2, chofer2, vehiculo2);
	}

	@Test
	public void test_creo_viaje() {
		try {
			assertEquals(2,Empresa.getInstance().getVehiculosDesocupados().size());
			assertEquals(1,Empresa.getInstance().getChoferesDesocupados().size());
			Empresa.getInstance().crearViaje(this.pedido1, this.chofer1, this.vehiculo1);
			assertNotNull(Empresa.getInstance().getViajeDeCliente(this.user1));
			assertEquals(1,Empresa.getInstance().getVehiculosDesocupados().size());
			assertEquals(0,Empresa.getInstance().getChoferesDesocupados().size());
			assertEquals(this.chofer1,Empresa.getInstance().getViajesIniciados().get(this.user1).getChofer());
			assertEquals(this.vehiculo1,Empresa.getInstance().getViajesIniciados().get(this.user1).getVehiculo());
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			fail("tira exception cuando no debe");
		}
	}
	
	@Test
	public void test_creo_viaje_pedido_inexistente() throws SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException {
		Pedido pedido_no_agregado = new Pedido(this.user1,5,false,false,10,Constantes.ZONA_STANDARD);
		try {
			assertNull(Empresa.getInstance().getViajeDeCliente(this.user1));
			Empresa.getInstance().crearViaje(pedido_no_agregado, this.chofer1, this.vehiculo1);
			fail("crea viaje con un pedido que no fue agregado");
		} catch (ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			fail("exception incorrecta");
		} catch (PedidoInexistenteException e) {
			assertNull(Empresa.getInstance().getViajeDeCliente(this.user1));
		}
	}
	
	@Test
	public void test_creo_viaje_cliente_viaje_pendiente() throws SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException {
		Pedido pedido_nuevo = new Pedido(this.user2,4,true,true,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().agregarPedido(pedido_nuevo);
		try {
			assertNotNull(Empresa.getInstance().getViajeDeCliente(this.user2));
			Empresa.getInstance().crearViaje(pedido_nuevo, this.chofer1, this.vehiculo1);
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
		try {
			Viaje viaje_iniciado_user2 = Empresa.getInstance().getViajesIniciados().get(this.user2);
			assertEquals(this.chofer2,viaje_iniciado_user2.getChofer());
			Empresa.getInstance().crearViaje(this.pedido1, this.chofer2, this.vehiculo1);
			fail("no tendria que haber creado el viaje, el chofer esta en viaje");
		} catch (PedidoInexistenteException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			fail("exception equivocada");
		} catch (ChoferNoDisponibleException e) {
			//esta ok
			assertNull(Empresa.getInstance().getViajeDeCliente(user1));
		}
	}
	
	@Test
	public void test_creo_viaje_chofer_no_registrado() {
		Chofer chofer_no_registrado = new ChoferTemporario("333","jorge");
		try {
			Empresa.getInstance().crearViaje(this.pedido1, chofer_no_registrado, this.vehiculo1);
			fail("no tendria que haber creado el viaje, el chofer no esta registrado");
		} catch (PedidoInexistenteException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			fail("exception equivocada");
		} catch (ChoferNoDisponibleException e) {
			//esta ok
			assertNull(Empresa.getInstance().getViajeDeCliente(user1));
		}
	}
	
	@Test
	public void test_creo_viaje_vehiculo_no_disponible() {
		Viaje viaje_iniciado_user2 = Empresa.getInstance().getViajesIniciados().get(this.user2);
		assertEquals(this.vehiculo2,viaje_iniciado_user2.getVehiculo());
		try {
			Empresa.getInstance().crearViaje(this.pedido1, this.chofer1, this.vehiculo2);
			fail("no tendria que haber creado el viaje, el vehiculo no esta registrado");
		} catch (PedidoInexistenteException | ChoferNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			fail("exception equivocada");
		} catch (VehiculoNoDisponibleException e) {
			//esta ok
			assertNull(Empresa.getInstance().getViajeDeCliente(user1));
		}
	}
	
	@Test
	public void test_creo_viaje_vehiculo_no_valido() {
		try {
			Empresa.getInstance().crearViaje(pedido1, this.chofer1, this.vehiculo3);
			fail("no tendria que haber creado el viaje, el vehiculo no es valido");
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException | ClienteConViajePendienteException e) {
			fail("exception equivocada");
		} catch (VehiculoNoValidoException e) {
			//esta ok
			assertNull(Empresa.getInstance().getViajeDeCliente(user1));
		}
	}
	
	@Test
	public void test_creo_viaje_vehiculo_no_registrado() {
		Vehiculo vehiculo_no_registrado = new Auto("zzz",4,true);
		try {
			Empresa.getInstance().crearViaje(this.pedido1, this.chofer1, vehiculo_no_registrado);
			fail("no tendria que haber creado el viaje, el vehiculo no esta registrado");
		} catch (PedidoInexistenteException | ChoferNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			fail("exception equivocada");
		} catch (VehiculoNoDisponibleException e) {
			//esta ok
			assertNull(Empresa.getInstance().getViajeDeCliente(user1));
		}
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
		Empresa.getInstance().getChoferesDesocupados().clear();
	}

}
