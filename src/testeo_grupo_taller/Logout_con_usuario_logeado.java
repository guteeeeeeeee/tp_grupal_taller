package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloNegocio.Empresa;

public class Logout_con_usuario_logeado {

	Cliente user_logeado;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("a", "aaa", "user logeado");
		this.user_logeado = (Cliente) Empresa.getInstance().login("a", "aaa");
	}

	@Test
	public void test_logout_user_logeado() {
		assertEquals(this.user_logeado,Empresa.getInstance().getUsuarioLogeado());
		Empresa.getInstance().logout();
		assertNull(Empresa.getInstance().getUsuarioLogeado());
	}
	
}
