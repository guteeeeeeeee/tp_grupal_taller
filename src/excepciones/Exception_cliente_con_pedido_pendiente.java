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

public class Exception_cliente_con_pedido_pendiente {

	Cliente user1;
	Chofer chofer1,chofer2;
	Pedido pedido2;
	Vehiculo auto;
	
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

		Vehiculo moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		this.auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarVehiculo(auto);
		
		Pedido pedido1 = new Pedido(user2,1,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido1);
		this.pedido2 = new Pedido(user1,3,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido2);
		
		Empresa.getInstance().crearViaje(pedido1, chofer1, moto);
	}

	@Test
	public void test_cliente_pedido_pendiente() {
		Pedido pedido2 = new Pedido(this.user1,3,false,false,5,Constantes.ZONA_PELIGROSA);
		try {
			Empresa.getInstance().agregarPedido(pedido2);
			fail("no lanza la exception de cliente con pedido pendiente");
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException e) {
			fail("exception equivocada");
		} catch(ClienteConPedidoPendienteException e) {
			assertEquals("el mensaje no indica que el cliente ya tiene un pedido pendiente",Mensajes.CLIENTE_CON_PEDIDO_PENDIENTE.getValor(),e.getMessage());
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
