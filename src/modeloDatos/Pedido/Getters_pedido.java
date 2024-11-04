package modeloDatos.Pedido;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import modeloNegocio.Empresa;

public class Getters_pedido {

	Pedido pedido;
	Cliente user_logeado;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("a", "aaa", "user");
		this.user_logeado = (Cliente) Empresa.getInstance().login("a","aaa");
		this.pedido = new Pedido(this.user_logeado,4,true,true,5,"ZONA_PELIGROSA");
	}

	@Test
	public void test_getters_pedido() {
		assertEquals(4,this.pedido.getCantidadPasajeros());
		assertEquals(this.user_logeado,this.pedido.getCliente());
		assertEquals(5,this.pedido.getKm());
		assertEquals("ZONA_PELIGROSA",this.pedido.getZona());
		assertTrue(this.pedido.isMascota());
		assertTrue(this.pedido.isBaul());
	}

}
