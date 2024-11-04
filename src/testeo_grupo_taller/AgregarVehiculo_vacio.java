package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class AgregarVehiculo_vacio {
	Vehiculo vehiculo;
	String patente;
	
	@Before
	public void setUp() throws Exception {
		this.patente = "a";
		this.vehiculo = new Auto(this.patente,4,true);
	}
	
	@Test
	public void test_agregar_vehiculo() {
		try {
			Empresa.getInstance().agregarVehiculo(this.vehiculo);
			assertEquals(1,Empresa.getInstance().getVehiculos().size());
			assertEquals(this.vehiculo,Empresa.getInstance().getVehiculos().get(this.patente));
		} catch (VehiculoRepetidoException e) {
			fail("dice que esta repetido cuando el hashmap esta vacio");
		}
	}

}
