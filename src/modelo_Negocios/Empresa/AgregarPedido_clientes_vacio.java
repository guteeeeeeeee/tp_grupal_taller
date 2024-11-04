package modelo_Negocios.Empresa;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PasswordErroneaException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloNegocio.Empresa;
import modeloDatos.Vehiculo;
import modeloDatos.Auto;
import modeloDatos.ChoferPermanente;
import modeloDatos.Viaje;
import modeloDatos.Chofer;

public class AgregarPedido_clientes_vacio {
	
	@Before
	public void setUp() throws Exception {
		Vehiculo auto = new Auto("pda134",4,true);
		try { //agrego el vehiculo disponible
			Empresa.getInstance().agregarVehiculo(auto);
		} catch (VehiculoRepetidoException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_clientes_vacio() {
		Cliente cliente_nuevo = new Cliente("a","test","user test");
		Pedido pedido_nuevo = new Pedido(cliente_nuevo,3,true,true,10,"ZONA_STANDARD");
		try { //agrego el pedido
			Empresa.getInstance().agregarPedido(pedido_nuevo);
		} catch (SinVehiculoParaPedidoException e) {
			fail("exception sin vehiculo disponible");
		} catch (ClienteNoExisteException e) {
			//esta ok
		} catch (ClienteConViajePendienteException e) {
			fail("exception el cliente tiene un viaje pendiente");
		} catch (ClienteConPedidoPendienteException e) {
			fail("exception el cliente tiene un pedido pendiente");
		}
	}

}
