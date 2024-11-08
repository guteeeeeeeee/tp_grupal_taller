package modelo_Datos.Vehiculo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloNegocio.Empresa;
import util.Constantes;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;

public class GetPuntajePedido_auto_pf {

	Vehiculo auto;
	Cliente user_logeado;
	
	@Before
	public void setUp() throws Exception {
		this.auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarCliente("a", "aaa", "nombre");
		this.user_logeado = (Cliente) Empresa.getInstance().login("a", "aaa");
	}

	@Test
	public void test_pedido_1_pasajero() {
		Pedido pedido = new Pedido(this.user_logeado,1,false,false,5,Constantes.ZONA_STANDARD);
		int puntaje = this.auto.getPuntajePedido(pedido);
		assertEquals(30,puntaje);
	}
	
	@Test
	public void test_pedido_2_pasajeros() {
		Pedido pedido = new Pedido(this.user_logeado,2,false,false,5,Constantes.ZONA_STANDARD);
		int puntaje = this.auto.getPuntajePedido(pedido);
		assertEquals(60,puntaje);
	}
	
	@Test
	public void test_pedido_2_pasajeros_con_mascota() {
		Pedido pedido = new Pedido(this.user_logeado,2,true,false,5,Constantes.ZONA_STANDARD);
		int puntaje = this.auto.getPuntajePedido(pedido);
		assertEquals(60,puntaje);
	}
	
	@Test
	public void test_pedido_2_pasajeros_con_baul() {
		Pedido pedido = new Pedido(this.user_logeado,2,false,true,5,Constantes.ZONA_STANDARD);
		int puntaje = this.auto.getPuntajePedido(pedido);
		assertEquals(80,puntaje);
	}
	
	@Test
	public void test_pedido_4_pasajeros() {
		Pedido pedido = new Pedido(this.user_logeado,4,false,false,5,Constantes.ZONA_STANDARD);
		assertNull(this.auto.getPuntajePedido(pedido));
	}

	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
	}

}
