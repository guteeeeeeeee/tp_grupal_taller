package testeo_grupo_taller;

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
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class CrearViaje_sin_choferes {

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
		this.chofer2 = new ChoferTemporario("111","raul");
	}

	@Test
	public void test_creo_viaje_sin_choferes() {
		Pedido pedido = new Pedido(this.user1,4,true,true,5,"ZONA_STANDARD");
		try {
			Empresa.getInstance().agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Empresa.getInstance().crearViaje(pedido, this.chofer1, this.vehiculo1);
			fail("no tiene que crear el viaje, el hashmap de choferes esta vacio");
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
