package modelo_Negocios.Empresa.agregarVehiculo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloNegocio.Empresa;
import modeloDatos.Vehiculo;

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
	
	@After
	public void limpiar() {
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
	}

}
