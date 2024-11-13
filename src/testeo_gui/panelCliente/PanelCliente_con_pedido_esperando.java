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
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.TestUtils;
import util.Constantes;

public class PanelCliente_con_pedido_esperando {
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
		
		Vehiculo auto = new Auto("fpp645",4,true);
		Empresa.getInstance().agregarVehiculo(auto);
		Chofer chofer = new ChoferTemporario("213213","fernando");
		Empresa.getInstance().agregarChofer(chofer);
		Cliente cliente_logeado = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		Pedido pedido = new Pedido(cliente_logeado,3,false,false,5,"ZONA_STANDARD");
		Empresa.getInstance().agregarPedido(pedido);
		controlador.getVista().actualizar();
	}
	
	@Test
	public void test_pedido_esperando() throws VehiculoRepetidoException, ChoferRepetidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException, SinVehiculoParaPedidoException {	
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
		Empresa.getInstance().logout();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getViajesTerminados().clear();
	}
}
