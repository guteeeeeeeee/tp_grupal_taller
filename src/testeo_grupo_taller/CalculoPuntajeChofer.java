package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloNegocio.Empresa;

public class CalculoPuntajeChofer {

	Chofer chofer;
	@Before
	public void setUp() throws Exception {
		this.chofer = new ChoferTemporario("321321","raul armando");
	}

	@Test
	public void test_un_solo_puntaje() {
		//sadasd
	}

}
