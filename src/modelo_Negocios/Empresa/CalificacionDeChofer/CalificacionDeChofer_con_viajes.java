package modelo_Negocios.Empresa.CalificacionDeChofer;

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

public class CalificacionDeChofer_con_viajes {

	Chofer chofer_sin_viajes;
	Chofer chofer_un_viaje;
	Chofer chofer_tres_viajes;
	Vehiculo vehiculo;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("a", "b", "c");
		this.chofer_sin_viajes = new ChoferTemporario("1","medina");
		Empresa.getInstance().agregarChofer(this.chofer_sin_viajes);
		this.chofer_un_viaje = new ChoferTemporario("2","carlos");
		Empresa.getInstance().agregarChofer(this.chofer_un_viaje);
		this.chofer_tres_viajes = new ChoferTemporario("3","raul");
		Empresa.getInstance().agregarChofer(this.chofer_tres_viajes);
		this.vehiculo = new Moto("123");
		Empresa.getInstance().agregarVehiculo(this.vehiculo);
		crea_y_puntua_viaje(this.chofer_un_viaje,1);
		crea_y_puntua_viaje(this.chofer_tres_viajes,1);
		crea_y_puntua_viaje(this.chofer_tres_viajes,3);
		crea_y_puntua_viaje(this.chofer_tres_viajes,5);
	}

	@Test
	public void test_calificacion_sin_viajes() {
		try {
			double calificacion = Empresa.getInstance().calificacionDeChofer(this.chofer_sin_viajes);
			assertEquals(calificacion,0.0,0.1);
			fail("devuelve calificacion cuando el chofer no tuvo viajes");
		} catch (SinViajesException e) {
			//esta ok
		}
	}
	
	@Test
	public void test_calificacion_un_solo_viaje() {
		try {
			double promedio_calificacion = Empresa.getInstance().calificacionDeChofer(this.chofer_un_viaje);
			assertEquals(promedio_calificacion,1.0,0.1);
		} catch (SinViajesException e) {
			fail("dice que no tiene viajes");
		}
	}
	
	@Test
	public void test_calificacion_tres_viajes() {
		try {
			double promedio_calificacion = Empresa.getInstance().calificacionDeChofer(this.chofer_tres_viajes);
			assertEquals(promedio_calificacion,3.0,0.1);
		} catch (SinViajesException e) {
			fail("dice que no tiene viajes");
		}
	}
	
	@Test
	public void test_calificacion_chofer_no_registrado() {
		try {
			Chofer chofer_nuevo = new ChoferTemporario("4","b");
			double promedio_calificacion = Empresa.getInstance().calificacionDeChofer(chofer_nuevo);
			assertEquals(promedio_calificacion,0.0,0.1);
		} catch (SinViajesException e) {
			fail("dice que no tiene viajes");
		}
	}
	
	public void crea_y_puntua_viaje(Chofer chofer,int calificacion) throws UsuarioNoExisteException, PasswordErroneaException, ClienteSinViajePendienteException {
		HashMap<Cliente,Viaje> viajes_iniciados = new HashMap<Cliente,Viaje>();
		Cliente cliente = (Cliente) Empresa.getInstance().login("a", "b");
		Pedido pedido = new Pedido(cliente,1,false,false,10,"ZONA_SIN_ASFALTAR");
		Viaje viaje_chofer = new Viaje(pedido,chofer,this.vehiculo);
		viajes_iniciados.put(cliente, viaje_chofer);
		Empresa.getInstance().setViajesIniciados(viajes_iniciados);
		Empresa.getInstance().pagarYFinalizarViaje(calificacion);
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
	}
}
