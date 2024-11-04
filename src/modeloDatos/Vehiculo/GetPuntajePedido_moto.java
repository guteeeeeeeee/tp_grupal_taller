package modeloDatos.Vehiculo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class GetPuntajePedido_moto {

	Vehiculo moto;
	Cliente user_logeado;
	
	@Before
	public void setUp() throws Exception {
		this.moto = new Moto("mmm111");
		Empresa.getInstance().agregarCliente("a", "aaa", "nombre");
		this.user_logeado = (Cliente) Empresa.getInstance().login("a", "aaa");
	}

	@Test
	public void test_pedido_1_pasajero() {
		Pedido pedido = new Pedido(this.user_logeado,1,false,false,5,"ZONA_STANDARD");
		int puntaje = this.moto.getPuntajePedido(pedido);
		assertEquals(1000,puntaje);
	}
	
	@Test
	public void test_pedido_2_pasajeros() {
		Pedido pedido = new Pedido(this.user_logeado,2,false,false,5,"ZONA_STANDARD");
		assertNull(this.moto.getPuntajePedido(pedido));
	}

	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
	}
}
