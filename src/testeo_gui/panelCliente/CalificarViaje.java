package testeo_gui.panelCliente;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import util.Constantes;
import util.Mensajes;
import excepciones.ChoferNoDisponibleException;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.TestUtils;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;

public class CalificarViaje {
	Robot robot;
	Controlador controlador;
	String nombre_usuario = "pepe123";
	String password = "123";
	String nombre_real = "camilo fernandez";
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
		try {
			this.robot = new Robot();
			TestUtils.setDelay(100);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		try {
			Empresa.getInstance().agregarCliente(this.nombre_usuario,this.password,nombre_real);
		} catch (UsuarioYaExisteException e) {
			e.printStackTrace();
		}
		login_usuario();
		Vehiculo auto = new Auto("fpp645",4,true);
		Empresa.getInstance().agregarVehiculo(auto);
		Chofer chofer = new ChoferTemporario("213213","fernando");
		Empresa.getInstance().agregarChofer(chofer);
		Cliente cliente_logeado = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		Pedido pedido = new Pedido(cliente_logeado,3,false,false,5,"ZONA_STANDARD");
		Empresa.getInstance().agregarPedido(pedido);
		Empresa.getInstance().crearViaje(pedido, chofer, auto);
		this.controlador.getVista().actualizar();
	}
	
	@Test
	public void test_pagar_calificar_0() throws VehiculoRepetidoException, ChoferRepetidoException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException, PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException {
		robot.delay(TestUtils.getDelay());
		JTextField text_calificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
	
		JButton boton_pagar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
		
		assertFalse("el boton para pagar deberia estar deshabilitado",boton_pagar.isEnabled());
		TestUtils.clickComponent(text_calificacion, robot);
		TestUtils.tipeaTexto("0", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue("el boton para pagar deberia estar habilitado",boton_pagar.isEnabled());
	}
	
	@Test
	public void test_pagar_calificar_5() throws VehiculoRepetidoException, ChoferRepetidoException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException, PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException {
		robot.delay(TestUtils.getDelay());
		JTextField text_calificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		
		JButton boton_pagar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
		
		assertFalse("el boton para pagar deberia estar deshabilitado",boton_pagar.isEnabled());
		TestUtils.clickComponent(text_calificacion, robot);
		TestUtils.tipeaTexto("5", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue("el boton para pagar deberia estar habilitado",boton_pagar.isEnabled());
	}
	
	@Test
	public void test_pagar() throws VehiculoRepetidoException, ChoferRepetidoException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException, PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException {
		robot.delay(TestUtils.getDelay());
		JList lista_viajes_cliente = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_CLIENTE);

		JTextArea text_viajes_realizados = (JTextArea) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
		assertFalse("la lista de viajes realizados no deberia estar vacia",text_viajes_realizados.getText().isEmpty());
		
		JTextField text_calificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		
		JButton boton_pagar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICAR_PAGAR);
		
		TestUtils.clickComponent(text_calificacion, robot);
		TestUtils.tipeaTexto("3", robot);
		robot.delay(TestUtils.getDelay());
		
		assertEquals("la lista de viajes del cliente deberia estar vacia",0,lista_viajes_cliente.getModel().getSize());
		
		TestUtils.clickComponent(boton_pagar, robot);

		robot.delay(TestUtils.getDelay());

		assertTrue("la lista de viajes realizados no deberia estar vacia",text_viajes_realizados.getText().isEmpty());
		assertNotEquals("la lista de viajes del cliente no deberia estar vacia",0,lista_viajes_cliente.getModel().getSize());
		assertTrue("no se borro el contenido de la calificacion",text_calificacion.getText().isEmpty());
	}
	
	public void login_usuario() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_USUARIO");
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "PASSWORD");
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto(this.nombre_usuario,this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto(this.password,this.robot);
		robot.delay(TestUtils.getDelay());
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "LOGIN");
		TestUtils.clickComponent(boton_login, robot);
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getViajesTerminados().clear();
	}
}
