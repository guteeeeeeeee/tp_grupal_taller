package ejecutar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	testeo_gui.vistaAdmin.crearViaje.CrearViaje.class,
	testeo_gui.vistaAdmin.registrarChofer.RegistrarChofer.class,
	testeo_gui.vistaAdmin.registrarChofer.RegistrarChofer_repetido.class,
	testeo_gui.vistaAdmin.registrarVehiculo.RegistrarVehiculo.class,
	testeo_gui.vistaAdmin.registrarVehiculo.RegistrarVehiculo_repetidos.class,
	testeo_gui.vistaAdmin.visualizarInfoAdmin.GestionPedidos_choferes_con_choferes.class,
	testeo_gui.vistaAdmin.visualizarInfoAdmin.GestionPedidos_choferes_sin_choferes.class,
	testeo_gui.vistaAdmin.visualizarInfoAdmin.GestionPedidos_pedidos_pendientes_no_vacia.class,
	testeo_gui.vistaAdmin.visualizarInfoAdmin.GestionPedidos_pedidos_pendientes_vacia.class,
	testeo_gui.vistaAdmin.visualizarInfoAdmin.VisualizarInfoAdmin.class
})
public class ejecuta_tests_panel_administrador {

}
