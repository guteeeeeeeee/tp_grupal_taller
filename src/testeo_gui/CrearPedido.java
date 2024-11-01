package testeo_gui;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloNegocio.Empresa;
import modeloDatos.Vehiculo;
import modeloDatos.Moto;

public class CrearPedido {
	Robot robot;
	Controlador controlador;
	String nombre_usuario = "pepe123";
	String password = "123";
	String nombre_real = "camilo fernandez";
	FalsoOptionPane op = new FalsoOptionPane();
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
		this.controlador.getVista().setOptionPane(this.op);
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
	}

	@Test
	public void cant_pax_1() { //tienen que ser 1 pasajero, 10 pasajeros o 0 km
		robot.delay(TestUtils.getDelay());
		JTextField text_cant_pax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CANT_PAX");
		JTextField text_cant_km = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CANT_KM");
		
		JRadioButton zona_peligrosa = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "ZONA_SIN_ASFALTAR");
		
		JCheckBox check_mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_MASCOTA");
		JCheckBox check_baul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_BAUL");
		
		JButton nuevo_pedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_PEDIDO");
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(text_cant_pax, robot);
		TestUtils.tipeaTexto("1", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(text_cant_km, robot);
		TestUtils.tipeaTexto("5", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(zona_peligrosa, robot);
		TestUtils.clickComponent(check_mascota, robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(check_baul, robot);
		assertTrue("el boton de generar nuevo pedido deberia estar habilitado",nuevo_pedido.isEnabled());
	}
	@Test
	public void cant_pax_10() { //tienen que ser 1 pasajero, 10 pasajeros o 0 km
		robot.delay(TestUtils.getDelay());
		JTextField text_cant_pax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CANT_PAX");
		JTextField text_cant_km = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CANT_KM");
		
		JRadioButton zona_peligrosa = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "ZONA_SIN_ASFALTAR");
		
		JCheckBox check_mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_MASCOTA");
		JCheckBox check_baul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_BAUL");
		
		JButton nuevo_pedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_PEDIDO");
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(text_cant_pax, robot);
		TestUtils.tipeaTexto("10", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(text_cant_km, robot);
		TestUtils.tipeaTexto("5", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(zona_peligrosa, robot);
		TestUtils.clickComponent(check_mascota, robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(check_baul, robot);
		assertTrue("el boton de generar nuevo pedido deberia estar habilitado",nuevo_pedido.isEnabled());
	}

	@Test
	public void cant_km_0() { //tienen que ser 1 pasajero, 10 pasajeros o 0 km
		robot.delay(TestUtils.getDelay());
		JTextField text_cant_pax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CANT_PAX");
		JTextField text_cant_km = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CANT_KM");
		
		JRadioButton zona_peligrosa = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "ZONA_SIN_ASFALTAR");
		
		JCheckBox check_mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_MASCOTA");
		JCheckBox check_baul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_BAUL");
		
		JButton nuevo_pedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_PEDIDO");
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(text_cant_pax, robot);
		TestUtils.tipeaTexto("1", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(text_cant_km, robot);
		TestUtils.tipeaTexto("0", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(zona_peligrosa, robot);
		TestUtils.clickComponent(check_mascota, robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(check_baul, robot);
		assertTrue("el boton de generar nuevo pedido deberia estar habilitado",nuevo_pedido.isEnabled());
	}
	@Test
	public void pedido_con_vehiculo_disponible() {
		Vehiculo moto = new Moto("aaa111");
		try {
			Empresa.getInstance().agregarVehiculo(moto);
		} catch (VehiculoRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robot.delay(TestUtils.getDelay());
		JTextField text_cant_pax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CANT_PAX");
		JTextField text_cant_km = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CANT_KM");
		
		JRadioButton zona_peligrosa = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "ZONA_SIN_ASFALTAR");
		
		JCheckBox check_mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_MASCOTA");
		JCheckBox check_baul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_BAUL");
		
		JButton nuevo_pedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_PEDIDO");
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(text_cant_pax, robot);
		TestUtils.tipeaTexto("1", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(text_cant_km, robot);
		TestUtils.tipeaTexto("5", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(zona_peligrosa, robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(nuevo_pedido, robot);
		assertTrue("se deberia haber borrado el text field de cant pasajeros",text_cant_pax.getText().isEmpty());
		assertTrue("se deberia haber borrado el text field de cant km",text_cant_pax.getText().isEmpty());
		JTextArea text_viajes_realizados = (JTextArea) TestUtils.getComponentForName((Component) controlador.getVista(), "PEDIDO_O_VIAJE_ACTUAL");
		assertFalse("la lista de viajes realizados no deberia estar vacia",text_viajes_realizados.getText().isEmpty());
	}
	@Test
	public void pedido_sin_vehiculo_disponible() {
		robot.delay(TestUtils.getDelay());
		JTextField text_cant_pax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CANT_PAX");
		JTextField text_cant_km = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CANT_KM");
		
		JRadioButton zona_peligrosa = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "ZONA_SIN_ASFALTAR");
		
		JCheckBox check_mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_MASCOTA");
		JCheckBox check_baul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_BAUL");
		
		JButton nuevo_pedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_PEDIDO");
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(text_cant_pax, robot);
		TestUtils.tipeaTexto("3", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(text_cant_km, robot);
		TestUtils.tipeaTexto("5", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(zona_peligrosa, robot);
		TestUtils.clickComponent(check_mascota, robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(check_baul, robot);
		TestUtils.clickComponent(nuevo_pedido, robot);
		assertEquals("deberia decir que no hay vehiculo disponible para el pedido","Ningun vehiculo puede satisfacer el pedido",op.getMensaje());
		JTextArea text_viajes_realizados = (JTextArea) TestUtils.getComponentForName((Component) controlador.getVista(), "PEDIDO_O_VIAJE_ACTUAL");
		assertTrue("la lista de viajes realizados deberia estar vacia",text_viajes_realizados.getText().isEmpty());
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
