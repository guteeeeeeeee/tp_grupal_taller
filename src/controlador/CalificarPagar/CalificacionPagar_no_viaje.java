package controlador.CalificarPagar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.SinViajesException;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import testeo_gui.FalsoOptionPane;
import vista.IVista;
import vista.Ventana;

public class CalificacionPagar_no_viaje {

	Controlador controlador;
	Chofer chofer;
	Cliente user_logeado;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		Empresa.getInstance().agregarCliente("alfa", "aaa", "alfonso");
		this.user_logeado = (Cliente) Empresa.getInstance().login("alfa","aaa");
		this.chofer = new ChoferTemporario("12345","chofer");
		Empresa.getInstance().agregarChofer(chofer);
		Vehiculo moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		Pedido pedido = new Pedido(user_logeado,1,false,false,5,"ZONA_STANDARD");
		Empresa.getInstance().agregarPedido(pedido);
		
		Empresa.getInstance().crearViaje(pedido, chofer, moto);
		
		this.controlador = new Controlador();
		int calificacion = 3;
		IVista vista_mock = mock(Ventana.class);
		when(vista_mock.getCalificacion()).thenReturn(calificacion);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_calificacion_3_en_viaje() throws SinViajesException {
		assertNotNull(Empresa.getInstance().getViajeDeCliente(this.user_logeado));
		this.controlador.calificarPagar();
		assertEquals("Cliente Sin Viaje Pendiente",this.op.getMensaje());
	}

}
