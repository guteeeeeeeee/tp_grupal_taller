package testeo_gui;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import excepciones.ChoferRepetidoException;
import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;

public class RegistrarChofer {

	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	
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
		login_admin();
	}
	
	@Ignore
	@Test
	public void registrar_chofer_temporario() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "DNI_CHOFER");
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_CHOFER");
		JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "TEMPORARIO");
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_CHOFER");
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(temporario, robot);
		assertTrue("el boton de agregar nuevo chofer deberia estar habilitado",nuevo_chofer.isEnabled());
	}
	@Ignore
	@Test
	public void registrar_chofer_permanente_cant_hijos_0() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "DNI_CHOFER");
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_CHOFER");
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CH_CANT_HIJOS");
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CH_FECHA");
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "PERMANENTE");
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_CHOFER");
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(permanente, robot);
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
		TestUtils.clickComponent(cant_hijos, robot);
		TestUtils.tipeaTexto("0", robot);
		TestUtils.clickComponent(cant_anio, robot);
		TestUtils.tipeaTexto("2010", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue("el boton de agregar nuevo chofer deberia estar habilitado",nuevo_chofer.isEnabled());
	}
	@Ignore
	@Test
	public void registrar_chofer_permanente_anio_1900() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "DNI_CHOFER");
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_CHOFER");
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CH_CANT_HIJOS");
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CH_FECHA");
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "PERMANENTE");
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_CHOFER");
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(permanente, robot);
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
		TestUtils.clickComponent(cant_hijos, robot);
		TestUtils.tipeaTexto("2", robot);
		TestUtils.clickComponent(cant_anio, robot);
		TestUtils.tipeaTexto("1900", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue("el boton de agregar nuevo chofer deberia estar habilitado",nuevo_chofer.isEnabled());
	}
	
	@Ignore
	@Test
	public void registrar_chofer_permanente_anio_3000() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "DNI_CHOFER");
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_CHOFER");
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CH_CANT_HIJOS");
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CH_FECHA");
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "PERMANENTE");
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_CHOFER");
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(permanente, robot);
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
		TestUtils.clickComponent(cant_hijos, robot);
		TestUtils.tipeaTexto("2", robot);
		TestUtils.clickComponent(cant_anio, robot);
		TestUtils.tipeaTexto("3000", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue("el boton de agregar nuevo chofer deberia estar habilitado",nuevo_chofer.isEnabled());
	}
	
	@Ignore
	@Test
	public void registrar_chofer_permanente_correctamente() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "DNI_CHOFER");
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_CHOFER");
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CH_CANT_HIJOS");
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "CH_FECHA");
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "PERMANENTE");
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_CHOFER");
		
		JList lista_choferes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), "LISTA_CHOFERES_TOTALES");
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(permanente, robot);
		TestUtils.clickComponent(cant_hijos, robot);
		TestUtils.tipeaTexto("2", robot);
		TestUtils.clickComponent(cant_anio, robot);
		TestUtils.tipeaTexto("3000", robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("la cantidad de choferes actuales debe ser 0",0,lista_choferes.getModel().getSize());
		TestUtils.clickComponent(nuevo_chofer, robot);
		assertEquals("la cantidad de choferes actuales debe ser 1",1,lista_choferes.getModel().getSize());
		robot.delay(TestUtils.getDelay());
		assertEquals("debe estar vacio el campo dni",dni.getText().isEmpty());
		assertEquals("debe estar vacio el campo nombre",nombre.getText().isEmpty());
		assertEquals("debe estar vacio el campo cant hijos",cant_hijos.getText().isEmpty());
		assertEquals("debe estar vacio el campo anio de ingreso",cant_anio.getText().isEmpty());
	}
	
	@Test
	public void registrar_chofer_permanente_repetido() {
		registrar_chofer();
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "DNI_CHOFER");
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_CHOFER");
		JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), "TEMPORARIO");
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_CHOFER");
		
		JList lista_choferes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), "LISTA_CHOFERES_TOTALES");
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("123", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("abc", robot);
		TestUtils.clickComponent(temporario, robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("la cantidad de choferes actuales debe ser 1",1,lista_choferes.getModel().getSize());
		TestUtils.clickComponent(nuevo_chofer, robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("deberia decir chofer ya registrado","Chofer Ya Registrado",op.getMensaje());
		assertEquals("la cantidad de choferes actuales debe seguir siendo 1",1,lista_choferes.getModel().getSize());
	}
	
	public void registrar_chofer() {
		Chofer chofer_nuevo = new ChoferTemporario("123","abc");
		try {
			Empresa.getInstance().agregarChofer(chofer_nuevo);
		} catch (ChoferRepetidoException e) {
			e.printStackTrace();
		}
	}
	
	public void login_admin() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "NOMBRE_USUARIO");
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), "PASSWORD");
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto("admin",this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("admin",this.robot);
		robot.delay(TestUtils.getDelay());
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "LOGIN");
		TestUtils.clickComponent(boton_login, robot);
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getChoferes().clear();
	}

}
