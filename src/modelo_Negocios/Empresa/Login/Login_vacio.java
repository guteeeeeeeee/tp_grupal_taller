package modelo_Negocios.Empresa.Login;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;

public class Login_vacio {

	@Test
	public void login_sin_usuarios() {
		try {
			Empresa.getInstance().login("a","b");
			fail("logea al usuario cuando no hay ninguno registrado");
		} catch (UsuarioNoExisteException e) {
			//esta ok
		} catch(PasswordErroneaException e) {
			fail("dice password erronea y no hay ningun usuario en el sistema");
		}
	}
	
	@Test
	public void login_sin_usuarios_admin() {
		try {
			Empresa.getInstance().login("admin","admin");
			//esta ok
		} catch (UsuarioNoExisteException e) {
			fail("dice que el admin no existe");
		} catch(PasswordErroneaException e) {
			fail("dice password erronea y no hay ningun usuario en el sistema");
		}
	}

	@After
	public void limpio() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
	}
	
}
