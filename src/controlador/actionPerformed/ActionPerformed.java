package controlador.actionPerformed;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;
import modeloDatos.Cliente;
import testeo_gui.FalsoOptionPane;
import vista.IVista;
import vista.Ventana;

public class ActionPerformed {

	Controlador controlador;
	IVista vista_mock;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
		this.vista_mock = mock(Ventana.class);
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_registrar() {
		String user_name = "jorge123";
		String password = "12345";
		String nombre_real = "jorge alvarez";
		
		when(vista_mock.getRegUsserName()).thenReturn(user_name);
		when(vista_mock.getRegPassword()).thenReturn(password);
		when(vista_mock.getRegConfirmPassword()).thenReturn(password);
		when(vista_mock.getRegNombreReal()).thenReturn(nombre_real);
		
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn("REG_BUTTON_REGISTRAR");
		this.controlador.actionPerformed(mockEvent);
	}
	
	@Test
	public void test_login() {
		String user_name = "pepe123";
		String password = "123";
		
		when(vista_mock.getUsserName()).thenReturn(user_name);
		when(vista_mock.getPassword()).thenReturn(password);
		
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn("LOGIN");
		this.controlador.actionPerformed(mockEvent);
	}
	
	@Test
	public void test_nuevo_pedido() throws UsuarioYaExisteException, UsuarioNoExisteException, PasswordErroneaException {
		int cant_pax = 3;
		boolean mascota = true;
		boolean baul = true;
		int cant_km = 5;
		String tipo_zona = "ZONA_PELIGROSA";
		
		Empresa.getInstance().agregarCliente("pepe123", "123", "pepe");
		Cliente usuario_actual = (Cliente) Empresa.getInstance().login("pepe123", "123");
		when(this.vista_mock.getCantidadPax()).thenReturn(cant_pax);
		when(this.vista_mock.isPedidoConMascota()).thenReturn(mascota);
		when(this.vista_mock.isPedidoConBaul()).thenReturn(baul);
		when(this.vista_mock.getCantKm()).thenReturn(cant_km);
		when(this.vista_mock.getTipoZona()).thenReturn(tipo_zona);
		
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn("NUEVO_PEDIDO");
		this.controlador.actionPerformed(mockEvent);
	}
	
	@Test
	public void test_nuevo_chofer() {
		String dni = "12345";
		String nombre = "jorge";
		String tipo = "TEMPORARIO";
		
		when(this.vista_mock.getTipoChofer()).thenReturn(tipo);
		when(this.vista_mock.getNombreChofer()).thenReturn(nombre);
		when(this.vista_mock.getDNIChofer()).thenReturn(dni);
		
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn("NUEVO_CHOFER");
		this.controlador.actionPerformed(mockEvent);
	}
	
	@Test
	public void test_nuevo_vehiculo() {
		String patente = "mmm111";
		String tipo = "MOTO";
		
		when(this.vista_mock.getTipoVehiculo()).thenReturn(tipo);
		when(this.vista_mock.getPatente()).thenReturn(patente);
		
		ActionEvent mockEvent = mock(ActionEvent.class);
		when(mockEvent.getActionCommand()).thenReturn("NUEVO_VEHICULO");
		this.controlador.actionPerformed(mockEvent);
	}

}
