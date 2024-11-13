package testeo_gui.panelCliente;

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
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Moto;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import testeo_gui.ayuda.TestUtils;
import util.Constantes;
import util.Mensajes;

public class CrearPedido_sin_vehiculo_disponible {

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
		Empresa.getInstance().agregarCliente(this.nombre_usuario,this.password,nombre_real);
		login_usuario();
	}

	@Test
	public void pedido_sin_vehiculo_disponible() {
		robot.delay(TestUtils.getDelay());
		JTextField text_cant_pax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		JTextField text_cant_km = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
		
		JRadioButton zona_peligrosa = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_SIN_ASFALTAR);
		
		JCheckBox check_mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
		JCheckBox check_baul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
		
		JButton nuevo_pedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		
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
		TestUtils.clickComponent(nuevo_pedido, robot);
		assertEquals("deberia decir que no hay vehiculo disponible para el pedido",Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(),op.getMensaje());
		JTextArea text_viajes_realizados = (JTextArea) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
		assertTrue("la lista de viajes realizados deberia estar vacia",text_viajes_realizados.getText().isEmpty());
	}
	
	public void login_usuario() {
		robot.delay(TestUtils.getDelay());
		JTextField nombre = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NOMBRE_USUARIO);
		JTextField password = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PASSWORD);
		TestUtils.clickComponent(nombre, this.robot);
		TestUtils.tipeaTexto(this.nombre_usuario,this.robot);
		TestUtils.clickComponent(password, this.robot);
		TestUtils.tipeaTexto(this.password,this.robot);
		robot.delay(TestUtils.getDelay());
		JButton boton_login = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LOGIN);
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
