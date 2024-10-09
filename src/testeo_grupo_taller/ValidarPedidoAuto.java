package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class ValidarPedidoAuto {

	@BeforeClass
	public static void set() throws Exception{
		Empresa.getInstance().agregarCliente("pepe", "123", "jose castro");
	}
	
	@Before
	public void setUp() throws Exception {
		Vehiculo auto1 = new Auto("213257",3,false);
		Empresa.getInstance().agregarVehiculo(auto1);
	}
	
	@Test
	public void test_autos_satisfacen() throws UsuarioNoExisteException, PasswordErroneaException {
		Cliente cliente = (Cliente)Empresa.getInstance().login("pepe", "123");
		Pedido pedido = new Pedido(cliente,3,false,false,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_autos_no_cant_personas() throws UsuarioNoExisteException, PasswordErroneaException {
		Cliente cliente = (Cliente)Empresa.getInstance().login("pepe", "123");
		Pedido pedido = new Pedido(cliente,4,false,false,10,"ZONA_STANDARD");
		assertFalse(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_autos_no_pf() throws UsuarioNoExisteException, PasswordErroneaException {
		Cliente cliente = (Cliente)Empresa.getInstance().login("pepe", "123");
		Pedido pedido = new Pedido(cliente,3,true,false,10,"ZONA_STANDARD");
		assertFalse(Empresa.getInstance().validarPedido(pedido));
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getVehiculos().clear();
	}

}
