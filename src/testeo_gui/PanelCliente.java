package testeo_gui;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.awt.TextField;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import excepciones.ChoferNoDisponibleException;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloNegocio.Empresa;
import util.Constantes;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;

public class PanelCliente {
	Robot robot;
	Controlador controlador;
	String nombre_usuario = "pepe123";
	String password = "123";
	String nombre_real = "camilo fernandez";
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
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
	public void test_ningun_viaje_realizado_ni_iniciado() {
		JTextArea text_viajes_realizados = (JTextArea) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
		assertTrue("la lista de viajes realizados deberia estar vacia",text_viajes_realizados.getText().isEmpty());
		JTextField text_cant_pax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		assertTrue(text_cant_pax.isEnabled());
		JTextField text_cant_km = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
		assertTrue(text_cant_km.isEnabled());
		
		JRadioButton zona_standard = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
		assertTrue("deberia estar habilitado",zona_standard.isEnabled());
		JRadioButton zona_sin_asfaltar = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_SIN_ASFALTAR);
		assertTrue("deberia estar habilitado",zona_sin_asfaltar.isEnabled());
		JRadioButton zona_peligrosa = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_PELIGROSA);
		assertTrue("deberia estar habilitado",zona_peligrosa.isEnabled());
		
		JCheckBox check_mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
		assertTrue("el check de mascota deberia estar habilitado",check_mascota.isEnabled());
		JCheckBox check_baul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
		assertTrue("el check de baul deberia estar habilitado",check_baul.isEnabled());
		
		JTextField text_calificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		assertFalse("no tendria que estar habilitado poner la calificacion",text_calificacion.isEnabled());
		JTextField text_valor = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
		assertTrue("el valor deberia estar vacio",text_valor.getText().isEmpty());
		JButton nuevo_pedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertFalse("deberia estar deshabilitado",nuevo_pedido.isEnabled());
	}
	
	@Test
	public void test_pedido_esperando() throws VehiculoRepetidoException, ChoferRepetidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException, SinVehiculoParaPedidoException {
		agregarPedido();
		controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		JTextArea text_viajes_realizados = (JTextArea) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
		assertFalse("la lista de viajes realizados no deberia estar vacia",text_viajes_realizados.getText().isEmpty());
		JTextField text_cant_pax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		assertFalse(text_cant_pax.isEnabled());
		JTextField text_cant_km = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
		assertFalse(text_cant_km.isEnabled());
		
		JRadioButton zona_standard = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
		assertFalse("deberia estar habilitado",zona_standard.isEnabled());
		JRadioButton zona_sin_asfaltar = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_SIN_ASFALTAR);
		assertFalse("deberia estar habilitado",zona_sin_asfaltar.isEnabled());
		JRadioButton zona_peligrosa = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_PELIGROSA);
		assertFalse("deberia estar habilitado",zona_peligrosa.isEnabled());
		
		JCheckBox check_mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
		assertFalse("el check de mascota deberia estar habilitado",check_mascota.isEnabled());
		JCheckBox check_baul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
		assertFalse("el check de baul deberia estar habilitado",check_baul.isEnabled());
		
		JTextField text_calificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		assertFalse("no tendria que estar habilitado poner la calificacion",text_calificacion.isEnabled());
		JTextField text_valor = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
		assertTrue("el valor deberia estar vacio",text_valor.getText().isEmpty());
		JButton nuevo_pedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertFalse("deberia estar deshabilitado",nuevo_pedido.isEnabled());
	}
	
	
	@Test
	public void test_pedido_en_viaje() throws PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException, ClienteConViajePendienteException, VehiculoRepetidoException, ChoferRepetidoException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConPedidoPendienteException {
		agregarViaje();
		controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		JTextArea text_viajes_realizados = (JTextArea) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.PEDIDO_O_VIAJE_ACTUAL);
		assertFalse("la lista de viajes realizados no deberia estar vacia",text_viajes_realizados.getText().isEmpty());
		JTextField text_cant_pax = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_PAX);
		assertFalse(text_cant_pax.isEnabled());
		JTextField text_cant_km = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CANT_KM);
		assertFalse(text_cant_km.isEnabled());
		
		JRadioButton zona_standard = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_STANDARD);
		assertFalse("deberia estar habilitado",zona_standard.isEnabled());
		JRadioButton zona_sin_asfaltar = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_SIN_ASFALTAR);
		assertFalse("deberia estar habilitado",zona_sin_asfaltar.isEnabled());
		JRadioButton zona_peligrosa = (JRadioButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.ZONA_PELIGROSA);
		assertFalse("deberia estar habilitado",zona_peligrosa.isEnabled());
		
		JCheckBox check_mascota = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_MASCOTA);
		assertFalse("el check de mascota deberia estar habilitado",check_mascota.isEnabled());
		JCheckBox check_baul = (JCheckBox) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CHECK_BAUL);
		assertFalse("el check de baul deberia estar habilitado",check_baul.isEnabled());
		
		JTextField text_calificacion = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_DE_VIAJE);
		assertTrue("no tendria que estar habilitado poner la calificacion",text_calificacion.isEnabled());
		JTextField text_valor = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.VALOR_VIAJE);
		assertFalse("el valor deberia estar vacio",text_valor.getText().isEmpty());
		JButton nuevo_pedido = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_PEDIDO);
		assertFalse("deberia estar deshabilitado",nuevo_pedido.isEnabled());
	}
	
	public void agregarPedido() throws VehiculoRepetidoException, ChoferRepetidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException, SinVehiculoParaPedidoException {
		Vehiculo auto = new Auto("fpp645",4,true);
		Empresa.getInstance().agregarVehiculo(auto);
		Chofer chofer = new ChoferTemporario("213213","fernando");
		Empresa.getInstance().agregarChofer(chofer);
		Cliente cliente_logeado = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		Pedido pedido = new Pedido(cliente_logeado,3,false,false,5,"ZONA_STANDARD");
		Empresa.getInstance().agregarPedido(pedido);
		
	}
	
	public void agregarViaje() throws PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException, ClienteConViajePendienteException, VehiculoRepetidoException, ChoferRepetidoException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConPedidoPendienteException {
		Vehiculo auto = new Auto("fpp645",4,true);
		Empresa.getInstance().agregarVehiculo(auto);
		Chofer chofer = new ChoferTemporario("213213","fernando");
		Empresa.getInstance().agregarChofer(chofer);
		Cliente cliente_logeado = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		Pedido pedido = new Pedido(cliente_logeado,3,false,false,5,"ZONA_STANDARD");
		Empresa.getInstance().agregarPedido(pedido);	
		Empresa.getInstance().crearViaje(pedido, chofer, auto);
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
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getViajesTerminados().clear();
	}

}
