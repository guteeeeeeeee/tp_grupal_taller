package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;

public class Login {
	String nombre_usuario = "jorge123";
	String password = "123";
	String nombre_completo = "jorge fernandez";
	
	@Before
	public void setUp() throws Exception { //tendria que mockear el metodo agregarCliente ?
		try {
			Empresa.getInstance().agregarCliente(this.nombre_usuario,this.password,this.nombre_completo);
		} catch (UsuarioYaExisteException e) {
			//e.printStackTrace();
		}
	}

	@Test
	public void login_correcto() {
		try {
			Empresa.getInstance().login(this.nombre_usuario,this.password);
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {
			fail("no logea correctamente al usuario");
			//e.printStackTrace();
		}
	}
	
	@Test
	public void login_usuario_inexistente() {
		try {
			Empresa.getInstance().login("dasdasd",this.password);
			fail("logea con un nombre de usuario inexistente en el hashmap de clientes");
		} catch (UsuarioNoExisteException e) {
			//esta ok
			//e.printStackTrace();
		} catch(PasswordErroneaException e) {
			fail("password erronea cuando en realidad esta mal el nombre de usuario");
		}
	}
	
	@Test
	public void login_password_incorrecta() {
		try {
			Empresa.getInstance().login(this.nombre_usuario,"98321903");
			fail("logea con un nombre de usuario inexistente en el hashmap de clientes");
		} catch (UsuarioNoExisteException e) {
			fail("usuario incorrecto cuando en realidad esta mal la password");
		} catch(PasswordErroneaException e) {
			//esta ok
			//e.printStackTrace();
		}
	}
	
	@Test
	public void login_usuario_ya_logeado() { // se puede logear varias veces sin hacer logout ?
		try {
			Empresa.getInstance().login(this.nombre_usuario,this.password);
			Empresa.getInstance().login(this.nombre_usuario,this.password);
			fail("logea a un usuario ya logeado sin antes haber hecho logout");
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {
			fail("no logea correctamente al usuario");
			//e.printStackTrace();
		}
	}
	

}
