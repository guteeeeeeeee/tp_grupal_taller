package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Moto;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class AgregarVehiculo {
	Vehiculo vehiculo;
	String patente;
	@Before
	public void setUp() throws Exception {
		this.patente = "fpp245";
		this.vehiculo = new Auto(this.patente,3,true);
	}

	@Ignore
	@Test
	public void test_agregar_vehiculo() {
		try {
			Empresa.getInstance().agregarVehiculo(this.vehiculo);
			assertEquals(1,Empresa.getInstance().getVehiculos().size());
			assertEquals(this.vehiculo,Empresa.getInstance().getVehiculos().get(this.patente));
		} catch (VehiculoRepetidoException e) {
			fail("tira repetido cuando no lo esta");
			//e.printStackTrace();
		}
	}
	
	@Test
	public void test_agregar_vehiculo_repetido() {
		Vehiculo vehiculo_repetido = new Moto(this.patente);
		try {
			Empresa.getInstance().agregarVehiculo(this.vehiculo);
		} catch (VehiculoRepetidoException e) {
			fail("tira repetido cuando no lo esta");
			//e.printStackTrace();
		}
		try {
			Empresa.getInstance().agregarVehiculo(vehiculo_repetido);
			fail("agrega un vehiculo con patente repetida");
			assertEquals(1,Empresa.getInstance().getVehiculos().size());
			assertEquals(this.vehiculo,Empresa.getInstance().getVehiculos().get(this.patente));
		} catch (VehiculoRepetidoException e) {
			//esta ok
			//e.printStackTrace();
		}
	}

}
