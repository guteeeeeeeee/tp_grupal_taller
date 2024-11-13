package testeo_gui.panelLogin;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import testeo_gui.ayuda.TestUtils;
import util.Constantes;
import util.Mensajes;

public class PanelLogin {

	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	String nombre_usuario = "pepe123";
	String password = "123";
	String nombre_real = "jorge";
	
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
		Empresa.getInstance().agregarCliente(this.nombre_usuario,this.password,this.nombre_real);
	}
	
	@Test
	public void test_nombre_password_ok() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto(this.nombre_usuario,this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto(this.password,this.robot);
		robot.delay(TestUtils.getDelay());
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		assertTrue("el boton login deberia estar habilitado",boton_login.isEnabled());
		TestUtils.clickComponent(boton_login, robot);
		//pasa a la ventana de cliente
		robot.delay(TestUtils.getDelay());
		JPanel panel_cliente = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_CLIENTE);
		TitledBorder border = (TitledBorder) panel_cliente.getBorder();
		assertTrue("ingresamos al panel de cliente",panel_cliente.isEnabled());
		assertEquals(this.nombre_real,border.getTitle());
	}
	
	@Test
	public void test_password_incorrecta() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto(this.nombre_usuario,this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("12345",this.robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(boton_login, robot);
		assertEquals("deberia decir password incorrecta",Mensajes.PASS_ERRONEO.getValor(),op.getMensaje());
	}
	
	@Test
	public void test_nombre_usuario_incorrecto() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto("cualquiera",this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("123",this.robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(boton_login, robot);
		assertEquals("deberia decir nombre de usuario incorrecto",Mensajes.USUARIO_DESCONOCIDO.getValor(),op.getMensaje());
	}

	@Test
	public void test_sin_escribir_nombre_ni_password() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		assertFalse("el boton login deberia estar deshabilitado",boton_login.isEnabled());
	}
	
	@Test
	public void test_nombre_si_pero_no_password() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto("pepe22",this.robot);
		assertFalse("el boton login deberia estar deshabilitado",boton_login.isEnabled());
	}
	
	@Test
	public void test_password_si_pero_no_nombre() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("12345",this.robot);
		assertFalse("el boton login deberia estar deshabilitado",boton_login.isEnabled());
	}
	
	@Test
	public void test_admin_correcto() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto("admin",this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("admin",this.robot);
		robot.delay(TestUtils.getDelay());
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		assertTrue("el boton login deberia estar habilitado",boton_login.isEnabled());
		TestUtils.clickComponent(boton_login, robot);
		//pasa a la ventana del administrador
		robot.delay(TestUtils.getDelay());
		JPanel panel_admin = (JPanel) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PANEL_ADMINISTRADOR);
		assertTrue("ingresamos al panel de administrador",panel_admin.isEnabled());
	}
	
	@Test
	public void test_admin_password_incorrecta() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto("admin",this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("cualquiera",this.robot);
		robot.delay(TestUtils.getDelay());
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		assertTrue("el boton login deberia estar habilitado",boton_login.isEnabled());
		TestUtils.clickComponent(boton_login, robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("deberia decir password incorrecta",Mensajes.PASS_ERRONEO.getValor(),op.getMensaje());
	}

	@After
	public void limpio() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
	}
}
