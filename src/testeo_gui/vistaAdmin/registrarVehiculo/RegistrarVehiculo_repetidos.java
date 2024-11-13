package testeo_gui.vistaAdmin.registrarVehiculo;

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
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import testeo_gui.ayuda.TestUtils;
import util.Constantes;
import util.Mensajes;

public class RegistrarVehiculo_repetidos {
	
	Robot robot;
	Controlador controlador;
	FalsoOptionPane op = new FalsoOptionPane();
	String patente;
	
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
		this.patente = "prr765";
		Vehiculo moto_nueva = new Moto(this.patente);
		Empresa.getInstance().agregarVehiculo(moto_nueva);
		/*Vehiculo auto_nuevo = new Auto("poo123",4,false);
		Empresa.getInstance().agregarVehiculo(auto_nuevo);
		Vehiculo combi_nueva = new Combi("uaa177",8,false);
		Empresa.getInstance().agregarVehiculo(combi_nueva);*/
		this.controlador.getVista().actualizar();
	}
	
	@Test
	public void test_registrar_moto_repetido() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JRadioButton boton_moto = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.MOTO);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		JList<Vehiculo> lista_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto(this.patente, robot);
		TestUtils.clickComponent(boton_moto, robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
		TestUtils.clickComponent(nuevo_vehiculo, robot);
		assertEquals("deberia decir vehiculo ya registrado",Mensajes.VEHICULO_YA_REGISTRADO.getValor(),op.getMensaje());
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
	}
	
	@Test
	public void test_registrar_auto_repetido() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField cant_plazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		JRadioButton boton_auto = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.AUTO);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		JList<Vehiculo> lista_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto(this.patente, robot);
		TestUtils.clickComponent(boton_auto, robot);
		TestUtils.clickComponent(cant_plazas, robot);
		TestUtils.tipeaTexto("4", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue("el boton de registrar nuevo vehiculo debe estar habilitado",nuevo_vehiculo.isEnabled());
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
		TestUtils.clickComponent(nuevo_vehiculo, robot);
		assertEquals("deberia decir vehiculo ya registrado",Mensajes.VEHICULO_YA_REGISTRADO.getValor(),op.getMensaje());
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
	}
	
	@Test
	public void test_registrar_combi_repetida() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField cant_plazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		JRadioButton boton_combi = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.COMBI);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		JList<Vehiculo> lista_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		//JCheckBox mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_VEHICULO_ACEPTA_MASCOTA");
		
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto(this.patente, robot);
		TestUtils.clickComponent(boton_combi, robot);
		assertFalse("el boton de registrar nuevo vehiculo debe estar deshabilitado",nuevo_vehiculo.isEnabled());
		TestUtils.clickComponent(cant_plazas, robot);
		TestUtils.tipeaTexto("8", robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
		TestUtils.clickComponent(nuevo_vehiculo, robot);
		assertEquals("deberia decir vehiculo ya registrado",Mensajes.VEHICULO_YA_REGISTRADO.getValor(),op.getMensaje());
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
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
		Empresa.getInstance().getVehiculos().clear();
	}

}
