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
import org.junit.Test;

import controlador.Controlador;
import excepciones.ChoferRepetidoException;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import testeo_gui.ayuda.TestUtils;
import util.Constantes;
import util.Mensajes;

public class RegistrarChofer_repetido {
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
		Chofer chofer_nuevo = new ChoferTemporario("123","abc");
		Empresa.getInstance().agregarChofer(chofer_nuevo);
		login_admin();
	}

	@Test
	public void registrar_chofer_permanente_repetido() throws ChoferRepetidoException {
		robot.delay(TestUtils.getDelay());
		JTextField dni = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.DNI_CHOFER);
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_CHOFER);
		JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.TEMPORARIO);
		JButton nuevo_chofer = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_CHOFER);
		
		JList lista_choferes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);
		
		TestUtils.clickComponent(dni, robot);
		TestUtils.tipeaTexto("123", robot);
		TestUtils.clickComponent(nombre, robot);
		TestUtils.tipeaTexto("abcabcabc", robot);
		TestUtils.clickComponent(temporario, robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("la cantidad de choferes actuales debe ser 1",1,lista_choferes.getModel().getSize());
		TestUtils.clickComponent(nuevo_chofer, robot);
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		assertEquals("deberia decir chofer ya registrado",Mensajes.CHOFER_YA_REGISTRADO.getValor(),op.getMensaje());
		assertEquals("la cantidad de choferes actuales debe seguir siendo 1",1,lista_choferes.getModel().getSize());
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
		robot.delay(TestUtils.getDelay());
		this.controlador.getVista().actualizar();
	}
	
	@After
	public void limpio() {
		Empresa.getInstance().getChoferes().clear();
	}

}
