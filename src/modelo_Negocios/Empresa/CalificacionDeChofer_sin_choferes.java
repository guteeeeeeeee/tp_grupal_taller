package modelo_Negocios.Empresa;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.SinViajesException;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Moto;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class CalificacionDeChofer_sin_choferes {

	Chofer chofer;
	
	@Before
	public void setUp() throws Exception {
		this.chofer = new ChoferTemporario("1","a"); //no lo agrega
	}

	@Test
	public void test_calificacion_sin_viajes() {
		try {
			double calificacion = Empresa.getInstance().calificacionDeChofer(this.chofer);
			assertEquals(0.0,calificacion,0.1);
		} catch (SinViajesException e) {
			fail("no hay choferes en la empresa");
		}
	}
}
