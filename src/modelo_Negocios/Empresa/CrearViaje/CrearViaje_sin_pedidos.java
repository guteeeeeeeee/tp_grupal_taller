package modelo_Negocios.Empresa.CrearViaje;

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
import modeloNegocio.Empresa;
import util.Constantes;
import modeloDatos.*;

public class CrearViaje_sin_pedidos {

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
	}

	@Test
	public void test_creo_viaje_sin_pedidos() {
		try {
			Empresa.getInstance().crearViaje(this.pedido1, this.chofer1, this.vehiculo1);
			fail("crea viaje cuando no tiene ningun pedido registrado");
		} catch (ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			fail("exception equivocada");
		} catch (PedidoInexistenteException e) { 
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
