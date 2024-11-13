package testeo_gui.vistaAdmin.registrarChofer;

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
import testeo_gui.ayuda.FalsoOptionPane;
import testeo_gui.ayuda.TestUtils;
import util.Constantes;
import util.Mensajes;
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
	
	@Test
	public void registrar_chofer_permanente_correctamente() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		
		JList lista_choferes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		
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
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		assertEquals("la cantidad de choferes actuales debe ser 1",1,lista_choferes.getModel().getSize());
		assertTrue("debe estar vacio el campo dni",dni.getText().isEmpty()); //no vacia los campos
		assertTrue("debe estar vacio el campo nombre",nombre.getText().isEmpty());
		assertTrue("debe estar vacio el campo cant hijos",cant_hijos.getText().isEmpty());
		assertTrue("debe estar vacio el campo anio de ingreso",cant_anio.getText().isEmpty());
	}
	
	@Test
	public void registrar_chofer_temporario() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.TEMPORARIO);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		JList lista_choferes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(temporario, robot);
		assertEquals("la cantidad de choferes actuales debe ser 0",0,lista_choferes.getModel().getSize());
		assertTrue("el boton de agregar nuevo chofer deberia estar habilitado",nuevo_chofer.isEnabled());
		TestUtils.clickComponent(nuevo_chofer, robot);
		robot.delay(TestUtils.getDelay());
		this.controlador.getVista().actualizar();
		assertEquals("la cantidad de choferes actuales debe ser 1",1,lista_choferes.getModel().getSize()); // se agrega
		assertTrue("debe estar vacio el campo dni",dni.getText().isEmpty()); //no vacia los campos
		assertTrue("debe estar vacio el campo nombre",nombre.getText().isEmpty());
	}
	
	@Test
	public void registrar_chofer_temporario_dni_si_nombre_vacio() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.TEMPORARIO);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		JList lista_choferes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("1", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(temporario, robot);
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
	}
	
	@Test
	public void registrar_chofer_temporario_nombre_si_dni_vacio() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.TEMPORARIO);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		JList lista_choferes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickComponent(temporario, robot);
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
	}
	
	@Test
	public void registrar_chofer_permanente_cant_hijos_0() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		
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
	
	@Test
	public void registrar_chofer_permanente_cant_hijos_negativa() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(permanente, robot);
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
		TestUtils.clickComponent(cant_hijos, robot);
		TestUtils.tipeaTexto("-1", robot);
		TestUtils.clickComponent(cant_anio, robot);
		TestUtils.tipeaTexto("2010", robot);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
	}

	@Test
	public void registrar_chofer_permanente_anio_1900() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		
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
	
	@Test
	public void registrar_chofer_permanente_anio_1899() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(permanente, robot);
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
		TestUtils.clickComponent(cant_hijos, robot);
		TestUtils.tipeaTexto("2", robot);
		TestUtils.clickComponent(cant_anio, robot);
		TestUtils.tipeaTexto("1899", robot);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
	}
	
	@Test
	public void registrar_chofer_permanente_anio_3000() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		
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
	
	@Test
	public void registrar_chofer_permanente_anio_3001() {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JTextField cant_hijos = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_CANT_HIJOS);
		JTextField cant_anio = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CH_ANIO);
		JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PERMANENTE);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("1", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(permanente, robot);
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
		TestUtils.clickComponent(cant_hijos, robot);
		TestUtils.tipeaTexto("2", robot);
		TestUtils.clickComponent(cant_anio, robot);
		TestUtils.tipeaTexto("3001", robot);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton de agregar nuevo chofer deberia estar deshabilitado",nuevo_chofer.isEnabled());
	}
	
	public void login_admin() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto("admin",this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto("admin",this.robot);
		robot.delay(TestUtils.getDelay());
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
		TestUtils.clickComponent(boton_login, robot);
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getChoferes().clear();
	}

}
