package testeo_gui.vistaAdmin.visualizarInfoAdmin;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JList;
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
import excepciones.ClienteSinViajePendienteException;
import excepciones.PasswordErroneaException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.TestUtils;
import util.Constantes;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;

public class GestionPedidos_pedidos_pendientes_vacia {
	Robot robot;
	Controlador controlador;
	Chofer chofer1;
	Chofer chofer2;
	Chofer chofer3;
	Chofer chofer4;
	Pedido pedido1;
	Pedido pedido2;
	Pedido pedido3;
	Vehiculo auto;
	Vehiculo moto;
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
		TestUtils.setDelay(100);
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		Empresa.getInstance().agregarCliente("pepe1", "123", "pepe cibrian");
		Empresa.getInstance().agregarCliente("tutu", "asdasd", "tulio paredes");
		HashMap<String,Cliente> clientes = Empresa.getInstance().getClientes();
		
		this.auto = new Auto("asd389",3,true);
		this.moto = new Moto("shak223");
		Empresa.getInstance().agregarVehiculo(auto);
		Empresa.getInstance().agregarVehiculo(moto);
		
		this.chofer1 = new ChoferTemporario("123123","checho perez");
		this.chofer2 = new ChoferTemporario("1598","leclerc");
		this.chofer3 = new ChoferPermanente("8323","marcelo",2020,3);
		this.chofer4 = new ChoferPermanente("7474","roberto",2019,8);
		Empresa.getInstance().agregarChofer(chofer1);
		Empresa.getInstance().agregarChofer(chofer2);
		Empresa.getInstance().agregarChofer(chofer3);
		Empresa.getInstance().agregarChofer(chofer4);

		robot.delay(TestUtils.getDelay());
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		Empresa.getInstance().logout();
		login_admin();
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
	}

	@Test
	public void pedidos_pendientes_vacia() {
		robot.delay(TestUtils.getDelay());
		JList<Pedido> lista_pedidos_pendientes = (JList<Pedido>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		assertEquals("la lista de pedidos pendientes debe estar vacia",0,lista_pedidos_pendientes.getModel().getSize());
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
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getViajesTerminados().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getChoferesDesocupados().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getClientes().clear();
	}
}
