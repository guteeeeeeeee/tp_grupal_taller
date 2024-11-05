package modelo_Negocios.Empresa.Logout;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloNegocio.Empresa;

public class Logout_sin_usuarios {

	@Test
	public void test_logout_sin_clientes_registrados() {
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		Empresa.getInstance().logout();
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
	}
}
