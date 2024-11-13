package ejecutar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	modelo_Datos.Administrador.GetInstance.class,
	modelo_Datos.Chofer.GetSueldoBruto_chofer_permanente.class,
	modelo_Datos.Chofer.GetSueldoBruto_chofer_temporario.class,
	modelo_Datos.Chofer.Getters_Setters_chofer.class,
	modelo_Datos.Chofer.Getters_Setters_chofer_permanente.class,
	modelo_Datos.Cliente.Getters_cliente.class,
	modelo_Datos.Pedido.Getters_pedido.class,
	modelo_Datos.Usuario.Getters_usuario.class,
	modelo_Datos.Vehiculo.GetPuntajePedido_auto.class,
	modelo_Datos.Vehiculo.GetPuntajePedido_auto_pf.class,
	modelo_Datos.Vehiculo.GetPuntajePedido_combi.class,
	modelo_Datos.Vehiculo.GetPuntajePedido_combi_pf.class,
	modelo_Datos.Vehiculo.GetPuntajePedido_moto.class,
	modelo_Datos.Vehiculo.Getters_vehiculo.class,
	modelo_Datos.Viaje.Getters_viaje.class,
	modelo_Datos.Viaje.GetValor_viaje.class
})

public class ejecuta_tests_modelo_datos {

}
