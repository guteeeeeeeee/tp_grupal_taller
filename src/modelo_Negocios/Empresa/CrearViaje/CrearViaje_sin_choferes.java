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

public class CrearViaje_sin_choferes {

	Cliente user1;
	Cliente user2;
	Vehiculo vehiculo1;
	Vehiculo vehiculo2;
	Vehiculo vehiculo3;
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
				
		this.pedido1 = new Pedido(this.user1,4,true,true,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido1);
		
		Pedido pedido2 = new Pedido(this.user2,4,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido2);
	}

	@Test
	public void test_creo_viaje_sin_choferes() {
		Chofer chofer = new ChoferTemporario("444","robert");
		try {
			Empresa.getInstance().crearViaje(this.pedido1, chofer, this.vehiculo1);
			fail("crea viaje cuando no tiene ningun chofer registrado");
		} catch (PedidoInexistenteException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			fail("exception equivocada");
		} catch (ChoferNoDisponibleException e) { 
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
