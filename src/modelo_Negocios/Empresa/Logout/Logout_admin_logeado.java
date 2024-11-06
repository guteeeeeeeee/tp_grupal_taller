package modelo_Negocios.Empresa.Logout;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import modeloNegocio.Empresa;
import modeloDatos.Cliente;

public class Logout_admin_logeado {

	Administrador admin;
	
	@Before
	public void setUp() throws Exception {
		this.admin = (Administrador) Empresa.getInstance().login("admin", "admin");
	}

	@Test
	public void test_logout_admin_logeado() {
		assertEquals(this.admin,Empresa.getInstance().getUsuarioLogeado());
		Empresa.getInstance().logout();
		assertNull(Empresa.getInstance().getUsuarioLogeado());
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
	}
}
