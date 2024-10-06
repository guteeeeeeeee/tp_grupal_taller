package testeo_grupo_taller;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.ClienteSinViajePendienteException;
import excepciones.UsuarioYaExisteException;
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
	
	public void crea_viaje_anterior() {
		HashMap<Cliente,Viaje> viajes_iniciados = new HashMap<Cliente,Viaje>();
		Pedido pedido_test = new Pedido(this.cliente_logeado,2,false,true,5,"ZONA_STANDARD");
		Chofer chofer = new ChoferPermanente("213213","marcelo hanson",2020,4);
		Vehiculo auto = new Auto("pda123",3,true);
		Viaje viaje = new Viaje(pedido_test,chofer,auto);
		viajes_iniciados.put(this.cliente_logeado, viaje);
		Empresa.getInstance().setViajesIniciados(viajes_iniciados);
	}
	
	@Test
	public void test_finaliza_viaje() {
		crea_viaje_anterior();
		try {
			Empresa.getInstance().pagarYFinalizarViaje(4);
		} catch (ClienteSinViajePendienteException e) {
			fail("cliente no esta en viaje cuando si lo esta");
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
