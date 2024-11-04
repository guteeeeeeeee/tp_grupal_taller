package modeloDatos.Usuario;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import modeloNegocio.Empresa;

public class Getters_usuario {

	Usuario user;
	String nombre_usuario;
	String password;
	Administrador admin;
	
	@Before
	public void setUp() throws Exception {
		this.nombre_usuario = "n";
		this.password = "c";
		this.user = new Cliente(this.nombre_usuario,this.password,"usuario tipo cliente");
		this.admin = Administrador.getInstance();
	}

	@Test
	public void test_get_nombre_usuario_cliente() {
		assertEquals(this.nombre_usuario,this.user.getNombreUsuario());
		assertEquals(this.password,this.user.getPass());
	}
	
	@Test
	public void test_get_nombre_usuario_admin() {
		assertEquals("admin",this.admin.getNombreUsuario());
		assertEquals("admin",this.admin.getPass());
	}

}
