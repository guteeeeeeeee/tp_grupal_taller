package modelo_Negocios.Empresa.ValidarPedido;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Moto;
import modeloDatos.Auto;
import modeloNegocio.Empresa;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;

public class ValidarPedido_moto {
		Cliente user_logeado;
		
		@Before
		public void setUp() throws Exception {
			Empresa.getInstance().agregarCliente("a", "aaa", "jose");
			this.user_logeado = (Cliente)Empresa.getInstance().login("a", "aaa");
			Vehiculo moto = new Moto("mmm111");
			Empresa.getInstance().agregarVehiculo(moto);
		}
		
		@Test
		public void test_pedido_con_moto_moto() throws UsuarioNoExisteException, PasswordErroneaException {
			Pedido pedido = new Pedido(this.user_logeado,1,false,false,10,"ZONA_STANDARD");
			assertTrue(Empresa.getInstance().validarPedido(pedido));
		}
		
		@Test
		public void test_pedido_con_moto_auto() throws UsuarioNoExisteException, PasswordErroneaException {
			Pedido pedido = new Pedido(this.user_logeado,2,false,false,10,"ZONA_STANDARD");
			assertFalse(Empresa.getInstance().validarPedido(pedido));
		}
		
		
		@Test
		public void test_pedido_con_moto_combi() throws UsuarioNoExisteException, PasswordErroneaException {
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