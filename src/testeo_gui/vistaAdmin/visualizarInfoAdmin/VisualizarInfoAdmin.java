package testeo_gui.vistaAdmin.visualizarInfoAdmin;

import static org.junit.Assert.*;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.util.ArrayList;

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
import modeloDatos.Moto;
import modeloDatos.Auto;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.TestUtils;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import util.Constantes;
import util.Mensajes;
import modeloDatos.Viaje;


public class VisualizarInfoAdmin {

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
	Cliente cliente_logeado;
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
		TestUtils.setDelay(100);
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		//login_admin();
	}
	
	@Test
	public void sin_chofer_seleccionado() {
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList lista_viajes_chofer = (JList) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_DE_CHOFER);
		JTextField calificacion_chofer = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_CHOFER);
		JTextField sueldo_chofer = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.SUELDO_DE_CHOFER);
		
		assertEquals("la lista de viajes del chofer deberia esta vacia",0,lista_viajes_chofer.getModel().getSize());
		assertTrue("la calificacion del chofer debe esta vacia",calificacion_chofer.getText().isEmpty());
		assertTrue("el sueldo del chofer debe esta vacio",sueldo_chofer.getText().isEmpty());
	}

	@Test
	public void con_chofer_seleccionado_sin_viajes_realizados() throws ChoferRepetidoException {
		agregar_choferes();
		robot.delay(TestUtils.getDelay());
		login_admin();
		robot.delay(TestUtils.getDelay());
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		JList<Chofer> lista_choferes_totales = (JList<Chofer>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);

		JList<Viaje> lista_viajes_chofer = (JList<Viaje>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_DE_CHOFER);
		JTextField calificacion_chofer = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_CHOFER);
		JTextField sueldo_chofer = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.SUELDO_DE_CHOFER);
		
		assertEquals("la cantidad de choferes debe ser 4",4,lista_choferes_totales.getModel().getSize());
		robot.delay(TestUtils.getDelay());
		TestUtils.clickear_jlist(lista_choferes_totales,1,robot);
		
		robot.delay(TestUtils.getDelay());
		assertEquals("la lista de viajes del chofer deberia esta vacia",0,lista_viajes_chofer.getModel().getSize());
		assertEquals("la calificacion del chofer debe ser 0.0",0.0,Double.parseDouble(calificacion_chofer.getText()),0.1);
		assertEquals("el sueldo del chofer no es el correcto",this.chofer1.getSueldoNeto(),Double.parseDouble(sueldo_chofer.getText()),1.0);
	}

	@Test
	public void con_chofer_seleccionado_con_viajes_realizados() throws ChoferRepetidoException, UsuarioYaExisteException, UsuarioNoExisteException, PasswordErroneaException, VehiculoRepetidoException, PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException, ClienteConViajePendienteException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConPedidoPendienteException, ClienteSinViajePendienteException {
		robot.delay(TestUtils.getDelay());
		agregar_choferes();
		robot.delay(TestUtils.getDelay());
		agregar_viajes();
		robot.delay(TestUtils.getDelay());
		this.controlador.getVista().actualizar();
		robot.delay(TestUtils.getDelay());
		login_admin();
		robot.delay(TestUtils.getDelay());
		
		JList<Chofer> lista_choferes_totales = (JList<Chofer>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_CHOFERES_TOTALES);

		JList<Viaje> lista_viajes_chofer = (JList<Viaje>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_DE_CHOFER);
		JTextField calificacion_chofer = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.CALIFICACION_CHOFER);
		JTextField sueldo_chofer = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.SUELDO_DE_CHOFER);
		
		robot.delay(TestUtils.getDelay());
		TestUtils.clickear_jlist(lista_choferes_totales,3,robot);
		
		robot.delay(TestUtils.getDelay());
		assertEquals("la lista de viajes del chofer debe contener 3 viajes",3,lista_viajes_chofer.getModel().getSize());
		assertEquals("el chofer es incorrecto",this.chofer3,lista_viajes_chofer.getModel().getElementAt(0).getChofer());
		assertEquals("el chofer es incorrecto",this.moto,lista_viajes_chofer.getModel().getElementAt(0).getVehiculo());
		assertEquals("el chofer es incorrecto",this.chofer3,lista_viajes_chofer.getModel().getElementAt(1).getChofer());
		assertEquals("el chofer es incorrecto",this.auto,lista_viajes_chofer.getModel().getElementAt(1).getVehiculo());
		assertEquals("el chofer es incorrecto",this.chofer3,lista_viajes_chofer.getModel().getElementAt(2).getChofer());
		assertEquals("el chofer es incorrecto",this.auto,lista_viajes_chofer.getModel().getElementAt(2).getVehiculo());
		assertNotEquals("no muestra correctamente la puntuacion del chofer",0.0,Double.parseDouble(calificacion_chofer.getText()),0.1);
		assertEquals("no muestra correctamente el sueldo del chofer",this.chofer3.getSueldoNeto(),Double.parseDouble(sueldo_chofer.getText()),1.0);
	}

	@Test
	public void listado_clientes_vacio() {
		robot.delay(TestUtils.getDelay());
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Cliente> listado_clientes = (JList<Cliente>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTADO_DE_CLIENTES);
		assertEquals("el listado debe estar vacio",0,listado_clientes.getModel().getSize());
	}
	
	@Test
	public void listado_clientes_no_vacio() throws UsuarioYaExisteException {
		robot.delay(TestUtils.getDelay());
		agregar_clientes();
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Cliente> listado_clientes = (JList<Cliente>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTADO_DE_CLIENTES);
		assertEquals("el listado debe contener los 3 clientes registrados",3,listado_clientes.getModel().getSize());
		//no estan ordenados como son ingresados
		/*assertEquals("el nombre de usuario no coincide","dasdas",listado_clientes.getModel().getElementAt(0).getNombreUsuario());
		assertEquals("la password no coincide","214231",listado_clientes.getModel().getElementAt(0).getPass());
		assertEquals("el nombre de usuario no coincide","iweudas",listado_clientes.getModel().getElementAt(1).getNombreUsuario());
		assertEquals("la password no coincide","1232",listado_clientes.getModel().getElementAt(1).getPass());
		assertEquals("el nombre de usuario no coincide","patriciorey",listado_clientes.getModel().getElementAt(2).getNombreUsuario());
		assertEquals("la password no coincide","rrpp",listado_clientes.getModel().getElementAt(2).getPass());*/
	}
	
	@Test
	public void listado_vehiculos_vacio() {
		robot.delay(TestUtils.getDelay());
		//agregar_vehiculos();
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Vehiculo> listado_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		assertEquals("el listado debe estar vacio",0,listado_vehiculos.getModel().getSize());
	}
	
	@Test
	public void listado_vehiculos_no_vacio() throws VehiculoRepetidoException {
		robot.delay(TestUtils.getDelay());
		agregar_vehiculos();
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Vehiculo> listado_vehiculos = (JList<Vehiculo>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VEHICULOS_TOTALES);
		assertEquals("el listado debe contener los 2 vehiculos registrados",2,listado_vehiculos.getModel().getSize());
		assertEquals("no coincide la patente del moto",this.moto.getPatente(),listado_vehiculos.getModel().getElementAt(0).getPatente());
		assertEquals("no coincide la patente del auto",this.auto.getPatente(),listado_vehiculos.getModel().getElementAt(1).getPatente());
	}
	
	@Test
	public void listado_viajes_vacio() {
		robot.delay(TestUtils.getDelay());
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Viaje> listado_viajes = (JList<Viaje>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_HISTORICOS);
		assertEquals("el listado de viajes debe estar vacio",0,listado_viajes.getModel().getSize());
	}

	@Test
	public void listado_viajes_no_vacio() throws UsuarioYaExisteException, ChoferRepetidoException, UsuarioNoExisteException, PasswordErroneaException, VehiculoRepetidoException, PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException, ClienteConViajePendienteException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConPedidoPendienteException, ClienteSinViajePendienteException {
		robot.delay(TestUtils.getDelay());
		agregar_viajes();
		login_admin();
		robot.delay(TestUtils.getDelay());
		JList<Viaje> listado_viajes = (JList<Viaje>) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.LISTA_VIAJES_HISTORICOS);
		assertEquals("el listado debe contener los 3 viajes registrados",3,listado_viajes.getModel().getSize());
	}
	
	@Test
	public void sueldos_a_pagar_sin_choferes() {
		robot.delay(TestUtils.getDelay());
		login_admin();
		robot.delay(TestUtils.getDelay());
		JTextField sueldos_a_pagar = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.TOTAL_SUELDOS_A_PAGAR);
		assertEquals("la cantida de sueldos a pagar debe ser 0",0,Double.parseDouble(sueldos_a_pagar.getText()),0.1);
	}
	
	@Test
	public void sueldos_a_pagar_con_choferes() throws ChoferRepetidoException {
		robot.delay(TestUtils.getDelay());
		agregar_choferes();
		login_admin();
		robot.delay(TestUtils.getDelay());
		JTextField sueldos_a_pagar = (JTextField) TestUtils.getComponentForName((Component) controlador.getVista(), Constantes.TOTAL_SUELDOS_A_PAGAR);
		assertNotEquals("la cantida de sueldos a pagar debe ser distinto de 0",0,Double.parseDouble(sueldos_a_pagar.getText()),0.1);
	}
	
	public void agregar_vehiculos() throws VehiculoRepetidoException {
		this.auto = new Auto("asd389",3,true);
		this.moto = new Moto("shak223");
		Empresa.getInstance().agregarVehiculo(auto);
		Empresa.getInstance().agregarVehiculo(moto);
	}
	
	public void agregar_choferes() throws ChoferRepetidoException {
		this.chofer1 = new ChoferTemporario("123123","checho perez");
		this.chofer2 = new ChoferTemporario("1598","leclerc");
		this.chofer3 = new ChoferPermanente("8323","marcelo",2020,3);
		this.chofer4 = new ChoferPermanente("7474","roberto",2019,8);
		Empresa.getInstance().agregarChofer(chofer1);
		Empresa.getInstance().agregarChofer(chofer2);
		Empresa.getInstance().agregarChofer(chofer3);
		Empresa.getInstance().agregarChofer(chofer4);
	}
	
	public void agregar_clientes() throws UsuarioYaExisteException {
		Empresa.getInstance().agregarCliente("patriciorey", "rrpp", "carlos alberto");
		Empresa.getInstance().agregarCliente("iweudas", "1232", "gustavo");
		Empresa.getInstance().agregarCliente("dasdas", "214231", "elias gomez");
	}
	
	public void agregar_viajes() throws UsuarioYaExisteException, ChoferRepetidoException, UsuarioNoExisteException, PasswordErroneaException, VehiculoRepetidoException, PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException, ClienteConViajePendienteException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConPedidoPendienteException, ClienteSinViajePendienteException {
		Empresa.getInstance().agregarCliente("pepe1", "123", "pepe cibrian");
		Empresa.getInstance().login("pepe1", "123");
		
		this.cliente_logeado = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		agregar_choferes();
		agregar_vehiculos();
		
		this.pedido1 = new Pedido(this.cliente_logeado,1,false,false,5,Constantes.ZONA_SIN_ASFALTAR);
		this.pedido2 = new Pedido(this.cliente_logeado,2,true,false,10,Constantes.ZONA_STANDARD);
		this.pedido3 = new Pedido(this.cliente_logeado,3,true,true,7,Constantes.ZONA_PELIGROSA);
		
		Empresa.getInstance().agregarPedido(pedido1);
		Empresa.getInstance().crearViaje(pedido1, this.chofer3, moto);
		Empresa.getInstance().pagarYFinalizarViaje(3);
		Empresa.getInstance().agregarPedido(pedido2);
		Empresa.getInstance().crearViaje(pedido2, this.chofer3, auto);
		Empresa.getInstance().pagarYFinalizarViaje(4);
		Empresa.getInstance().agregarPedido(pedido3);
		Empresa.getInstance().crearViaje(pedido3, this.chofer3, auto);
		Empresa.getInstance().pagarYFinalizarViaje(2);

		Empresa.getInstance().logout();
		robot.delay(TestUtils.getDelay());
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
