package testeo_grupo_taller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteSinViajePendienteException;
import excepciones.PedidoInexistenteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;

public class PagarYFinalizarViaje_en_viaje {
	
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
		/*HashMap<Cliente,Pedido> pedidos_test = new HashMap<Cliente,Pedido>();
		pedidos_test.put(cliente_logeado, pedido_test);
		Empresa.getInstance().setPedidos(pedidos_test);*/
		Empresa.getInstance().crearViaje(pedido_test, this.chofer, this.vehiculo);
	}
	
	@Test
	public void test_finaliza_viaje_calificacion_0() {
		int calificacion = 0;
		try {				
			assertEquals(0,Empresa.getInstance().getChoferesDesocupados().size());
			assertEquals(0,Empresa.getInstance().getVehiculosDesocupados().size());
			Empresa.getInstance().pagarYFinalizarViaje(calificacion);
			//debe liberar chofer y vehiculo
			assertEquals(1,Empresa.getInstance().getChoferesDesocupados().size());
			assertEquals(1,Empresa.getInstance().getVehiculosDesocupados().size());
			ArrayList<Viaje> viajes_chofer = Empresa.getInstance().getHistorialViajeChofer(chofer);
			ArrayList<Viaje> viajes_cliente = Empresa.getInstance().getHistorialViajeCliente(cliente_logeado);
			//viajes del chofer
			assertEquals(calificacion,viajes_chofer.getLast().getCalificacion());
			assertEquals(this.chofer,viajes_chofer.getLast().getChofer());
			assertEquals(this.vehiculo,viajes_chofer.getLast().getVehiculo());
			//viajes del cliente
			assertEquals(calificacion,viajes_cliente.getLast().getCalificacion());
			assertEquals(this.chofer,viajes_cliente.getLast().getChofer());
			assertEquals(this.vehiculo,viajes_cliente.getLast().getVehiculo());
			} catch (ClienteSinViajePendienteException e) {
				fail("cliente no esta en viaje cuando si lo esta");
			}
	}
	
	@Test
	public void test_finaliza_viaje_calificacion_5() {
		int calificacion = 5;
		try {				
			assertEquals(0,Empresa.getInstance().getChoferesDesocupados().size());
			assertEquals(0,Empresa.getInstance().getVehiculosDesocupados().size());
			Empresa.getInstance().pagarYFinalizarViaje(calificacion);
			//debe liberar chofer y vehiculo
			assertEquals(1,Empresa.getInstance().getChoferesDesocupados().size());
			assertEquals(1,Empresa.getInstance().getVehiculosDesocupados().size());
			ArrayList<Viaje> viajes_chofer = Empresa.getInstance().getHistorialViajeChofer(chofer);
			ArrayList<Viaje> viajes_cliente = Empresa.getInstance().getHistorialViajeCliente(cliente_logeado);
			//viajes del chofer
			assertEquals(calificacion,viajes_chofer.getLast().getCalificacion());
			assertEquals(this.chofer,viajes_chofer.getLast().getChofer());
			assertEquals(this.vehiculo,viajes_chofer.getLast().getVehiculo());
			//viajes del cliente
			assertEquals(calificacion,viajes_cliente.getLast().getCalificacion());
			assertEquals(this.chofer,viajes_cliente.getLast().getChofer());
			assertEquals(this.vehiculo,viajes_cliente.getLast().getVehiculo());
			} catch (ClienteSinViajePendienteException e) {
				fail("cliente no esta en viaje cuando si lo esta");
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
