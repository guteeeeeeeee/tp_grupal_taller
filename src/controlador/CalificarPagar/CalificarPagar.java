package controlador.CalificarPagar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.SinViajesException;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.*;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;
import testeo_gui.FalsoOptionPane;
import vista.IVista;
import vista.Ventana;

public class CalificarPagar {

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
		Pedido pedido = new Pedido(user_logeado,1,false,false,5,"ZONA_STANDARD");
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
		
		assertEquals(0,Empresa.getInstance().calificacionDeChofer(this.chofer),0.1);
		this.controlador.calificarPagar();
		assertEquals(calificacion,Empresa.getInstance().calificacionDeChofer(this.chofer),0.1);
	}
	
	@Test
	public void test_calificacion_5() throws SinViajesException {
		int calificacion = 5;
		IVista vista_mock = mock(Ventana.class);
		when(vista_mock.getCalificacion()).thenReturn(calificacion);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
		
		assertEquals(0,Empresa.getInstance().calificacionDeChofer(this.chofer),0.1);
		this.controlador.calificarPagar();
		assertEquals(calificacion,Empresa.getInstance().calificacionDeChofer(this.chofer),0.1);
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getViajesTerminados().clear();
	}

}
