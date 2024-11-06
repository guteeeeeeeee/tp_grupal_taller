package controlador.actionPerformed;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
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

public class ActionPerformed_logout {

	Controlador controlador;
	IVista vista_mock;
	Chofer chofer;
	Vehiculo moto;
	Pedido pedido;
	Cliente user;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.vista_mock = mock(Ventana.class);
		Empresa.getInstance().agregarCliente("alfa", "aaa", "alfonso");
		
		this.user = (Cliente) Empresa.getInstance().login("alfa","aaa");
		
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(this.vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_logout_cliente() { //fijarse
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn("CERRAR_SESION_CLIENTE");
		this.controlador.actionPerformed(mockEvent);
	}
	
	@Test
	public void test_logout_admin() { //fijarse
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn("CERRAR_SESION_ADMIN");
		this.controlador.actionPerformed(mockEvent);
	}

}
