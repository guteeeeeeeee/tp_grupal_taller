package modelo_Negocios.Empresa.Login;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;

public class Login_no_vacio {
	String nombre_usuario = "a";
	String password = "b";
	String nombre_completo = "c";
	
	@Before
	public void setUp() throws Exception {
		try {
			Empresa.getInstance().agregarCliente(this.nombre_usuario,this.password,this.nombre_completo);
		} catch (UsuarioYaExisteException e) {
			//e.printStackTrace();
		}
	}

	@Test
	public void login_correcto() {
		try {
			assertNull(Empresa.getInstance().getUsuarioLogeado());
			Empresa.getInstance().login(this.nombre_usuario,this.password);
			assertEquals(this.nombre_usuario,Empresa.getInstance().getUsuarioLogeado().getNombreUsuario());
			assertEquals(this.password,Empresa.getInstance().getUsuarioLogeado().getPass());
			assertNotNull(Empresa.getInstance().getUsuarioLogeado());
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {
			fail("no logea correctamente al usuario");
			//e.printStackTrace();
		}
	}
	
	@Test
	public void login_correcto_admin() {
		try {
			assertNull(Empresa.getInstance().getUsuarioLogeado());
			Empresa.getInstance().login("admin","admin");
			assertEquals("admin",Empresa.getInstance().getUsuarioLogeado().getNombreUsuario());
			assertEquals("admin",Empresa.getInstance().getUsuarioLogeado().getPass());
			assertNotNull(Empresa.getInstance().getUsuarioLogeado());
			assertTrue(Empresa.getInstance().isAdmin());
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {
			fail("no logea correctamente al usuario");
			//e.printStackTrace();
		}
	}
	
	@Test
	public void login_password_incorrecta() {
		try {
			Empresa.getInstance().login(this.nombre_usuario,"z");
			fail("se logea con la password incorrecta");
		} catch (UsuarioNoExisteException e) {
			fail("usuario incorrecto cuando en realidad esta mal la password");
		} catch(PasswordErroneaException e) {
			//esta ok
			//e.printStackTrace();
		}
	}

	@Test
	public void login_usuario_inexistente() {
		try {
			Empresa.getInstance().login("b","b");
			fail("logea con un nombre de usuario inexistente");
		} catch (UsuarioNoExisteException e) {
			//esta ok
			//e.printStackTrace();
		} catch(PasswordErroneaException e) {
			fail("password erronea cuando en realidad esta mal el nombre de usuario");
		}
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
	}

}
