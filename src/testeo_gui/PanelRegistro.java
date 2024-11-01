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

public class PanelRegistro {

	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	String nombre_usuario = "ricardo22";
	String password = "harakiri";
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
		this.controlador.getVista().setOptionPane(this.op);
		TestUtils.setDelay(100);
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		try {
			Empresa.getInstance().agregarCliente(this.nombre_usuario,this.password,"ricardo alberto");
		} catch (UsuarioYaExisteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_registro_ok() {
		TestUtils.setDelay(100);
		robot.delay(TestUtils.getDelay());
		JButton registro = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REGISTRAR");
		assertTrue(registro.isEnabled());
		TestUtils.clickComponent(registro, this.robot);
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_USSER_NAME");
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("pepe123", robot);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_PASSWORD");
		TestUtils.clickComponent(password, robot);
		TestUtils.tipeaTexto("12345", robot);
		JTextField confirmacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_CONFIRM_PASSWORD");
		TestUtils.clickComponent(confirmacion, robot);
		TestUtils.tipeaTexto("12345", robot);
		JTextField nombre_real = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_REAL_NAME");
		TestUtils.clickComponent(nombre_real, robot);
		TestUtils.tipeaTexto("juan carlos", robot);
		JButton registrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_REGISTRAR");
		assertTrue("el boton registrar debe estar habilitado",registrar.isEnabled());
		JButton boton_cancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_CANCELAR");
		assertTrue("el boton cancelar deberia estar siempre habilitado",boton_cancelar.isEnabled());
	}
	
	@Test
	public void test_registro_usuario_repetido() {
		robot.delay(TestUtils.getDelay());
		JButton registro = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REGISTRAR");
		assertTrue(registro.isEnabled());
		TestUtils.clickComponent(registro, this.robot);
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_USSER_NAME");
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto(this.nombre_usuario, robot); //nombre repetido
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_PASSWORD");
		TestUtils.clickComponent(password, robot);
		TestUtils.tipeaTexto("12345", robot);
		JTextField confirmacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_CONFIRM_PASSWORD");
		TestUtils.clickComponent(confirmacion, robot);
		TestUtils.tipeaTexto("12345", robot);
		JTextField nombre_real = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_REAL_NAME");
		TestUtils.clickComponent(nombre_real, robot);
		TestUtils.tipeaTexto("juan carlos", robot);
		JButton registrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_REGISTRAR");
		assertTrue("el boton registrar debe estar habilitado",registrar.isEnabled());
		JButton boton_cancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_CANCELAR");
		assertTrue("el boton cancelar deberia estar siempre habilitado",boton_cancelar.isEnabled());
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(registrar, robot);
		assertEquals("deberia decir usuario repetido","Usuario repetido",op.getMensaje());
	}
	
	@Test
	public void test_registro_pass_y_confirmacion_no_coinciden() {
		robot.delay(TestUtils.getDelay());
		JButton registro = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REGISTRAR");
		assertTrue(registro.isEnabled());
		TestUtils.clickComponent(registro, this.robot);
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_USSER_NAME");
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("isis", robot);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_PASSWORD");
		TestUtils.clickComponent(password, robot);
		TestUtils.tipeaTexto(this.password, robot);
		JTextField confirmacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_CONFIRM_PASSWORD");
		TestUtils.clickComponent(confirmacion, robot);
		TestUtils.tipeaTexto("12345", robot); //no coinciden
		JTextField nombre_real = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_REAL_NAME");
		TestUtils.clickComponent(nombre_real, robot);
		TestUtils.tipeaTexto("juan carlos", robot);
		JButton registrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_REGISTRAR");
		assertTrue("el boton registrar debe estar habilitado",registrar.isEnabled());
		JButton boton_cancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_CANCELAR");
		assertTrue("el boton cancelar deberia estar siempre habilitado",boton_cancelar.isEnabled());
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(registrar, robot);
		assertEquals("deberia decir que no coinciden password y confirmacion","La contrasena y su confirmacion no coinciden",op.getMensaje());
	}
	
	@Test
	public void test_boton_cancelar_habilitado() {
		TestUtils.setDelay(100);
		robot.delay(TestUtils.getDelay());
		JButton registrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REGISTRAR");
		assertTrue(registrar.isEnabled());
		TestUtils.clickComponent(registrar, this.robot);
		robot.delay(TestUtils.getDelay());
		JButton boton_cancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_CANCELAR");
		assertTrue("el boton cancelar deberia estar siempre habilitado",boton_cancelar.isEnabled());
	}
	
	@Test
	public void test_lo_demas_vacio() {
		TestUtils.setDelay(100);
		robot.delay(TestUtils.getDelay());
		JButton registro = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REGISTRAR");
		assertTrue(registro.isEnabled());
		TestUtils.clickComponent(registro, this.robot);
		robot.delay(TestUtils.getDelay());
		JButton registrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_REGISTRAR");
		assertFalse("el boton registrar debe estar deshabilitado",registrar.isEnabled());
		JButton boton_cancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_CANCELAR");
		assertTrue("el boton cancelar deberia estar siempre habilitado",boton_cancelar.isEnabled());
	}
	
	@Test
	public void test_nombre_ok_lo_demas_vacio() {
		TestUtils.setDelay(100);
		robot.delay(TestUtils.getDelay());
		JButton registro = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REGISTRAR");
		assertTrue(registro.isEnabled());
		TestUtils.clickComponent(registro, this.robot);
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_USSER_NAME");
		JButton registrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_REGISTRAR");
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("pepe123", robot);
		assertFalse("el boton registrar debe estar deshabilitado",registrar.isEnabled());
		JButton boton_cancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_CANCELAR");
		assertTrue("el boton cancelar deberia estar siempre habilitado",boton_cancelar.isEnabled());
	}
	
	@Test
	public void test_password_ok_lo_demas_vacio() {
		TestUtils.setDelay(100);
		robot.delay(TestUtils.getDelay());
		JButton registro = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REGISTRAR");
		assertTrue(registro.isEnabled());
		TestUtils.clickComponent(registro, this.robot);
		robot.delay(TestUtils.getDelay());
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_PASSWORD");
		JButton registrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_REGISTRAR");
		TestUtils.clickComponent(password, robot);
		TestUtils.tipeaTexto("12345", robot);
		assertFalse("el boton registrar debe estar deshabilitado",registrar.isEnabled());
		JButton boton_cancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_CANCELAR");
		assertTrue("el boton cancelar deberia estar siempre habilitado",boton_cancelar.isEnabled());
	}
	
	@Test
	public void test_confirmacion_ok_lo_demas_vacio() {
		TestUtils.setDelay(100);
		robot.delay(TestUtils.getDelay());
		JButton registro = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REGISTRAR");
		assertTrue(registro.isEnabled());
		TestUtils.clickComponent(registro, this.robot);
		robot.delay(TestUtils.getDelay());
		JTextField confirmacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_CONFIRM_PASSWORD");
		JButton registrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_REGISTRAR");
		TestUtils.clickComponent(confirmacion, robot);
		TestUtils.tipeaTexto("12345", robot);
		assertFalse("el boton registrar debe estar deshabilitado",registrar.isEnabled());
		JButton boton_cancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_CANCELAR");
		assertTrue("el boton cancelar deberia estar siempre habilitado",boton_cancelar.isEnabled());
	}
	
	@Test
	public void test_nombre_real_ok_lo_demas_vacio() {
		TestUtils.setDelay(100);
		robot.delay(TestUtils.getDelay());
		JButton registro = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REGISTRAR");
		assertTrue(registro.isEnabled());
		TestUtils.clickComponent(registro, this.robot);
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_REAL_NAME");
		JButton registrar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_REGISTRAR");
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("juan carlos", robot);
		assertFalse("el boton registrar debe estar deshabilitado",registrar.isEnabled());
		JButton boton_cancelar = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "REG_BUTTON_CANCELAR");
		assertTrue("el boton cancelar deberia estar siempre habilitado",boton_cancelar.isEnabled());
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getClientes().clear();
	}

}
