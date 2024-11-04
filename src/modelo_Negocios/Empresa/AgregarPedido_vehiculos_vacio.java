package modelo_Negocios.Empresa;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PasswordErroneaException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class AgregarPedido_vehiculos_vacio {
	Pedido pedido;
	Cliente cliente_logeado;
	
	@Before
	public void setUp() throws Exception {
		String nombre_usuario = "a";
		String password = "test";
		String nombre_real = "user test";
		try {
			Empresa.getInstance().agregarCliente(nombre_usuario,password,nombre_real);
		} catch (UsuarioYaExisteException e) {
			e.printStackTrace();
		}
		try { //logea al usuario en el sistema
			 this.cliente_logeado = (Cliente) Empresa.getInstance().login(nombre_usuario,password);
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {				
			e.printStackTrace();
		}
		this.pedido = new Pedido(this.cliente_logeado,3,true,true,10,"ZONA_STANDARD");
	}
	
	@Test
	public void test_vehiculos_vacio() {
		Pedido pedido_nuevo = new Pedido(this.cliente_logeado,3,true,true,10,"ZONA_STANDARD");
		try { //agrego el pedido
			Empresa.getInstance().agregarPedido(pedido_nuevo);
		} catch (SinVehiculoParaPedidoException e) {
			//esta ok
		} catch (ClienteNoExisteException e) {
			fail("exception el cliente no esta registrado");
		} catch (ClienteConViajePendienteException e) {
			fail("exception el cliente tiene un viaje pendiente");
		} catch (ClienteConPedidoPendienteException e) {
			fail("exception el cliente tiene un pedido pendiente");
		}
	}

}
