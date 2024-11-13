package modelo_Negocios.Empresa.vehiculosOrdenadosPorPedido;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;

public class VehiculosOrdenados_sin_vehiculos {
	
	Cliente cliente_logeado;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("jorge123","123","jorge fernandez");
		this.cliente_logeado = (Cliente) Empresa.getInstance().login("jorge123","123");
	}
	
	@Test
	public void vehiculosOrdenados_sin_vehiculos() {
		Pedido pedido_test = new Pedido(this.cliente_logeado,1,false,false,5,Constantes.ZONA_SIN_ASFALTAR);
		ArrayList<Vehiculo> vehiculos = Empresa.getInstance().vehiculosOrdenadosPorPedido(pedido_test);
		assertEquals(0,vehiculos.size());
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getVehiculos().clear();
	}
}
