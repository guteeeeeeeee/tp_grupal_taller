package modelo_Negocios.Empresa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ClienteSinViajePendienteException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;

public class PagarYFinalizarViaje_viaje_finalizado {

	Pedido pedido;
	Vehiculo vehiculo;
	Chofer chofer;
	Cliente cliente_logeado;
	String nombre_usuario;
	String password;
	String nombre_real;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("jorge123","123","jorge fernandez");
		this.cliente_logeado = (Cliente) Empresa.getInstance().login("jorge123","123");
		HashMap<Cliente,Viaje> viajes_iniciados = new HashMap<Cliente,Viaje>();
		this.chofer = new ChoferPermanente("213213","marcelo hanson",2020,4);
		Empresa.getInstance().agregarChofer(this.chofer);
		this.vehiculo = new Auto("pda123",3,true);
		Empresa.getInstance().agregarVehiculo(this.vehiculo);
		Pedido pedido_test = new Pedido(this.cliente_logeado,2,false,true,5,"ZONA_STANDARD");
		Empresa.getInstance().agregarPedido(pedido_test);
		Viaje viaje = new Viaje(pedido_test,this.chofer,this.vehiculo);
		Empresa.getInstance().crearViaje(pedido_test, this.chofer, this.vehiculo);
		Empresa.getInstance().pagarYFinalizarViaje(0);
	}
	
	@Test
	public void test_finalizar_viaje_ya_finalizado() {
		int calificacion = 0;
		try {				
			Empresa.getInstance().pagarYFinalizarViaje(calificacion);
			fail("no debe contiunar, el viaje ya fue finalizado antes");
			} catch (ClienteSinViajePendienteException e) {
				//esta ok
			}
	}
	
	@After
	public void limpia() {
		Empresa.getInstance().getChoferesDesocupados().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getViajesTerminados().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().logout();
	}

}
