package ejecutar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({	
	integracion.admin_login_y_agrega_chofer.admin_login_y_agrega_chofer.class,
	integracion.admin_login_y_agrega_vehiculo.admin_login_y_agrega_vehiculo.class,
	integracion.admin_login_y_crea_viaje.admin_login_y_crea_viaje.class,
	integracion.login_crea_pedido_y_termina_viaje.login_crea_pedido_y_termina_viaje.class,
	integracion.login_y_crea_pedido.login_y_crea_pedido.class,
	integracion.registro_y_login.registro_y_login.class,
	integracion.registro_y_login.registro_y_login_erroneo.class
})
public class ejecuta_tests_integracion {

}
