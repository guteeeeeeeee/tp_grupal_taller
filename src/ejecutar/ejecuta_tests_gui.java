package ejecutar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({	
	testeo_gui.panelCliente.CalificarViaje.class,
	testeo_gui.panelCliente.PanelCliente.class,
	testeo_gui.panelCliente.PanelCliente_con_pedido_esperando.class,
	testeo_gui.panelCliente.PanelCliente_en_viaje.class,
	testeo_gui.panelLogin.PanelLogin.class,
	testeo_gui.panelRegistro.PanelRegistro.class,
	testeo_gui.panelCliente.CrearPedido_sin_vehiculo_disponible.class,
	testeo_gui.panelCliente.CrearPedido_vehiculo_disponible.class,
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

public class ejecuta_tests_gui {

}
