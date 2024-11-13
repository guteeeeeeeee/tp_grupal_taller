package controlador.calificarPagar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.SinViajesException;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import vista.IVista;
import util.Constantes;
import util.Mensajes;
import vista.Ventana;

public class CalificarPagar_no_viaje {

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
		Pedido pedido = new Pedido(user_logeado,1,false,false,5,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido);
		
		this.controlador = new Controlador();
		int calificacion = 3;
		IVista vista_mock = mock(Ventana.class);
		when(vista_mock.getCalificacion()).thenReturn(calificacion);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_calificacion_3_no_esta_en_viaje() {
		assertNull(Empresa.getInstance().getViajeDeCliente(this.user_logeado));
		this.controlador.calificarPagar();
		assertEquals("no salta error que dice que el cliente no tiene viajes pendientes",Mensajes.CLIENTE_SIN_VIAJE_PENDIENTE.getValor(),this.op.getMensaje());
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
