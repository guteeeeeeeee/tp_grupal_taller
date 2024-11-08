package modelo_Negocios.Empresa.Getters_Setters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.plaf.BorderUIResource.EmptyBorderUIResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PasswordErroneaException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.*;
import modeloNegocio.Empresa;
import util.Constantes;

public class Empresa_getters_setters {

	@Test
	public void test_get_instance() {
		Empresa empresa1 = Empresa.getInstance();
		Empresa empresa2 = Empresa.getInstance();
		assertEquals(empresa1,empresa2);
	}
	
	@Test
	public void get_clientes() {
		assertEquals(0,Empresa.getInstance().getClientes().size());
	}
	
	@Test
	public void set_clientes() throws UsuarioYaExisteException {
		String nombre_nuevo = "rasputin123";
		Cliente cliente_nuevo = new Cliente(nombre_nuevo,"contra","rasputin");
		assertEquals(0,Empresa.getInstance().getClientes().size());
		HashMap<String,Cliente> clientes = new HashMap<String,Cliente>();
		clientes.put(nombre_nuevo,cliente_nuevo);
		Empresa.getInstance().setClientes(clientes);
		assertEquals(1,Empresa.getInstance().getClientes().size());
		assertNotNull(Empresa.getInstance().getClientes().get(nombre_nuevo));
	}
	
	@Test
	public void iterator_clientes() throws UsuarioYaExisteException {
		String nombre_nuevo = "rasputin123";
		
		Iterator<Cliente> it_cliente = Empresa.getInstance().iteratorClientes();
		assertFalse("el iterator deberia estar vacio",it_cliente.hasNext());
		Empresa.getInstance().agregarCliente(nombre_nuevo, "contra", "rasputin");
		Cliente cliente = Empresa.getInstance().iteratorClientes().next();
		assertEquals(nombre_nuevo,cliente.getNombreUsuario());
	}
	
	@Test
	public void get_choferes() {
		assertEquals(0,Empresa.getInstance().getChoferes().size());
	}
	
	@Test
	public void set_choferes() {
		String dni_nuevo = "678";
		Chofer chofer_nuevo = new ChoferTemporario(dni_nuevo,"jorge");
		assertEquals(0,Empresa.getInstance().getChoferes().size());
		HashMap<String,Chofer> choferes = new HashMap<String,Chofer>();
		choferes.put(dni_nuevo,chofer_nuevo);
		Empresa.getInstance().setChoferes(choferes);
		assertEquals(1,Empresa.getInstance().getChoferes().size());
		assertNotNull(Empresa.getInstance().getChoferes().get(dni_nuevo));
	}
	
	@Test
	public void get_choferes_desocupados() {
		assertEquals(0,Empresa.getInstance().getChoferesDesocupados().size());
	}
	
	@Test
	public void set_choferes_desocupados() {
		String dni_nuevo = "678";
		Chofer chofer_nuevo = new ChoferTemporario(dni_nuevo,"jorge");
		assertEquals(0,Empresa.getInstance().getChoferesDesocupados().size());
		ArrayList<Chofer> choferes = new ArrayList<Chofer>();
		choferes.add(chofer_nuevo);
		Empresa.getInstance().setChoferesDesocupados(choferes);
		assertEquals(1,Empresa.getInstance().getChoferesDesocupados().size());
		assertEquals(chofer_nuevo,Empresa.getInstance().getChoferesDesocupados().getLast());
	}

	@Test
	public void iterator_choferes() throws ChoferRepetidoException {
		String dni_nuevo = "678";
		Chofer chofer_nuevo = new ChoferTemporario(dni_nuevo,"jorge");
		Iterator<Chofer> it_choferes = Empresa.getInstance().iteratorChoferes();
		assertFalse("el iterator deberia estar vacio",it_choferes.hasNext());
		Empresa.getInstance().agregarChofer(chofer_nuevo);
		it_choferes = Empresa.getInstance().iteratorChoferes();
		Chofer chofer = it_choferes.next();
		assertEquals(dni_nuevo,chofer.getDni());
	}
	
	@Test
	public void get_vehiculos() {
		assertEquals(0,Empresa.getInstance().getVehiculos().size());
	}
	
