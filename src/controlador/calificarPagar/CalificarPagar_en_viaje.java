package controlador.calificarPagar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.SinViajesException;
import modeloDatos.*;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import vista.IVista;
import util.Constantes;
import util.Mensajes;
import vista.Ventana;

public class CalificarPagar_en_viaje {

	Controlador controlador;
	Chofer chofer;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("alfa", "aaa", "alfonso");
		Cliente user_logeado = (Cliente) Empresa.getInstance().login("alfa","aaa");
		this.chofer = new ChoferTemporario("12345","chofer");
		Empresa.getInstance().agregarChofer(chofer);
		Vehiculo moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		Pedido pedido = new Pedido(user_logeado,1,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido);
		
		Empresa.getInstance().crearViaje(pedido, chofer, moto);
		
		this.controlador = new Controlador();
	}

	@Test
	public void test_calificacion_0() throws SinViajesException {
		int calificacion = 0;
		IVista vista_mock = mock(Ventana.class);
		when(vista_mock.getCalificacion()).thenReturn(calificacion);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
		
		this.controlador.calificarPagar();
		assertEquals(calificacion,Empresa.getInstance().calificacionDeChofer(this.chofer),0.1);
		verify(vista_mock).actualizar();
	}
	
	@Test
	public void test_calificacion_5() throws SinViajesException {
		int calificacion = 5;
		IVista vista_mock = mock(Ventana.class);
		when(vista_mock.getCalificacion()).thenReturn(calificacion);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
		
		this.controlador.calificarPagar();
		assertEquals(calificacion,Empresa.getInstance().calificacionDeChofer(this.chofer),0.1);
		verify(vista_mock).actualizar();
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getViajesTerminados().clear();
	}

}
