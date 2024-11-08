package testeo_gui;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import modeloDatos.Vehiculo;

public class RegistrarVehiculo {

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
	public void test_registrar_moto() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JRadioButton boton_moto = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.MOTO);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		assertFalse("el boton de registrar nuevo vehiculo debe estar deshabilitado",nuevo_vehiculo.isEnabled());
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(boton_moto, robot);
		assertTrue("el boton de registrar nuevo vehiculo debe estar habilitado",nuevo_vehiculo.isEnabled());
	}
	
	@Test
	public void test_registrar_auto_plazas_1() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField cant_plazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		JRadioButton boton_auto = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.AUTO);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		assertFalse("el boton de registrar nuevo vehiculo debe estar deshabilitado",nuevo_vehiculo.isEnabled());
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(boton_auto, robot);
		assertFalse("el boton de registrar nuevo vehiculo debe estar deshabilitado",nuevo_vehiculo.isEnabled());
		TestUtils.clickComponent(cant_plazas, robot);
		TestUtils.tipeaTexto("1", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue("el boton de registrar nuevo vehiculo debe estar habilitado",nuevo_vehiculo.isEnabled());
	}
	
	@Test
	public void test_registrar_auto_plazas_4() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField cant_plazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		JRadioButton boton_auto = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.AUTO);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		assertFalse("el boton de registrar nuevo vehiculo debe estar deshabilitado",nuevo_vehiculo.isEnabled());
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(boton_auto, robot);
		assertFalse("el boton de registrar nuevo vehiculo debe estar deshabilitado",nuevo_vehiculo.isEnabled());
		TestUtils.clickComponent(cant_plazas, robot);
		TestUtils.tipeaTexto("4", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue("el boton de registrar nuevo vehiculo debe estar habilitado",nuevo_vehiculo.isEnabled());
	}
	
	@Test
	public void test_registrar_combi_plazas_5() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField cant_plazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		JRadioButton boton_combi = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.COMBI);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		assertFalse("el boton de registrar nuevo vehiculo debe estar deshabilitado",nuevo_vehiculo.isEnabled());
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(boton_combi, robot);
		assertFalse("el boton de registrar nuevo vehiculo debe estar deshabilitado",nuevo_vehiculo.isEnabled());
		TestUtils.clickComponent(cant_plazas, robot);
		TestUtils.tipeaTexto("5", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue("el boton de registrar nuevo vehiculo debe estar habilitado",nuevo_vehiculo.isEnabled());
	}
	
	@Test
	public void test_registrar_combi_plazas_10() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField cant_plazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		JRadioButton boton_combi = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.COMBI);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		assertFalse("el boton de registrar nuevo vehiculo debe estar deshabilitado",nuevo_vehiculo.isEnabled());
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("a", robot);
		TestUtils.clickComponent(boton_combi, robot);
		assertFalse("el boton de registrar nuevo vehiculo debe estar deshabilitado",nuevo_vehiculo.isEnabled());
		TestUtils.clickComponent(cant_plazas, robot);
		TestUtils.tipeaTexto("10", robot);
		robot.delay(TestUtils.getDelay());
		assertTrue("el boton de registrar nuevo vehiculo debe estar habilitado",nuevo_vehiculo.isEnabled());
	}
	
	@Test
	public void test_registrar_combi_exitosamente() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField cant_plazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		JRadioButton boton_combi = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.COMBI);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		JList<Vehiculo> lista_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("abc121", robot);
		TestUtils.clickComponent(boton_combi, robot);
		TestUtils.clickComponent(cant_plazas, robot);
		TestUtils.tipeaTexto("7", robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("el size de la lista deberia ser 0",0,lista_vehiculos.getModel().getSize());
		TestUtils.clickComponent(nuevo_vehiculo, robot);
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
		assertEquals("deberia tener la misma patente que se ingreso","abc121",lista_vehiculos.getModel().getElementAt(0).getPatente());
		assertEquals("deberia tener la misma cant de plazas que se ingreso",7,lista_vehiculos.getModel().getElementAt(0).getCantidadPlazas());
	}
	
	@Test
	public void test_registrar_combi_repetida() {
		robot.delay(TestUtils.getDelay());
		agregar_combi();
		controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField cant_plazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		JRadioButton boton_combi = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.COMBI);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		JList<Vehiculo> lista_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		//JCheckBox mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), "CHECK_VEHICULO_ACEPTA_MASCOTA");
		
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("uaa177", robot);
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
	
	@Test
	public void test_registrar_auto_exitosamente() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField cant_plazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		JRadioButton boton_auto = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.AUTO);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		JList<Vehiculo> lista_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("yta129", robot);
		TestUtils.clickComponent(boton_auto, robot);
		TestUtils.clickComponent(cant_plazas, robot);
		TestUtils.tipeaTexto("3", robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("el size de la lista deberia ser 0",0,lista_vehiculos.getModel().getSize());
		TestUtils.clickComponent(nuevo_vehiculo, robot);
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
		assertEquals("deberia tener la misma patente que se ingreso","yta129",lista_vehiculos.getModel().getElementAt(0).getPatente());
		assertEquals("deberia tener la misma cant de plazas que se ingreso",3,lista_vehiculos.getModel().getElementAt(0).getCantidadPlazas());
	}
	
	@Test
	public void test_registrar_auto_repetido() {
		robot.delay(TestUtils.getDelay());
		agregar_auto();
		controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JTextField cant_plazas = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANTIDAD_PLAZAS);
		JRadioButton boton_auto = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.AUTO);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		JList<Vehiculo> lista_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("poo123", robot);
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
	public void test_registrar_moto_exitosamente() {
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JRadioButton boton_moto = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.MOTO);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		JList<Vehiculo> lista_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("yta129", robot);
		TestUtils.clickComponent(boton_moto, robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("el size de la lista deberia ser 0",0,lista_vehiculos.getModel().getSize());
		TestUtils.clickComponent(nuevo_vehiculo, robot);
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
		assertEquals("deberia tener la misma patente que se ingreso","yta129",lista_vehiculos.getModel().getElementAt(0).getPatente());
		assertEquals("deberia tener la misma cant de plazas que se ingreso",1,lista_vehiculos.getModel().getElementAt(0).getCantidadPlazas());
	}
	
	@Test
	public void test_registrar_moto_repetido() {
		robot.delay(TestUtils.getDelay());
		agregar_moto();
		controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		JTextField patente = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PATENTE);
		JRadioButton boton_moto = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.MOTO);
		JButton nuevo_vehiculo = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VEHICULO);
		JList<Vehiculo> lista_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		
		TestUtils.clickComponent(patente, robot);
		TestUtils.tipeaTexto("prr765", robot);
		TestUtils.clickComponent(boton_moto, robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
		TestUtils.clickComponent(nuevo_vehiculo, robot);
		assertEquals("deberia decir vehiculo ya registrado","Vehiculo ya Registrado",op.getMensaje());
		assertEquals("el size de la lista deberia ser 1",1,lista_vehiculos.getModel().getSize());
	}
	
	
	public void agregar_moto() {
		Vehiculo moto_nueva = new Moto("prr765");
		try {
			Empresa.getInstance().agregarVehiculo(moto_nueva);
		} catch (VehiculoRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void agregar_auto() {
		Vehiculo auto_nuevo = new Auto("poo123",4,false);
		try {
			Empresa.getInstance().agregarVehiculo(auto_nuevo);
		} catch (VehiculoRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void agregar_combi() {
		Vehiculo combi_nueva = new Combi("uaa177",8,false);
		try {
			Empresa.getInstance().agregarVehiculo(combi_nueva);
		} catch (VehiculoRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
