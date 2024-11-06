package modelo_Datos.Vehiculo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Combi;
import modeloNegocio.Empresa;
import modeloDatos.*;

public class GetPuntajePedido_combi {

	Vehiculo combi;
	Cliente user_logeado;
	
	@Before
	public void setUp() throws Exception {
		this.combi = new Combi("ccc111",7,false);
		Empresa.getInstance().agregarCliente("a", "aaa", "nombre");
		this.user_logeado = (Cliente) Empresa.getInstance().login("a", "aaa");
	}

	@Test
	public void test_pedido_1_pasajero() {
		Pedido pedido = new Pedido(this.user_logeado,1,false,false,5,"ZONA_STANDARD");
		int puntaje = this.combi.getPuntajePedido(pedido);
		assertEquals(10,puntaje);
	}
	
	@Test
	public void test_pedido_2_pasajeros() {
		Pedido pedido = new Pedido(this.user_logeado,2,false,false,5,"ZONA_STANDARD");
		int puntaje = this.combi.getPuntajePedido(pedido);
		assertEquals(20,puntaje);
	}
	
	@Test
	public void test_pedido_2_pasajeros_con_mascota() {
		Pedido pedido = new Pedido(this.user_logeado,2,true,false,5,"ZONA_STANDARD");
		assertNull(this.combi.getPuntajePedido(pedido));
	}
	
	@Test
	public void test_pedido_5_pasajeros() {
		Pedido pedido = new Pedido(this.user_logeado,5,false,false,5,"ZONA_STANDARD");
		int puntaje = this.combi.getPuntajePedido(pedido);
		assertEquals(50,puntaje);
	}
	
	@Test
	public void test_pedido_5_pasajeros_con_baul() {
		Pedido pedido = new Pedido(this.user_logeado,5,false,true,5,"ZONA_STANDARD");
		int puntaje = this.combi.getPuntajePedido(pedido);
		assertEquals(120,puntaje);
	}
	
	@Test
	public void test_pedido_5_pasajeros_con_mascota() {
		Pedido pedido = new Pedido(this.user_logeado,5,true,false,5,"ZONA_STANDARD");
		assertNull(this.combi.getPuntajePedido(pedido));
	}

	@Test
	public void test_pedido_10_pasajeros() {
		Pedido pedido = new Pedido(this.user_logeado,10,false,false,5,"ZONA_STANDARD");
		assertNull(this.combi.getPuntajePedido(pedido));
	}

	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
	}

}
