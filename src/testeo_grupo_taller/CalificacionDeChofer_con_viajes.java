package testeo_grupo_taller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import excepciones.ClienteSinViajePendienteException;
import excepciones.PasswordErroneaException;
import excepciones.SinViajesException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Chofer;
import modeloDatos.Viaje;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;

public class CalificacionDeChofer {

	Chofer chofer;
	Vehiculo vehiculo;
	
	@BeforeClass
	public static void seteo() throws Exception{
		Empresa.getInstance().agregarCliente("bkk", "12345", "burian");
	}
	
	@Before
	public void setUp() throws Exception {
		this.chofer = new ChoferTemporario("3213213","medina");
		Empresa.getInstance().agregarChofer(this.chofer);
		this.vehiculo = new Moto("123123");
		Empresa.getInstance().agregarVehiculo(this.vehiculo);
	}

	@Test
	public void test_calificacion_sin_viajes() {
		try {
			double calificacion = Empresa.getInstance().calificacionDeChofer(this.chofer);
			assertEquals(calificacion,0.0,0.1);
			fail("devuelve calificacion cuando el chofer no tuvo viajes");
		} catch (SinViajesException e) {
			//esta ok
		}
	}
	@Test
	public void test_calificacion_un_solo_viaje_0() {
		int calificacion = 0;
		try {
			crea_y_puntua_viaje(calificacion);
			double promedio_calificacion = Empresa.getInstance().calificacionDeChofer(this.chofer);
			assertEquals(calificacion,promedio_calificacion,0.1);
		} catch (UsuarioNoExisteException | PasswordErroneaException | ClienteSinViajePendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SinViajesException e) {
			fail("dice que no tiene viajes");
		}
	}
	@Test
	public void test_calificacion_un_solo_viaje_5() {
		int calificacion = 5;
		try {
			crea_y_puntua_viaje(calificacion);
			double promedio_calificacion = Empresa.getInstance().calificacionDeChofer(this.chofer);
			assertEquals(calificacion,promedio_calificacion,0.1);
		} catch (UsuarioNoExisteException | PasswordErroneaException | ClienteSinViajePendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SinViajesException e) {
			fail("dice que no tiene viajes");
		}
	}
	
	@Test
	public void test_calificacion_varios_viajes() {
		int calificacion1,calificacion2,calificacion3;
		double promedio;
		calificacion1 = 3;
		calificacion2 = 0;
		calificacion3 = 5;
		promedio = ((double)calificacion1 + (double)calificacion2 + (double)calificacion3) / 3.;
		try {
			crea_y_puntua_viaje(calificacion1);
			crea_y_puntua_viaje(calificacion2);
			crea_y_puntua_viaje(calificacion3);
			double promedio_calificacion = Empresa.getInstance().calificacionDeChofer(this.chofer);
			assertEquals(promedio,promedio_calificacion,0.1); //mal calculado
		} catch (UsuarioNoExisteException | PasswordErroneaException | ClienteSinViajePendienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SinViajesException e) {
			fail("dice que no tiene viajes");
		}
	}
	
	public void crea_y_puntua_viaje(int calificacion) throws UsuarioNoExisteException, PasswordErroneaException, ClienteSinViajePendienteException {
		HashMap<Cliente,Viaje> viajes_iniciados = new HashMap<Cliente,Viaje>();
		Cliente cliente = (Cliente)Empresa.getInstance().login("bkk", "12345");
		Pedido pedido = new Pedido(cliente,1,false,false,10,"ZONA_SIN_ASFALTAR");
		Viaje viaje_chofer = new Viaje(pedido,this.chofer,this.vehiculo);
		viajes_iniciados.put(cliente, viaje_chofer);
		Empresa.getInstance().setViajesIniciados(viajes_iniciados);
		Empresa.getInstance().pagarYFinalizarViaje(calificacion);
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
	}
}
