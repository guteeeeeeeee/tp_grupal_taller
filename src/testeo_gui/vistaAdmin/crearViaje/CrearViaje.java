package testeo_gui.vistaAdmin.crearViaje;

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
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.TestUtils;
import util.Constantes;

public class CrearViaje {
	
	Robot robot;
	Controlador controlador;
	Pedido pedido1;
	Pedido pedido2;
	Pedido pedido3;
	Chofer chofer1;
	Chofer chofer2;
	Chofer chofer3;
	Chofer chofer4;
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
		Vehiculo combi_aux = new Combi("ccc111",10,true);
		Empresa.getInstance().agregarVehiculo(auto);
		Empresa.getInstance().agregarVehiculo(moto);
		Empresa.getInstance().agregarVehiculo(combi_aux);
		
		this.chofer1 = new ChoferTemporario("123123","checho perez");
		this.chofer2 = new ChoferTemporario("1598","leclerc");
		this.chofer3 = new ChoferPermanente("8323","marcelo",2020,3);
		this.chofer4 = new ChoferPermanente("7474","roberto",2019,8);
		Empresa.getInstance().agregarChofer(chofer1);
		Empresa.getInstance().agregarChofer(chofer2);
		Empresa.getInstance().agregarChofer(chofer3);
		Empresa.getInstance().agregarChofer(chofer4);
		
		this.pedido1 = new Pedido(clientes.get("pepe1"),1,false,false,5,Constantes.ZONA_SIN_ASFALTAR);
		this.pedido2 = new Pedido(clientes.get("tutu"),10,true,false,10,Constantes.ZONA_STANDARD);
		Empresa.getInstance().agregarPedido(pedido1);
		Empresa.getInstance().agregarPedido(pedido2);
		
		Empresa.getInstance().getVehiculosDesocupados().remove(combi_aux);
		
		robot.delay(TestUtils.getDelay());
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		Empresa.getInstance().logout();
		login_admin();
	}
	
	@Ignore
	@Test
	public void nuevo_viaje_todo_seleccionado() { //no hace click en pedidos pendientes
		robot.delay(TestUtils.getDelay());
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		JList lista_pedidos_pendientes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList lista_choferes_libres = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList lista_vehiculos_disponibles = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		JButton boton_nuevo_viaje = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());

		TestUtils.clickear_jlist(lista_pedidos_pendientes, 1, robot);
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());
		TestUtils.clickear_jlist(lista_choferes_libres, 0, robot);
		robot.delay(TestUtils.getDelay());
		TestUtils.clickear_jlist(lista_vehiculos_disponibles, 0, robot);
		assertTrue("el boton para iniciar nuevo viaje deberia estar disponible",boton_nuevo_viaje.isEnabled());
	}
	
	@Ignore
	@Test
	public void nuevo_viaje_sin_vehiculo_seleccionado() {
		robot.delay(TestUtils.getDelay());
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		JList lista_pedidos_pendientes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList lista_choferes_libres = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList lista_vehiculos_disponibles = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		JButton boton_nuevo_viaje = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());

		TestUtils.clickear_jlist(lista_pedidos_pendientes, 1, robot);
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());
		TestUtils.clickear_jlist(lista_choferes_libres, 0, robot);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
	}
	
	@Ignore
	@Test
	public void nuevo_viaje_sin_chofer_seleccionado() {
		robot.delay(TestUtils.getDelay());
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		JList lista_pedidos_pendientes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList lista_choferes_libres = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList lista_vehiculos_disponibles = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		JButton boton_nuevo_viaje = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());

		TestUtils.clickear_jlist(lista_pedidos_pendientes, 1, robot);
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());
		TestUtils.clickear_jlist(lista_choferes_libres, 0, robot);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
	}
	
	@Ignore
	@Test
	public void nuevo_viaje_ningun_vehiculo_satisface() {
		robot.delay(TestUtils.getDelay());
		JList lista_pedidos_pendientes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList lista_choferes_libres = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList lista_vehiculos_disponibles = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		JButton boton_nuevo_viaje = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());

		TestUtils.clickear_jlist(lista_pedidos_pendientes,0, robot);
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());
		TestUtils.clickear_jlist(lista_choferes_libres, 0, robot);
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(5000);
		robot.delay(TestUtils.getDelay());
		assertEquals(0,lista_vehiculos_disponibles.getModel().getSize());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
	}
	
	@Ignore
	@Test
	public void nuevo_viaje_vehiculo_satisface() {
		robot.delay(TestUtils.getDelay());
		JList lista_pedidos_pendientes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList lista_choferes_libres = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList lista_vehiculos_disponibles = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		JButton boton_nuevo_viaje = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());

		TestUtils.clickear_jlist(lista_pedidos_pendientes,1, robot);
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());
		TestUtils.clickear_jlist(lista_choferes_libres, 0, robot);
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(5000);
		robot.delay(TestUtils.getDelay());
		assertEquals(0,lista_vehiculos_disponibles.getModel().getSize());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
	}
	
	@Ignore
	@Test
	public void nuevo_viaje_vehiculo_satisface_genero_viaje() {
		robot.delay(TestUtils.getDelay());
		JList lista_pedidos_pendientes = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_PEDIDOS_PENDIENTES);
		JList lista_choferes_libres = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_LIBRES);
		JList lista_vehiculos_disponibles = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_DISPONIBLES);
		
		JButton boton_nuevo_viaje = (JButton) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.NUEVO_VIAJE);
		robot.delay(TestUtils.getDelay());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());

		TestUtils.clickear_jlist(lista_pedidos_pendientes,1, robot);
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());
		TestUtils.clickear_jlist(lista_choferes_libres, 0, robot);
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		robot.delay(TestUtils.getDelay());
		assertEquals(0,lista_vehiculos_disponibles.getModel().getSize());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());
		
		TestUtils.clickComponent(boton_nuevo_viaje, robot);
		robot.delay(TestUtils.getDelay());
		assertEquals("no actualiza la lista de pedidos pendientes",1,lista_pedidos_pendientes.getModel().getSize());
		assertEquals("no actualiza la lista de choferes libres",4,lista_choferes_libres.getModel().getSize());
		assertEquals("no vacia la lista de vehiculos disponibles",0,lista_vehiculos_disponibles.getModel().getSize());
		assertFalse("el boton para iniciar nuevo viaje no deberia estar disponible",boton_nuevo_viaje.isEnabled());

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
