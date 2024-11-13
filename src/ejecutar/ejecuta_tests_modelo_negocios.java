package ejecutar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	modelo_Negocios.Empresa.AgregarCliente.AgregarCliente_no_vacio.class,
	modelo_Negocios.Empresa.AgregarCliente.AgregarCliente_vacio.class,
	modelo_Negocios.Empresa.AgregarChofer.AgregarChofer_no_vacio.class,
	modelo_Negocios.Empresa.AgregarChofer.AgregarChofer_vacio.class,
	modelo_Negocios.Empresa.agregarPedido.AgregarPedido_cliente_en_viaje.class,
	modelo_Negocios.Empresa.agregarPedido.AgregarPedido_no_viajes.class,
	modelo_Negocios.Empresa.agregarVehiculo.AgregarVehiculo_no_vacio.class,
	modelo_Negocios.Empresa.agregarVehiculo.AgregarVehiculo_vacio.class,
	modelo_Negocios.Empresa.CalificacionDeChofer.CalificacionDeChofer_con_viajes.class,
	modelo_Negocios.Empresa.CalificacionDeChofer.CalificacionDeChofer_sin_choferes.class,
	modelo_Negocios.Empresa.CrearViaje.CrearViaje_con_todo.class,
	modelo_Negocios.Empresa.CrearViaje.CrearViaje_sin_choferes.class,
	modelo_Negocios.Empresa.CrearViaje.CrearViaje_sin_pedidos.class,
	modelo_Negocios.Empresa.Getters_Setters.Empresa_getHistorialViajeChofer.class,
	modelo_Negocios.Empresa.Getters_Setters.Empresa_getHistorialViajeCliente.class,
	modelo_Negocios.Empresa.Getters_Setters.Empresa_getters_setters.class,
	modelo_Negocios.Empresa.Getters_Setters.Empresa_getTotalSalarios.class,
	modelo_Negocios.Empresa.Login.Login_no_vacio.class,
	modelo_Negocios.Empresa.Login.Login_vacio.class,
	modelo_Negocios.Empresa.Logout.Logout_admin_logeado.class,
	modelo_Negocios.Empresa.Logout.Logout_con_usuario_logeado.class,
	modelo_Negocios.Empresa.Logout.Logout_con_usuario_no_logeado.class,
	modelo_Negocios.Empresa.Logout.Logout_sin_usuarios.class,
	modelo_Negocios.Empresa.PagarYFinalizarViaje.PagarYFinalizarViaje_en_viaje.class,
	modelo_Negocios.Empresa.PagarYFinalizarViaje.PagarYFinalizarViaje_sin_viajes.class,
	modelo_Negocios.Empresa.PagarYFinalizarViaje.PagarYFinalizarViaje_viaje_finalizado.class,
	modelo_Negocios.Empresa.ValidarPedido.ValidarPedido_auto.class,
	modelo_Negocios.Empresa.ValidarPedido.ValidarPedido_auto_pet_friendly.class,
	modelo_Negocios.Empresa.ValidarPedido.ValidarPedido_combi.class,
	modelo_Negocios.Empresa.ValidarPedido.ValidarPedido_combi_pet_friendly.class,
	modelo_Negocios.Empresa.ValidarPedido.ValidarPedido_moto.class,
	modelo_Negocios.Empresa.ValidarPedido.ValidarPedido_sin_vehiculos.class,
	modelo_Negocios.Empresa.vehiculosOrdenadosPorPedido.VehiculosOrdenados_con_moto.class,
	modelo_Negocios.Empresa.vehiculosOrdenadosPorPedido.VehiculosOrdenados_con_moto_auto_y_combi.class,
	modelo_Negocios.Empresa.vehiculosOrdenadosPorPedido.VehiculosOrdenados_con_moto_y_auto.class,
	modelo_Negocios.Empresa.vehiculosOrdenadosPorPedido.VehiculosOrdenados_sin_vehiculos.class
})

public class ejecuta_tests_modelo_negocios {

}
