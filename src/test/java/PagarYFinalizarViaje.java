import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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

public class PagarYFinalizarViaje {
	
	Pedido pedido;
	Vehiculo vehiculo;
	Chofer chofer;
	Cliente cliente_logeado;
	String nombre_usuario;
	String password;
	String nombre_real;
	
	@BeforeClass
	public static void setea() {
		try {
			Empresa.getInstance().agregarCliente("jorge123","123","jorge fernandez");
		} catch (UsuarioYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Before
	public void setUp() throws Exception {
		this.cliente_logeado = (Cliente) Empresa.getInstance().login("jorge123","123");
	}
	
	@Test
	public void test_finaliza_viaje() {
		try {
			HashMap<Cliente,Viaje> viajes_iniciados = new HashMap<Cliente,Viaje>();
			Pedido pedido_test = new Pedido(this.cliente_logeado,2,false,true,5,"ZONA_STANDARD");
			Chofer chofer = new ChoferPermanente("213213","marcelo hanson",2020,4);
			Empresa.getInstance().agregarChofer(chofer);
			Vehiculo auto = new Auto("pda123",3,true);
			Empresa.getInstance().agregarVehiculo(auto);
			assertEquals(1,Empresa.getInstance().getVehiculos().size());
			assertEquals(auto,Empresa.getInstance().getVehiculos().get(auto.getPatente()));
			assertEquals(1,Empresa.getInstance().getChoferes().size());
			assertEquals(chofer,Empresa.getInstance().getChoferes().get(chofer.getDni()));
			Viaje viaje = new Viaje(pedido_test,chofer,auto);
			HashMap<Cliente,Pedido> pedidos_test = new HashMap<Cliente,Pedido>();
			pedidos_test.put(cliente_logeado, pedido_test);
			Empresa.getInstance().setPedidos(pedidos_test);
			Empresa.getInstance().crearViaje(pedido_test, chofer, auto);
			assertEquals(0,Empresa.getInstance().getVehiculosDesocupados().size());
			assertEquals(0,Empresa.getInstance().getChoferesDesocupados().size());
			viajes_iniciados.put(this.cliente_logeado, viaje);
			Empresa.getInstance().setViajesIniciados(viajes_iniciados);
			try {
				Empresa.getInstance().pagarYFinalizarViaje(4);
				//debe liberar chofer y vehiculo
				assertEquals(1,Empresa.getInstance().getChoferesDesocupados().size());
				assertEquals(1,Empresa.getInstance().getVehiculosDesocupados().size());
				ArrayList<Viaje> viajes_chofer = Empresa.getInstance().getHistorialViajeChofer(chofer);
				ArrayList<Viaje> viajes_cliente = Empresa.getInstance().getHistorialViajeCliente(cliente_logeado);
				//viajes del chofer
				assertEquals(4,viajes_chofer.getLast().getCalificacion());
				assertEquals(chofer,viajes_chofer.getLast().getChofer());
				assertEquals(auto,viajes_chofer.getLast().getVehiculo());
				//viajes del cliente
				assertEquals(4,viajes_cliente.getLast().getCalificacion());
				assertEquals(chofer,viajes_cliente.getLast().getChofer());
				assertEquals(auto,viajes_cliente.getLast().getVehiculo());
				//comprobar valor del viaje
				assertEquals(1000.,viajes_chofer.getLast().getValor(),1.);
				assertEquals(2850,viajes_chofer.getLast().getValor(),1.); //!! esta mal calculado ?
			} catch (ClienteSinViajePendienteException e) {
				fail("cliente no esta en viaje cuando si lo esta");
			}
		} catch (ChoferRepetidoException | VehiculoRepetidoException | PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException | VehiculoNoValidoException | ClienteConViajePendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_finaliza_sin_esta_en_viaje() {
		try {
			Empresa.getInstance().pagarYFinalizarViaje(4);
			fail("finaliza viaje cuando el cliente no esta viajando");
		} catch (ClienteSinViajePendienteException e) {
			//esta ok
		}
	}
	
	@After
	public void limpia() {
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().logout();
	}
}