	@Test
	public void set_vehiculos() {
		String patente_nuevo = "678";
		Vehiculo auto_nuevo = new Auto(patente_nuevo,3,true);
		assertEquals(0,Empresa.getInstance().getVehiculos().size());
		HashMap<String,Vehiculo> vehiculos = new HashMap<String,Vehiculo>();
		vehiculos.put(patente_nuevo,auto_nuevo);
		Empresa.getInstance().setVehiculos(vehiculos);
		assertEquals(1,Empresa.getInstance().getVehiculos().size());
		assertNotNull(Empresa.getInstance().getVehiculos().get(patente_nuevo));
	}

	@Test
	public void get_vehiculos_desocupados() {
		assertEquals(0,Empresa.getInstance().getVehiculosDesocupados().size());
	}
	
	@Test
	public void set_vehiculos_desocupados() {
		String patente_nuevo = "678";
		Vehiculo auto_nuevo = new Auto(patente_nuevo,3,true);
		assertEquals(0,Empresa.getInstance().getVehiculosDesocupados().size());
		ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		vehiculos.add(auto_nuevo);
		Empresa.getInstance().setVehiculosDesocupados(vehiculos);
		assertEquals(1,Empresa.getInstance().getVehiculosDesocupados().size());
		assertEquals(patente_nuevo,Empresa.getInstance().getVehiculosDesocupados().getLast().getPatente());
	}
	
	@Test
	public void iterator_vehiculos() throws VehiculoRepetidoException {
		String patente_nuevo = "678";
		Vehiculo auto_nuevo = new Auto(patente_nuevo,3,true);
		Iterator<Vehiculo> it_vehiculos = Empresa.getInstance().iteratorVehiculos();
		assertFalse("el iterator deberia estar vacio",it_vehiculos.hasNext());
		Empresa.getInstance().agregarVehiculo(auto_nuevo);
		it_vehiculos = Empresa.getInstance().iteratorVehiculos();
		Vehiculo vehiculo = it_vehiculos.next();
		assertEquals(patente_nuevo,vehiculo.getPatente());
	}
	
	@Test
	public void get_pedidos() {
		assertEquals(0,Empresa.getInstance().getPedidos().size());
	}
	
	@Test
	public void set_pedidos() {
		Cliente cliente_pedido = new Cliente("babadad","123","raul");
		Pedido pedido = new Pedido(cliente_pedido,3,true,false,5,Constantes.ZONA_PELIGROSA);
		assertEquals(0,Empresa.getInstance().getPedidos().size());
		HashMap<Cliente,Pedido> pedidos = new HashMap<Cliente,Pedido>();
		pedidos.put(cliente_pedido,pedido);
		Empresa.getInstance().setPedidos(pedidos);
		assertEquals(1,Empresa.getInstance().getPedidos().size());
		assertEquals(pedido,Empresa.getInstance().getPedidos().get(cliente_pedido));
	}
	
	@Test
	public void iterator_pedidos() throws ChoferRepetidoException {
		Cliente cliente_pedido = new Cliente("babadad","123","raul");
		Pedido pedido = new Pedido(cliente_pedido,3,true,false,5,Constantes.ZONA_PELIGROSA);
		Iterator<Pedido> it_pedidos = Empresa.getInstance().iteratorPedidos();
		assertFalse("el iterator deberia estar vacio",it_pedidos.hasNext());
		HashMap<Cliente,Pedido> pedidos = new HashMap<Cliente,Pedido>();
		pedidos.put(cliente_pedido,pedido);
		Empresa.getInstance().setPedidos(pedidos);
		it_pedidos = Empresa.getInstance().iteratorPedidos();
		Pedido pedido_sacado = it_pedidos.next();
		assertEquals(cliente_pedido,pedido_sacado.getCliente());
	}
	
	@Test
	public void get_viajes_iniciados() {
		assertEquals(0,Empresa.getInstance().getViajesIniciados().size());
	}
	
