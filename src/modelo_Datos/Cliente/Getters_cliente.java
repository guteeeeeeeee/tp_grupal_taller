package modelo_Datos.Cliente;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class Getters_cliente {

	Cliente cliente;
	String nombre_completo;
	
	@Before
	public void setUp() throws Exception {
		this.nombre_completo = "g";
		this.cliente = new Cliente("nombre_usuario","password",this.nombre_completo);
	}

	@Test
	public void test_get_nombre_real_cliente() {
		assertEquals(this.nombre_completo,this.cliente.getNombreReal());
	}
	
}
