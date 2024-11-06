package modelo_Negocios.Empresa.AgregarChofer;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import modeloNegocio.Empresa;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;

public class AgregarChofer_vacio {
	Chofer chofer;
	String dni;
	
	@Before
	public void setUp() throws Exception {
		this.dni = "1";
		this.chofer = new ChoferPermanente(this.dni,"a",2020,4);
	}

	@Test
	public void test_agregar_chofer_sin_choferes() {
		try {
			assertEquals(0,Empresa.getInstance().getChoferes().size());
			Empresa.getInstance().agregarChofer(this.chofer);
			assertEquals(1,Empresa.getInstance().getChoferes().size());
			assertEquals(this.chofer,Empresa.getInstance().getChoferes().get(this.dni));
		} catch (ChoferRepetidoException e) {
			fail("dice que el chofer esta repetido cuando no es asi");
		}
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getChoferes().clear();
	}
}