	@Test
	public void set_viajes_iniciados() {
		Cliente cliente_viaje = new Cliente("babadad","123","raul");
		Pedido pedido = new Pedido(cliente_viaje,3,true,false,5,Constantes.ZONA_PELIGROSA);
		String dni_chofer = "747474";
		Chofer chofer = new ChoferTemporario(dni_chofer,"jorge");
		String patente_auto = "ooero1231";
		Vehiculo auto = new Auto(patente_auto,4,true);
		Viaje viaje = new Viaje(pedido,chofer,auto);
		assertEquals(0,Empresa.getInstance().getViajesIniciados().size());
		HashMap<Cliente,Viaje> viajes = new HashMap<Cliente,Viaje>();
		viajes.put(cliente_viaje,viaje);
		Empresa.getInstance().setViajesIniciados(viajes);
		assertEquals(1,Empresa.getInstance().getViajesIniciados().size());
		assertEquals(viaje,Empresa.getInstance().getViajesIniciados().get(cliente_viaje));
	}
	
	@Test
	public void iterator_viajes_iniciados() {
		Cliente cliente_viaje = new Cliente("babadad","123","raul");
		Pedido pedido = new Pedido(cliente_viaje,3,true,false,5,Constantes.ZONA_PELIGROSA);
		String dni_chofer = "747474";
		Chofer chofer = new ChoferTemporario(dni_chofer,"jorge");
		String patente_auto = "ooero1231";
		Vehiculo auto = new Auto(patente_auto,4,true);
		Viaje viaje = new Viaje(pedido,chofer,auto);
		Iterator<Viaje> it_viaje = Empresa.getInstance().iteratorViajesIniciados();
		assertFalse("el iterator deberia estar vacio",it_viaje.hasNext());
		HashMap<Cliente,Viaje> viajes = new HashMap<Cliente,Viaje>();
		viajes.put(cliente_viaje,viaje);
		Empresa.getInstance().setViajesIniciados(viajes);
		Viaje viaje_del_it = Empresa.getInstance().iteratorViajesIniciados().next();
		assertEquals(viaje,viaje_del_it);
	}
	
	@Test
	public void get_viajes_terminados() {
		assertEquals(0,Empresa.getInstance().getViajesTerminados().size());
	}
	
	@Test
	public void set_viajes_terminados() {
		Cliente cliente_viaje = new Cliente("babadad","123","raul");
		Pedido pedido = new Pedido(cliente_viaje,3,true,false,5,Constantes.ZONA_PELIGROSA);
		String dni_chofer = "747474";
		Chofer chofer = new ChoferTemporario(dni_chofer,"jorge");
		String patente_auto = "ooero1231";
		Vehiculo auto = new Auto(patente_auto,4,true);
		Viaje viaje = new Viaje(pedido,chofer,auto);
		assertEquals(0,Empresa.getInstance().getViajesTerminados().size());
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		viajes.add(viaje);
		Empresa.getInstance().setViajesTerminados(viajes);
		assertEquals(1,Empresa.getInstance().getViajesTerminados().size());
		assertEquals(viaje,Empresa.getInstance().getViajesTerminados().getLast());
	}
	
	/*
	@Test
	public void iterator_viajes_terminados() {
		Cliente cliente_viaje = new Cliente("babadad","123","raul");
		Pedido pedido = new Pedido(cliente_viaje,3,true,false,5,"ZONA_PELIGROSA");
		String dni_chofer = "747474";
		Chofer chofer = new ChoferTemporario(dni_chofer,"jorge");
		String patente_auto = "ooero1231";
		Vehiculo auto = new Auto(patente_auto,4,true);
		Viaje viaje = new Viaje(pedido,chofer,auto);
		Iterator<Viaje> it_viaje = Empresa.getInstance().iteratorViajesTerminados(); //el iterator es un arraylist
		assertFalse("el iterator deberia estar vacio",it_viaje.hasNext());
		HashMap<Cliente,Viaje> viajes = new HashMap<Cliente,Viaje>();
		viajes.put(cliente_viaje,viaje);
		Empresa.getInstance().setViajesTerminados(viajes);
		Viaje viaje_del_it = Empresa.getInstance().iteratorViajesTerminados().next();
		assertEquals(viaje,viaje_del_it);
	}*/
	
