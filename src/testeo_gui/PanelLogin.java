package testeo_gui;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;

public class PanelLogin {

	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	String nombre_usuario = "pepe123";
	String password = "123";
	
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
			Empresa.getInstance().agregarCliente(this.nombre_usuario,this.password,"jorge rafael");
		} catch (UsuarioYaExisteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_nombre_password_ok() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_USUARIO");
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "PASSWORD");
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto("pepe22",this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("12345",this.robot);
		robot.delay(TestUtils.getDelay());
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "LOGIN");
		assertTrue("el boton login deberia estar habilitado",boton_login.isEnabled());
	}
	
	@Test
	public void test_password_incorrecta() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_USUARIO");
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "PASSWORD");
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "LOGIN");
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto(this.nombre_usuario,this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("12345",this.robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(boton_login, robot);
		assertEquals("deberia decir password incorrecta","Password incorrecto",op.getMensaje());
	}
	
	@Test
	public void test_nombre_usuario_incorrecto() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_USUARIO");
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "PASSWORD");
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "LOGIN");
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto("cualquiera",this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("123",this.robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(boton_login, robot);
		assertEquals("deberia decir nombre de usuario incorrecto","Usuario inexistente",op.getMensaje());
	}

	@Test
	public void test_sin_escribir_nombre_ni_password() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_USUARIO");
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "PASSWORD");
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "LOGIN");
		assertFalse("el boton login deberia estar deshabilitado",boton_login.isEnabled());
	}
	
	@Test
	public void test_nombre_si_pero_no_password() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_USUARIO");
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "PASSWORD");
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "LOGIN");
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto("pepe22",this.robot);
		assertFalse("el boton login deberia estar deshabilitado",boton_login.isEnabled());
	}
	
	@Test
	public void test_password_si_pero_no_nombre() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_USUARIO");
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "PASSWORD");
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "LOGIN");
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("12345",this.robot);
		assertFalse("el boton login deberia estar deshabilitado",boton_login.isEnabled());
	}

	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
	}
}
