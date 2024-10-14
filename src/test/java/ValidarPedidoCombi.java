import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class ValidarPedidoCombi {

	@BeforeClass
	public static void set() throws Exception{
		Empresa.getInstance().agregarCliente("pepe", "123", "jose castro");
	}
	
	@Before
	public void setUp() throws Exception {
		Vehiculo combi1 = new Combi("213257",7,false);
		Empresa.getInstance().agregarVehiculo(combi1);
	}
	
	@Test
	public void test_autos_satisfacen() throws UsuarioNoExisteException, PasswordErroneaException {
		Cliente cliente = (Cliente)Empresa.getInstance().login("pepe", "123");
		Pedido pedido = new Pedido(cliente,6,false,false,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_autos_no_cant_personas() throws UsuarioNoExisteException, PasswordErroneaException {
		Cliente cliente = (Cliente)Empresa.getInstance().login("pepe", "123");
		Pedido pedido = new Pedido(cliente,10,false,false,10,"ZONA_STANDARD");
		assertFalse(Empresa.getInstance().validarPedido(pedido)); //mal validado
	}
	
	@Test
	public void test_autos_no_pf() throws UsuarioNoExisteException, PasswordErroneaException {
		Cliente cliente = (Cliente)Empresa.getInstance().login("pepe", "123");
		Pedido pedido = new Pedido(cliente,6,true,false,10,"ZONA_STANDARD");
		assertFalse(Empresa.getInstance().validarPedido(pedido));
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getVehiculos().clear();
	}

}
