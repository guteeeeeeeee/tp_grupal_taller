package modelo_Negocios.Empresa.vehiculosOrdenadosPorPedido;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;

public class VehiculosOrdenados_con_moto_auto_y_combi {

	Cliente cliente_logeado;
	Vehiculo moto,auto,combi;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("jorge123","123","jorge fernandez");
		this.cliente_logeado = (Cliente) Empresa.getInstance().login("jorge123","123");
		this.moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		this.auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarVehiculo(auto);
		this.combi = new Combi("ccc111",8,false);
		Empresa.getInstance().agregarVehiculo(combi);
	}
	
	@Test
	public void vehiculosOrdenados_con_moto_auto_y_combi_1_pasajero() {
		Pedido pedido_test = new Pedido(this.cliente_logeado,1,false,false,5,Constantes.ZONA_SIN_ASFALTAR);
		ArrayList<Vehiculo> vehiculos = Empresa.getInstance().vehiculosOrdenadosPorPedido(pedido_test);
		assertEquals(3,vehiculos.size());
		assertEquals(this.moto,vehiculos.getFirst());
		assertEquals(this.auto,vehiculos.get(1));
		assertEquals(this.combi,vehiculos.getLast());
	}
	
	@Test
	public void vehiculosOrdenados_con_moto_auto_y_combi_3_pasajeros_con_mascota() {
		Pedido pedido_test = new Pedido(this.cliente_logeado,3,true,false,5,Constantes.ZONA_SIN_ASFALTAR);
		ArrayList<Vehiculo> vehiculos = Empresa.getInstance().vehiculosOrdenadosPorPedido(pedido_test);
		assertEquals(1,vehiculos.size());
		assertEquals(this.auto,vehiculos.getFirst());
	}
	
	@Test
	public void vehiculosOrdenados_con_moto_auto_y_combi_3_pasajeros_sin_mascota() {
		Pedido pedido_test = new Pedido(this.cliente_logeado,3,false,false,5,Constantes.ZONA_SIN_ASFALTAR);
		ArrayList<Vehiculo> vehiculos = Empresa.getInstance().vehiculosOrdenadosPorPedido(pedido_test);
		assertEquals(2,vehiculos.size());
		assertEquals(this.auto,vehiculos.getFirst());
		assertEquals(this.combi,vehiculos.getLast());
	}
	
	@Test
	public void vehiculosOrdenados_con_moto_auto_y_combi_7_pasajeros_sin_mascota() {
		Pedido pedido_test = new Pedido(this.cliente_logeado,7,false,false,5,Constantes.ZONA_SIN_ASFALTAR);
		ArrayList<Vehiculo> vehiculos = Empresa.getInstance().vehiculosOrdenadosPorPedido(pedido_test);
		assertEquals(1,vehiculos.size());
		assertEquals(this.combi,vehiculos.getFirst());
	}
	
	@Test
	public void vehiculosOrdenados_con_moto_auto_combi_10_pasajeros() {
		Pedido pedido_test = new Pedido(this.cliente_logeado,10,true,false,5,Constantes.ZONA_SIN_ASFALTAR);
		ArrayList<Vehiculo> vehiculos = Empresa.getInstance().vehiculosOrdenadosPorPedido(pedido_test);
		assertEquals(0,vehiculos.size());
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
	}

}
