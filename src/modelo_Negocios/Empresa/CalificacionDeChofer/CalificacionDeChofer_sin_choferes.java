package modelo_Negocios.Empresa.CalificacionDeChofer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.SinViajesException;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import modeloDatos.*;

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
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
		Empresa.getInstance().getViajesIniciados().clear();
	}
}
