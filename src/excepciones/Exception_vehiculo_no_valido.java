package excepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

public class Exception_vehiculo_no_valido {
	Cliente user1;
	Chofer chofer1,chofer2;
	Pedido pedido1;
	Vehiculo auto,moto;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("pepe123", "aaa", "alfonso");
		Empresa.getInstance().agregarCliente("alfa", "aaa", "teo");
		this.user1 = (Cliente) Empresa.getInstance().login("pepe123","aaa");
		Cliente user2 = (Cliente) Empresa.getInstance().login("alfa","aaa");
		
		this.chofer1 = new ChoferTemporario("123","chofer");
		Empresa.getInstance().agregarChofer(chofer1);
		this.chofer2 = new ChoferTemporario("555","segundo");
		Empresa.getInstance().agregarChofer(chofer2);

		this.moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		this.auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarVehiculo(auto);

		Combi combi = new Combi("ccc111",10,true);
		Empresa.getInstance().agregarVehiculo(combi);
		this.pedido1 = new Pedido(user2,10,true,true,10,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido1);
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
		Empresa.getInstance().agregarVehiculo(this.auto);
		Empresa.getInstance().agregarVehiculo(this.moto);
	}
	
	@Test
	public void test_vehiculo_no_valido() throws SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException, VehiculoRepetidoException {
		try {
			Empresa.getInstance().crearViaje(this.pedido1, chofer1, moto);
			fail("no lanza la exception que el vehiculo no es valido");
		} catch (PedidoInexistenteException | ChoferNoDisponibleException
				| VehiculoNoDisponibleException | ClienteConViajePendienteException e) {
			fail("lanza la exception equivocada");
		} catch (VehiculoNoValidoException e) {
			assertEquals("el vehiculo de la exception no coincide con el ingresado",this.moto, e.getVehiculo());
			assertEquals("el pedido de la exception no coincide con el ingresado",this.pedido1, e.getPedido());
			assertEquals("el mensaje no indica que el vehiculo no es valido para el pedido ingresado",Mensajes.VEHICULO_NO_VALIDO.getValor(),e.getMessage());
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
