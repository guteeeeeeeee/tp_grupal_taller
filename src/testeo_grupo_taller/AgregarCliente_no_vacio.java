package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.UsuarioYaExisteException;
import modeloDatos.Cliente;
import modeloNegocio.Empresa;

public class AgregarCliente_no_vacio {
	
	String nombre_usuario = "a";
	String password = "123";
	String nombre_completo = "jorge fernandez";
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente(nombre_usuario, password, nombre_completo);
	}

	@Test
	public void test_agregar_cliente_repetido() {
		try {
			Empresa.getInstance().agregarCliente(this.nombre_usuario, "b", "c"); //repite el nombre de usuario
			fail("agrego un cliente con nombre de usuario repetido");
		} catch (UsuarioYaExisteException e) {
			//todo ok
			assertEquals(1,Empresa.getInstance().getClientes().size());
		}
	}
	
	@Test
	public void test_agregar_cliente() {
		String nombre_usuario = "b";
		String pass = "b";
		String nombre = "carlos";
		try {
			Empresa.getInstance().agregarCliente(nombre_usuario,pass,nombre);
			assertEquals(2,Empresa.getInstance().getClientes().size());
			assertEquals(pass,Empresa.getInstance().getClientes().get(nombre_usuario).getPass());
			assertEquals(nombre,Empresa.getInstance().getClientes().get(nombre_usuario).getNombreReal());
		} catch (UsuarioYaExisteException e) {
			fail("tiro excepcion de que el usuario esta repetido cuando no es asi");
		}
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().getClientes().clear();
	}

}
