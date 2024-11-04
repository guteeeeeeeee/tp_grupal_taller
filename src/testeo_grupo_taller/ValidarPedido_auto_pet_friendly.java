package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class ValidarPedido_auto_pet_friendly {

Cliente user_logeado;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("a", "aaa", "jose");
		this.user_logeado = (Cliente)Empresa.getInstance().login("a", "aaa");
		Vehiculo auto = new Auto("aaa111",3,true); //pet friendly
		Empresa.getInstance().agregarVehiculo(auto);
	}
	
	@Test
	public void test_pedido_con_auto_pedido_1_pasajero() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,1,false,false,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_pedido_con_auto_pedido_2_pasajeros_con_mascota() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,2,true,false,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_pedido_con_auto_pedido_3_pasajeros_sin_mascota() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,3,false,false,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_pedido_con_auto_pedido_4_pasajeros_sin_mascota() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,4,false,false,10,"ZONA_STANDARD");
		assertFalse(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_pedido_con_auto_pedido_5_pasajeros_sin_mascota() throws UsuarioNoExisteException, PasswordErroneaException {
		Pedido pedido = new Pedido(this.user_logeado,5,false,false,10,"ZONA_STANDARD");
		assertFalse(Empresa.getInstance().validarPedido(pedido));
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getVehiculos().clear();
	}

}
