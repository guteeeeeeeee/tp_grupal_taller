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
		String nombre_usuario = "a";
		String password = "aaa";
		Empresa.getInstance().agregarCliente(nombre_usuario,password, "user logeado");
		Empresa.getInstance().login(nombre_usuario, password);
		Empresa.getInstance().logout();
	}

	@Test
	public void test_logout_user_logeado() {
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		Empresa.getInstance().logout();
		assertNull(Empresa.getInstance().getUsuarioLogeado());
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
	}
}
