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

public class Exception_chofer_repetido {

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
	public void test_chofer_repetido_exception() {
		Chofer chofer_repetido = new ChoferTemporario(this.chofer1.getDni(),"carlos");
		try {
			Empresa.getInstance().agregarChofer(chofer_repetido);
			fail("no lanza la exception de chofer repetido");
		} catch (ChoferRepetidoException e) {
			assertEquals("no coincide el chofer agregado con el de la exception",chofer_repetido,e.getChoferExistente());
			assertEquals("no coincide el dni duplicado",this.chofer1.getDni(),e.getDniPrentendido());
			assertEquals("el mensaje no indica que esta repetido",Mensajes.CHOFER_YA_REGISTRADO.getValor(),e.getMessage());
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