	@Test
	public void get_pedido_cliente() throws UsuarioYaExisteException, UsuarioNoExisteException, PasswordErroneaException, ChoferRepetidoException, VehiculoRepetidoException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException {
		String username = "cazador23";
		String password = "ccc";
		Empresa.getInstance().agregarCliente(username, password, "dario");
		Cliente user_logeado = (Cliente) Empresa.getInstance().login(username, password);
		Pedido pedido = new Pedido(user_logeado,3,true,false,5,Constantes.ZONA_PELIGROSA);
		Chofer chofer = new ChoferTemporario("737373","jorge");
		Empresa.getInstance().agregarChofer(chofer);
		Vehiculo auto = new Auto("aaa111",4,true);
		Empresa.getInstance().agregarVehiculo(auto);
		assertNull(Empresa.getInstance().getPedidoDeCliente(user_logeado));
		Empresa.getInstance().agregarPedido(pedido);
		assertNotNull(Empresa.getInstance().getPedidoDeCliente(user_logeado));
	}
	
	@Test
	public void get_pedido_viaje() throws UsuarioYaExisteException, UsuarioNoExisteException, PasswordErroneaException, ChoferRepetidoException, VehiculoRepetidoException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException, PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException {
		String username = "cazador23";
		String password = "ccc";
		Empresa.getInstance().agregarCliente(username, password, "dario");
		Cliente user_logeado = (Cliente) Empresa.getInstance().login(username, password);
		Pedido pedido = new Pedido(user_logeado,3,true,false,5,Constantes.ZONA_PELIGROSA);
		Chofer chofer = new ChoferTemporario("737373","jorge");
		Empresa.getInstance().agregarChofer(chofer);
		Vehiculo auto = new Auto("aaa111",4,true);
		Empresa.getInstance().agregarVehiculo(auto);
		Empresa.getInstance().agregarPedido(pedido);
		assertNull(Empresa.getInstance().getViajeDeCliente(user_logeado));
		Empresa.getInstance().crearViaje(pedido, chofer, auto);
		assertNotNull(Empresa.getInstance().getViajeDeCliente(user_logeado));
	}
	
	@Test
	public void get_user_logeado() throws UsuarioYaExisteException, UsuarioNoExisteException, PasswordErroneaException {
		String username = "cazador23";
		String password = "ccc";
		Empresa.getInstance().agregarCliente(username, password, "dario");
		assertNull(Empresa.getInstance().getUsuarioLogeado());
		Cliente user_logeado = (Cliente) Empresa.getInstance().login(username,password);
		assertEquals(username,user_logeado.getNombreUsuario());
		assertEquals(password,user_logeado.getPass());
	}
	
	@Test
	public void set_user_logeado() throws UsuarioYaExisteException, UsuarioNoExisteException, PasswordErroneaException {
		String username_viejo = "cazador23";
		String password_viejo = "ccc";
		String username_nuevo = "lucas22";
		String password_nuevo = "lukkk";
		Empresa.getInstance().agregarCliente(username_viejo, password_viejo, "dario");
		Cliente user_logeado = (Cliente) Empresa.getInstance().login(username_viejo,password_viejo);
		assertEquals(username_viejo,user_logeado.getNombreUsuario());
		assertEquals(password_viejo,user_logeado.getPass());
		Cliente user_nuevo = new Cliente(username_nuevo,password_nuevo,"lucas");
		Empresa.getInstance().setUsuarioLogeado(user_nuevo);
		user_logeado = (Cliente) Empresa.getInstance().getUsuarioLogeado();
		assertEquals(username_nuevo,user_logeado.getNombreUsuario());
		assertEquals(password_nuevo,user_logeado.getPass());
	}
	
	@Test
	public void is_admin() throws UsuarioYaExisteException, UsuarioNoExisteException, PasswordErroneaException {
		String username = "cazador23";
		String password = "ccc";
		Empresa.getInstance().agregarCliente(username, password, "dario");
		Empresa.getInstance().login(username,password);
		assertFalse(Empresa.getInstance().isAdmin());
		Empresa.getInstance().logout();
		Empresa.getInstance().login("admin","admin");
		assertTrue(Empresa.getInstance().isAdmin());
	}
	
	
	@After
	public void limpiar() {
		Empresa.getInstance().logout();
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getVehiculosDesocupados().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getChoferesDesocupados().clear();
		Empresa.getInstance().getPedidos().clear();
		Empresa.getInstance().getViajesIniciados().clear();
		Empresa.getInstance().getViajesTerminados().clear();
	}
}
