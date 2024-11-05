package controlador.actionPerformed;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import testeo_gui.FalsoOptionPane;
import vista.IVista;
import vista.Ventana;

public class ActionPerformed_nuevo_viaje {

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
		
		this.chofer = new ChoferTemporario("12345","chofer");
		Empresa.getInstance().agregarChofer(this.chofer);

		this.moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(this.moto);
		
		this.user = (Cliente) Empresa.getInstance().login("alfa","aaa");
		
		this.pedido = new Pedido(this.user,1,false,false,5,"ZONA_STANDARD");
		Empresa.getInstance().agregarPedido(this.pedido);
		
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(this.vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_registrar() {
		when(this.vista_mock.getChoferDisponibleSeleccionado()).thenReturn(this.chofer); //getChoferSeleccionado
		when(this.vista_mock.getVehiculoDisponibleSeleccionado()).thenReturn(this.moto); //getVehiculoDisponibleSeleccionado
		when(this.vista_mock.getPedidoSeleccionado()).thenReturn(this.pedido); //getPedidoSeleccionado
		
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn("NUEVO_VIAJE");
		this.controlador.actionPerformed(mockEvent);
	}
}
