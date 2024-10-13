import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloNegocio.Empresa;

public class AgregarChofer {
	Chofer chofer;
	String dni;
	@Before
	public void setUp() throws Exception {
		this.dni = "321321";
		this.chofer = new ChoferPermanente(this.dni,"marcelo hanson",2020,4);
	}

	@Test
	public void test_agregar_chofer() {
		try {
			Empresa.getInstance().agregarChofer(this.chofer);
			assertEquals(1,Empresa.getInstance().getChoferes().size());
			assertEquals(this.chofer,Empresa.getInstance().getChoferes().get(this.dni));
		} catch (ChoferRepetidoException e) {
			fail("dice que el chofer esta repetido cuando no es asi");
			//e.printStackTrace();
		}
	}
	
	@Test
	public void test_agregar_chofer_repetido() {
		Chofer chofer_repetido = new ChoferPermanente(this.dni,"jorge amado",2019,2); //dni repetido
		try {
			Empresa.getInstance().agregarChofer(this.chofer);
			assertEquals(Empresa.getInstance().getChoferes().size(),1);
		} catch (ChoferRepetidoException e) {
			fail("dice que el chofer esta repetido cuando no es asi");
			//e.printStackTrace();
		}
		try {
			Empresa.getInstance().agregarChofer(chofer_repetido);
			fail("no tira la exception de chofer repetido");
			assertEquals(Empresa.getInstance().getChoferes().size(),1); 
			assertEquals(this.chofer,Empresa.getInstance().getChoferes().get(this.dni)); //sobrescribe el nuevo chofer por el viejo (esta mal)
		} catch (ChoferRepetidoException e) {
			//esta ok
			//e.printStackTrace();
		}
	}
	
	@Test
	public void test_agregar_chofer_ingreso_futuro() {
		ChoferPermanente chofer_agregar = new ChoferPermanente(this.dni,"marcelo hanson",2048,4);
		try {
			Empresa.getInstance().agregarChofer(chofer_agregar);
			assertEquals(1,Empresa.getInstance().getChoferes().size());
			assertEquals(chofer_agregar,Empresa.getInstance().getChoferes().get(this.dni));
			assertTrue(chofer_agregar.getAntiguedad() > 0.); //antiguedad negativa
			fail("agrega chofer con anio de ingreso posterior al actual");
		} catch (ChoferRepetidoException e) {
			//e.printStackTrace();
		}
	}
	
}
