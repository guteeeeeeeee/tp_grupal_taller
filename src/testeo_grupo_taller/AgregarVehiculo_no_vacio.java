package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Moto;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class AgregarVehiculo_no_vacio {
	Vehiculo vehiculo;
	String patente;
	
	@Before
	public void setUp() throws Exception {
		this.patente = "a";
		this.vehiculo = new Auto(this.patente,4,true);
		Empresa.getInstance().agregarVehiculo(this.vehiculo);
	}

	@Test
	public void test_agregar_vehiculo() {
		String patente_nuevo = "b";
		Vehiculo vehiculo_nuevo = new Auto(patente_nuevo,4,true);
		try {
			Empresa.getInstance().agregarVehiculo(vehiculo_nuevo);
			assertEquals(2,Empresa.getInstance().getVehiculos().size());
			assertEquals(vehiculo_nuevo,Empresa.getInstance().getVehiculos().get(patente_nuevo));
		} catch (VehiculoRepetidoException e) {
			fail("tira repetido cuando la patente es distinta de la que ya esta agregada");
		}
	}
	
	@Test
	public void test_agregar_vehiculo_repetido() {
		Vehiculo vehiculo_nuevo = new Auto(this.patente,4,true);
		try {
			Empresa.getInstance().agregarVehiculo(vehiculo_nuevo);
			fail("tendria que tirar la excepcion que el vehiculo esta repetido");
		} catch (VehiculoRepetidoException e) {
			//esta ok
		}
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().getVehiculos().clear();
	}

}
