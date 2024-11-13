package ejecutar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import testeo_persistencia.PersistenciaTest;
import testeo_persistencia.empresaDTOTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	PersistenciaTest.class, 
	empresaDTOTest.class
})
public class ejecuta_tests_persistencia {

}
