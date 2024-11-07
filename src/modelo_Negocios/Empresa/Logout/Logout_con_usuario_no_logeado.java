package modelo_Negocios.Empresa.Logout;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloNegocio.Empresa;
import modeloDatos.Cliente;

public class Logout_con_usuario_no_logeado {
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("a", "aaa", "user logeado");
	}

	@Test
	public void test_logout_user_logeado() {
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		Empresa.getInstance().logout();
		assertNull(Empresa.getInstance().getUsuarioLogeado());
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
	}
}
