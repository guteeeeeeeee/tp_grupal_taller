package modelo_Negocios.Empresa.vehiculosOrdenadosPorPedido;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;

public class VehiculosOrdenados_con_moto {

	Cliente cliente_logeado;
	Vehiculo moto;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("jorge123","123","jorge fernandez");
		this.cliente_logeado = (Cliente) Empresa.getInstance().login("jorge123","123");
		this.moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
	}
	
	@Test
	public void vehiculosOrdenados_con_moto_1_pasajero() {
		Pedido pedido_test = new Pedido(this.cliente_logeado,1,false,false,5,Constantes.ZONA_SIN_ASFALTAR);
		ArrayList<Vehiculo> vehiculos = Empresa.getInstance().vehiculosOrdenadosPorPedido(pedido_test);
		assertEquals(1,vehiculos.size());
		assertEquals(this.moto,vehiculos.getFirst());
	}
	
	@Test
	public void vehiculosOrdenados_con_moto_3_pasajeros() {
		Pedido pedido_test = new Pedido(this.cliente_logeado,3,true,false,5,Constantes.ZONA_SIN_ASFALTAR);
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
