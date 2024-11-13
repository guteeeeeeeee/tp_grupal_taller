package modelo_Negocios.Empresa.Getters_Setters;

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

public class Empresa_getHistorialViajeCliente {

	Cliente user_sin_viajes,user_en_viaje,user_con_viajes;
	Chofer chofer1,chofer2,chofer_sin_viajes;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("aatta", "123", "dario");
		Empresa.getInstance().agregarCliente("ababa", "123", "carlos");
		Empresa.getInstance().agregarCliente("dasodj", "123", "ernesto");
		this.user_sin_viajes = Empresa.getInstance().getClientes().get("aatta");
		this.user_en_viaje = Empresa.getInstance().getClientes().get("ababa");
		this.user_con_viajes = Empresa.getInstance().getClientes().get("dasodj");

		this.chofer1 = new ChoferTemporario("737373","jorge");
		this.chofer2 = new ChoferTemporario("65656","montano");
		this.chofer_sin_viajes = new ChoferTemporario("1222","robert");
		Empresa.getInstance().agregarChofer(chofer1);
		Empresa.getInstance().agregarChofer(chofer2);
		
		Vehiculo moto = new Moto("mmm111");
		Vehiculo auto = new Auto("aaa111",4,true);
		Vehiculo combi = new Combi("ccc111",5,true);
		Empresa.getInstance().agregarVehiculo(moto);
		Empresa.getInstance().agregarVehiculo(auto);
		Empresa.getInstance().agregarVehiculo(combi);

		Pedido pedido1 = new Pedido(this.user_en_viaje,3,false,false,5,Constantes.ZONA_PELIGROSA);
		Empresa.getInstance().agregarPedido(pedido1);
		Empresa.getInstance().crearViaje(pedido1, chofer1, auto);
		
		Pedido pedido2 = new Pedido(this.user_con_viajes,3,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido2);
		Empresa.getInstance().crearViaje(pedido2, chofer2, combi);
		Empresa.getInstance().login("dasodj", "123");
		Empresa.getInstance().pagarYFinalizarViaje(3);
		
		Pedido pedido3 = new Pedido(this.user_con_viajes,1,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido3);
		Empresa.getInstance().crearViaje(pedido3, chofer2, moto);
		Empresa.getInstance().pagarYFinalizarViaje(4);
	}

	@Test
	public void getHistorialViajeCliente_sin_viajes() {
		assertEquals(0,Empresa.getInstance().getHistorialViajeCliente(user_sin_viajes).size());
	}
	
	@Test
	public void getHistorialViajeCliente_en_viaje() {
		assertEquals(0,Empresa.getInstance().getHistorialViajeCliente(user_en_viaje).size());
	}
	
	@Test
	public void getHistorialViajeCliente_con_2_viajes() {
		assertEquals(2,Empresa.getInstance().getHistorialViajeCliente(user_con_viajes).size());
	}
	
	@Test
	public void getHistorialViajeCliente_no_existe() {
		Cliente cliente_no_existe = new Cliente("no_existo","123","roberto");
		assertEquals(0,Empresa.getInstance().getHistorialViajeCliente(cliente_no_existe).size());
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getChoferesDesocupados().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getViajesTerminados().clear();
	}
	
}
