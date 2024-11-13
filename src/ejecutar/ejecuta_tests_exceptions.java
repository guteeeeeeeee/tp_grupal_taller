package ejecutar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	excepciones.Exception_chofer_no_disponible.class,
	excepciones.Exception_chofer_repetido.class,
	excepciones.Exception_cliente_con_pedido_pendiente.class,
	excepciones.Exception_cliente_no_existe.class,
	excepciones.Exception_cliente_sin_viaje_pendiente.class,
	excepciones.Exception_cliente_viaje_pendiente.class,
	excepciones.Exception_password_erronea.class,
	excepciones.Exception_pedido_inexistente.class,
	excepciones.Exception_sin_vehiculo_para_pedido.class,
	excepciones.Exception_usuario_no_existe.class,
	excepciones.Exception_usuario_repetido.class,
	excepciones.Exception_vehiculo_no_disponible.class,
	excepciones.Exception_vehiculo_no_valido.class,
	excepciones.Exception_vehiculo_repetido.class
})
public class ejecuta_tests_exceptions {

}
