package modeloDatos.Administrador;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class GetInstance {

	@Test
	public void test() {
		Administrador admin1 = Administrador.getInstance();
		Administrador admin2 = Administrador.getInstance();
		assertTrue(admin1.equals(admin2));
	}

}
