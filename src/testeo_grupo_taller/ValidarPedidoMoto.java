package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class ValidarPedidoMoto {
	
	@BeforeClass
	public static void set() throws Exception{
		Empresa.getInstance().agregarCliente("pepe", "123", "jose castro");
	}
	
	@Before
	public void setUp() throws Exception {
		Vehiculo moto1 = new Moto("213257");
		Vehiculo moto2 = new Moto("21323");
		Empresa.getInstance().agregarVehiculo(moto1);
		//Empresa.getInstance().agregarVehiculo(moto2);
	}

	@Test
	public void test_motos_satisfacen() throws UsuarioNoExisteException, PasswordErroneaException, VehiculoRepetidoException {
		Cliente cliente = (Cliente)Empresa.getInstance().login("pepe", "123");
		Pedido pedido = new Pedido(cliente,1,false,false,10,"ZONA_STANDARD");
		assertTrue(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_motos_no_satisfacen_cantidad_personas() throws VehiculoRepetidoException, UsuarioNoExisteException, PasswordErroneaException {
		Cliente cliente = (Cliente)Empresa.getInstance().login("pepe", "123");
		Pedido pedido = new Pedido(cliente,5,false,false,10,"ZONA_STANDARD");
		assertFalse(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_motos_no_satisfacen_pf() throws VehiculoRepetidoException, UsuarioNoExisteException, PasswordErroneaException {
		Cliente cliente = (Cliente)Empresa.getInstance().login("pepe", "123");
		Pedido pedido = new Pedido(cliente,1,true,false,10,"ZONA_STANDARD");
		assertFalse(Empresa.getInstance().validarPedido(pedido));
	}
	
	@Test
	public void test_motos_no_satisfacen_baul() throws VehiculoRepetidoException, UsuarioNoExisteException, PasswordErroneaException {
		Cliente cliente = (Cliente)Empresa.getInstance().login("pepe", "123");
		Pedido pedido = new Pedido(cliente,1,false,true,10,"ZONA_STANDARD");
		assertFalse(Empresa.getInstance().validarPedido(pedido));
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().getVehiculos().clear();
	}
}
