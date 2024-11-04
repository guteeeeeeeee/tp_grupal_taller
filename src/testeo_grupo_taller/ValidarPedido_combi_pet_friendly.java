package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class ValidarPedido_combi_pet_friendly {

	Cliente user_logeado;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("a", "aaa", "jose");
		this.user_logeado = (Cliente)Empresa.getInstance().login("a", "aaa");
		Vehiculo combi = new Combi("ccc111",8,true);
		Empresa.getInstance().agregarVehiculo(combi);
	}
	
	@Test
	public void test_pedido_con_combi_pedido_1_pasajero() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,1,false,false,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_pedido_con_combi_pedido_4_pasajeros_sin_mascota() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,4,false,true,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_pedido_con_combi_pedido_4_pasajeros_con_mascota() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,4,true,true,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_pedido_con_combi_pedido_7_pasajeros_sin_mascota() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,7,false,true,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_pedido_con_combi_pedido_7_pasajeros_con_mascota() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,7,true,true,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_pedido_con_combi_pedido_9_pasajeros() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,9,false,true,10,"ZONA_STANDARD");
		assertFalse(Empresa.getInstance().validarPedido(pedido));
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getVehiculos().clear();
	}

}
