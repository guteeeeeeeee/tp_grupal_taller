package ejecutar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	controlador.actionPerformed.ActionPerformed.class,
	controlador.actionPerformed.ActionPerformed_logout.class,
	controlador.actionPerformed.ActionPerformed_pagar_finalizar_viaje.class,
	controlador.calificarPagar.CalificarPagar_no_viaje.class,
	controlador.calificarPagar.CalificarPagar_en_viaje.class,
	controlador.escribir.Escribir_existe.class,
	controlador.escribir.Escribir_no_existe.class,
	controlador.getters_setters.Getters_setters.class,
	controlador.leer.Leer_existe.class,
	controlador.leer.Leer_no_existe.class,
	controlador.login.Login.class,
	controlador.logout.Logout.class,
	controlador.nuevoChofer.NuevoChofer_repetido.class,
	controlador.nuevoChofer.NuevoChofer_sin_choferes.class,
	controlador.nuevoPedido.NuevoPedido.class,
	controlador.nuevoPedido.NuevoPedido_en_viaje.class,
	controlador.nuevoVehiculo.NuevoVehiculo_con_auto_cargado.class,
	controlador.nuevoVehiculo.NuevoVehiculo_con_patente_ya_cargada.class,
	controlador.nuevoVehiculo.NuevoVehiculo_sin_vehiculos.class,
	controlador.nuevoViaje.NuevoViaje.class,
	controlador.registrar.Registrar.class,
	controlador.Vista.Vista_getters_setters.class
})

public class ejecuta_tests_controlador {

}
