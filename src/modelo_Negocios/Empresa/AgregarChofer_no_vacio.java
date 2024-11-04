package modelo_Negocios.Empresa;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloNegocio.Empresa;

public class AgregarChofer_no_vacio {
	Chofer chofer;
	String dni;
	
	@Before
	public void setUp() throws Exception {
		this.dni = "1";
		this.chofer = new ChoferPermanente(this.dni,"b",2020,4);
		Empresa.getInstance().agregarChofer(chofer);
	}

	@Test
	public void test_agregar_chofer_repetido() {
		Chofer chofer_repetido = new ChoferPermanente(this.dni,"a",2019,2); //dni repetido
		try {
			Empresa.getInstance().agregarChofer(chofer_repetido);
			fail("no tira la exception de chofer repetido");
			//assertEquals(Empresa.getInstance().getChoferes().size(),1); 
			//assertEquals(this.chofer,Empresa.getInstance().getChoferes().get(this.dni)); //sobrescribe el nuevo chofer por el viejo (esta mal)
		} catch (ChoferRepetidoException e) {
			//esta ok
		}
	}
	
	@Test
	public void test_agregar_chofer() {
		String dni_nuevo = "2";
		ChoferPermanente chofer_agregar = new ChoferPermanente(dni_nuevo,"a",2020,4);
		try {
			Empresa.getInstance().agregarChofer(chofer_agregar);
			assertEquals(2,Empresa.getInstance().getChoferes().size());
			assertEquals(chofer_agregar,Empresa.getInstance().getChoferes().get(dni_nuevo));
		} catch (ChoferRepetidoException e) {
			fail("no deja agregar a chofer y no tiene repetido el dni");
		}
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getChoferes().clear();
	}

}
