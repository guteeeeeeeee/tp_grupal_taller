package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloNegocio.Empresa;

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

}
