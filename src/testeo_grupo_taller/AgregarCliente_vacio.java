package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;

public class AgregarCliente_vacio {
	
	String nombre_usuario = "a";
	String password = "b";
	String nombre_completo = "c";
	
	@Before
	public void setUp() throws Exception {	
	}

	@Test
	public void test_agregar_cliente_vacio() {
		try {
			Empresa.getInstance().agregarCliente(this.nombre_usuario,this.password,this.nombre_completo);
			assertEquals(1,Empresa.getInstance().getClientes().size());
			assertEquals(this.nombre_usuario,Empresa.getInstance().getClientes().get(this.nombre_usuario).getNombreUsuario());
			assertEquals(this.password,Empresa.getInstance().getClientes().get(this.nombre_usuario).getPass());
		} catch (UsuarioYaExisteException e) {
			fail("tiro excepcion de que el usuario estaba repetido cuando esta vacio el hashmap de clientes");
		}
	}

}
