package modelo_Negocios.Empresa.agregarPedido;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import modeloDatos.Auto;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import util.Constantes;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;

public class AgregarPedido_cliente_en_viaje {

	Chofer chofer1,chofer2;
	Vehiculo moto,auto;
	Pedido pedido1,pedido2;
	Cliente user1,user2;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("pepe123", "aaa", "alfonso");
		Empresa.getInstance().agregarCliente("alfa", "aaa", "teo");
		this.user1 = (Cliente) Empresa.getInstance().login("pepe123","aaa");
		this.user2 = (Cliente) Empresa.getInstance().login("alfa","aaa");
		
		this.chofer1 = new ChoferTemporario("123","chofer");
		Empresa.getInstance().agregarChofer(this.chofer1);
		this.chofer2 = new ChoferTemporario("777","chofer 2");
		Empresa.getInstance().agregarChofer(this.chofer2);

		this.moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(this.moto);
		this.auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarVehiculo(auto);
		
		this.pedido1 = new Pedido(this.user2,1,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(this.pedido1);
		
		Empresa.getInstance().crearViaje(pedido1, chofer1, moto);
	}
	
	@Test
	public void test_agregar_pedido_cliente_que_no_esta_en_viaje() {
		int cant_pasajeros = 3;
		boolean mascota = true;
		boolean baul = true;
		int cant_km = 10;
		String zona = Constantes.ZONA_STANDARD;
		Pedido pedido_nuevo = new Pedido(this.user1,cant_pasajeros,mascota,baul,cant_km,zona);
		try {
			assertEquals(0,Empresa.getInstance().getPedidos().size());
			Empresa.getInstance().agregarPedido(pedido_nuevo);
			assertEquals(1,Empresa.getInstance().getPedidos().size());
			assertEquals(this.user1,Empresa.getInstance().getPedidos().get(this.user1).getCliente());
			assertEquals(cant_pasajeros,Empresa.getInstance().getPedidos().get(this.user1).getCantidadPasajeros());
			assertEquals(cant_km,Empresa.getInstance().getPedidos().get(this.user1).getKm());
			assertEquals(zona,Empresa.getInstance().getPedidos().get(this.user1).getZona());
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			fail("tira excepcion cuando no debe");
		}
	}
	
	@Test
	public void test_agregar_pedido_no_vehiculo_satisface_en_viaje() {
		int cant_pasajeros = 7;
		boolean mascota = true;
		boolean baul = true;
		int cant_km = 10;
		String zona = Constantes.ZONA_STANDARD;
		Pedido pedido_nuevo = new Pedido(this.user1,cant_pasajeros,mascota,baul,cant_km,zona);
		try {
			assertEquals(0,Empresa.getInstance().getPedidos().size());
			Empresa.getInstance().agregarPedido(pedido_nuevo);
			fail("debe tirar excepcion de que no hay vehiculos disponibles para el pedido");
		}catch (SinVehiculoParaPedidoException e) {
			//esta ok
			assertEquals(0,Empresa.getInstance().getPedidos().size());
		}catch (ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			fail("tira la excepcion incorrecta");
		}
	}
	
	@Test
	public void test_agregar_pedido_cliente_en_viaje() {
		int cant_pasajeros = 3;
		boolean mascota = true;
		boolean baul = true;
		int cant_km = 10;
		String zona = Constantes.ZONA_STANDARD;
		Pedido pedido_nuevo = new Pedido(this.user2,cant_pasajeros,mascota,baul,cant_km,zona);
		try {
			assertEquals(0,Empresa.getInstance().getPedidos().size());
			assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
			assertNotNull(Empresa.getInstance().getViajeDeCliente(this.user2));
			Empresa.getInstance().agregarPedido(pedido_nuevo);
			fail("debe tirar excepcion de que el cliente esta en viaje");
		}catch (ClienteConViajePendienteException e) {
			//esta ok
			assertEquals(0,Empresa.getInstance().getPedidos().size());
		}catch (SinVehiculoParaPedidoException | ClienteConPedidoPendienteException
				| ClienteNoExisteException e) {
			fail("tira la excepcion incorrecta");
		}
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getChoferesDesocupados().clear();
		Empresa.getInstance().getViajesIniciados().clear();
	}

}
