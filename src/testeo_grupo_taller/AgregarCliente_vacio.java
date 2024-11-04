package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;

public class AgregarCliente {
	
	String nombre_usuario = "jorge123";
	String password = "123";
	String nombre_completo = "jorge fernandez";
	
	@Before
	public void setUp() throws Exception {	
	}

	@Test
	public void test_agregar_cliente() {
		try {
			Empresa.getInstance().agregarCliente(this.nombre_usuario,this.password,this.nombre_completo);
			assertEquals(1,Empresa.getInstance().getClientes().size());
			assertEquals(this.nombre_usuario,Empresa.getInstance().getClientes().get(this.nombre_usuario).getNombreUsuario());
			assertEquals(this.password,Empresa.getInstance().getClientes().get(this.nombre_usuario).getPass());
		} catch (UsuarioYaExisteException e) {
			fail("tiro excepcion de que el usuario estaba repetido");
			//e.printStackTrace();
		}
	}
	
	@Test
	public void test_agregar_cliente_repetido() { //repite el nombre de usuario
		try {
			Empresa.getInstance().agregarCliente("jorge123", "123", "jorge fernandez");
			Empresa.getInstance().agregarCliente("jorge123", "12345", "fernando coronel");
			fail("agrego un cliente con nombre de usuario repetido");
		} catch (UsuarioYaExisteException e) {
			//todo ok
			assertEquals(1,Empresa.getInstance().getClientes().size());
		}
	}

}
