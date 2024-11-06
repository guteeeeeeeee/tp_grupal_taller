package testeo_gui;

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
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;

public class GestionPedidos {
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
	}

	@Ignore
	@Test
	public void pedidos_pendientes_vacia() {
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Pedido> lista_pedidos_pendientes = (JList<Pedido>) TestUtils.getComponentForName((Component) controlador.getVista(), "LISTA_PEDIDOS_PENDIENTES");
		assertEquals("la lista de pedidos pendientes debe estar vacia",0,lista_pedidos_pendientes.getModel().getSize());
	}
	
	@Ignore
	@Test
	public void pedidos_pendientes_no_vacia() {
		agregar_pedidos();
		robot.delay(TestUtils.getDelay());
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Pedido> lista_pedidos_pendientes = (JList<Pedido>) TestUtils.getComponentForName((Component) controlador.getVista(), "LISTA_PEDIDOS_PENDIENTES");
		assertEquals("la lista de pedidos pendientes debe tener los 2 pedidos pendientes",2,lista_pedidos_pendientes.getModel().getSize());
		assertEquals("el cliente del pedido pendiente no coincide",this.pedido1.getCliente(),lista_pedidos_pendientes.getModel().getElementAt(0).getCliente());
		assertEquals("la cant de pasajeros del pedido pendiente no coincide",this.pedido1.getCantidadPasajeros(),lista_pedidos_pendientes.getModel().getElementAt(0).getCantidadPasajeros());
		assertEquals("no coincide la cant de km del pedido pendiente",this.pedido1.getKm(),lista_pedidos_pendientes.getModel().getElementAt(0).getKm());
		assertEquals("no coincide la zona del pedido pendiente",this.pedido1.getZona(),lista_pedidos_pendientes.getModel().getElementAt(0).getZona());
	}
	
	@Ignore
	@Test
	public void lista_choferes_libres_vacia() {
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Chofer> lista_choferes_libres = (JList<Chofer>) TestUtils.getComponentForName((Component) controlador.getVista(), "LISTA_CHOFERES_LIBRES");
		assertEquals("la cantidad de choferes libres debe ser 0",0,lista_choferes_libres.getModel().getSize());
	}
	
	@Ignore
	@Test
	public void lista_choferes_libres_no_vacia() {
		agregar_choferes();
		robot.delay(TestUtils.getDelay());
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Chofer> lista_choferes_libres = (JList<Chofer>) TestUtils.getComponentForName((Component) controlador.getVista(), "LISTA_CHOFERES_LIBRES");
		assertEquals("la cantidad de choferes libres debe ser 4",4,lista_choferes_libres.getModel().getSize());
	}
	
	@Test
	public void nuevo_viaje_habilitado() {
		robot.delay(TestUtils.getDelay());
		agregar_pedidos();
		robot.delay(TestUtils.getDelay());
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Pedido> lista_pedidos_pendientes = (JList<Pedido>) TestUtils.getComponentForName((Component) controlador.getVista(), "LISTA_PEDIDOS_PENDIENTES");
		//JList<Chofer> lista_choferes_libres = (JList<Chofer>) TestUtils.getComponentForName((Component) controlador.getVista(), "LISTA_CHOFERES_LIBRES");
		//JList<Chofer> lista_choferes_totales = (JList<Chofer>) TestUtils.getComponentForName((Component) controlador.getVista(), "LISTA_CHOFERES_TOTALES");

//		JList<Vehiculo> lista_vehiculos_disponibles = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), "LISTA_VEHICULOS_DISPONIBLES");
//		JButton boton_nuevo_viaje = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), "NUEVO_VIAJE");
//		robot.delay(TestUtils.getDelay());
//		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());

		//TestUtils.clickear_jlist(lista_pedidos_pendientes, 1, robot);
//		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
//		robot.delay(TestUtils.getDelay());
		//TestUtils.clickear_jlist(lista_choferes_totales, 0, robot);
		//TestUtils.clickear_jlist(lista_choferes_libres, 0, robot);
//		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(20000);
//		robot.delay(TestUtils.getDelay());
//		TestUtils.clickear_jlist(lista_vehiculos_disponibles, 0, robot);
//		assertTrue("el boton para iniciar nuevo viaje deberia estar disponible",boton_nuevo_viaje.isEnabled());
	}
	
	public void agregar_vehiculos() {
		this.auto = new Auto("asd389",3,true);
		this.moto = new Moto("shak223");
		try {
			Empresa.getInstance().agregarVehiculo(auto);
			Empresa.getInstance().agregarVehiculo(moto);
		} catch (VehiculoRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void agregar_choferes() {
		this.chofer1 = new ChoferTemporario("123123","checho perez");
		this.chofer2 = new ChoferTemporario("1598","leclerc");
		this.chofer3 = new ChoferPermanente("8323","marcelo",2020,3);
		this.chofer4 = new ChoferPermanente("7474","roberto",2019,8);
		try {
			Empresa.getInstance().agregarChofer(chofer1);
			Empresa.getInstance().agregarChofer(chofer2);
			Empresa.getInstance().agregarChofer(chofer3);
			Empresa.getInstance().agregarChofer(chofer4);
		} catch (ChoferRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void agregar_pedidos() {
		try {
			Empresa.getInstance().agregarCliente("pepe1", "123", "pepe cibrian");
			Empresa.getInstance().agregarCliente("tutu", "asdasd", "tulio paredes");
		} catch (UsuarioYaExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String,Cliente> clientes = Empresa.getInstance().getClientes();
		
		agregar_choferes();
		agregar_vehiculos();
		
		this.pedido1 = new Pedido(clientes.get("pepe1"),1,false,false,5,"ZONA_SIN_ASFALTAR");
		this.pedido2 = new Pedido(clientes.get("tutu"),2,true,false,10,"ZONA_STANDARD");
		try {
			Empresa.getInstance().agregarPedido(pedido1);
			Empresa.getInstance().agregarPedido(pedido2);
		} catch (ClienteConViajePendienteException | SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConPedidoPendienteException e) {
			e.printStackTrace();
		}
		Empresa.getInstance().logout();
		robot.delay(TestUtils.getDelay());
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
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getClientes().clear();
	}
}
