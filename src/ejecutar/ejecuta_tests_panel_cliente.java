package ejecutar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	testeo_gui.panelCliente.PanelCliente.class,
	testeo_gui.panelCliente.PanelCliente_con_pedido_esperando.class,
	testeo_gui.panelCliente.PanelCliente_en_viaje.class,
	testeo_gui.panelCliente.CrearPedido_sin_vehiculo_disponible.class,
	testeo_gui.panelCliente.CrearPedido_vehiculo_disponible.class
})
public class ejecuta_tests_panel_cliente {

}
