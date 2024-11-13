package excepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;

public class Exception_sin_vehiculo_para_pedido {

	Cliente user1;
	Chofer chofer;
	Pedido pedido2;
	Vehiculo auto;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("pepe123", "aaa", "alfonso");
		Empresa.getInstance().agregarCliente("alfa", "aaa", "teo");
		this.user1 = (Cliente) Empresa.getInstance().login("pepe123","aaa");
		Cliente user2 = (Cliente) Empresa.getInstance().login("alfa","aaa");
		
		this.chofer = new ChoferTemporario("123","chofer");
		Empresa.getInstance().agregarChofer(chofer);

		Vehiculo moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		this.auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarVehiculo(auto);
		
		Pedido pedido1 = new Pedido(user2,1,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido1);
	}
	
	@Test
	public void test_no_hay_vehiculo_para_pedido() {
		Pedido pedido_nuevo = new Pedido(this.user1,10,false,false,5,Constantes.ZONA_STANDARD);
		try {
			Empresa.getInstance().agregarPedido(pedido_nuevo);
			fail("no lanza la exception de que no hay vehiculos para el pedido");
		} catch (ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			fail("lanza la exception equivocada");
		} catch (SinVehiculoParaPedidoException e) {
			assertEquals("no coincide el pedido de la exception con el ingresado",pedido_nuevo,e.getPedido());
			assertEquals("el mensaje no dice que no hay vehiculos para el pedido ingresado",Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(),e.getMessage());
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
