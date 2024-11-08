package controlador.NuevoPedido;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.Auto;
import modeloDatos.Moto;
import modeloNegocio.Empresa;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import testeo_gui.FalsoOptionPane;
import vista.IVista;
import util.Constantes;
import util.Mensajes;
import vista.Ventana;

public class NuevoPedido_en_viaje {

	Controlador controlador;
	IVista vista_mock;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		Vehiculo auto = new Auto("aaa111",3,true);
		Empresa.getInstance().agregarVehiculo(auto);
		Moto moto = new Moto("mmm111");
		Empresa.getInstance().agregarVehiculo(moto);
		
		Chofer chofer = new ChoferTemporario("777","chofer temporario");
		Empresa.getInstance().agregarChofer(chofer);
		
		Empresa.getInstance().agregarCliente("pepe123", "123", "pepe");
		Empresa.getInstance().agregarCliente("alfa", "123", "alfa");
		Empresa.getInstance().agregarCliente("viajante3", "123", "viajante");
		
		Cliente viajante = (Cliente) Empresa.getInstance().login("alfa", "123");
		Empresa.getInstance().logout();
		
		Pedido pedido = new Pedido(viajante,1,false,false,5,Constantes.ZONA_PELIGROSA);
		Empresa.getInstance().agregarPedido(pedido);
		
		Empresa.getInstance().crearViaje(pedido, chofer, moto);
		
		this.vista_mock = mock(Ventana.class);
		
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(this.vista_mock.getOptionPane()).thenReturn(op);
	}
	
	@Test
	public void agregar_pedido_cliente_viaje_pendiente() throws UsuarioNoExisteException, PasswordErroneaException {
		int cant_pax = 2;
		boolean mascota = false;
		boolean baul = false;
		int cant_km = 2;
		String tipo_zona = "ZONA_PELIGROSA";
		
		Cliente usuario_actual = (Cliente) Empresa.getInstance().login("alfa", "123");
		
		when(this.vista_mock.getCantidadPax()).thenReturn(cant_pax);
		when(this.vista_mock.isPedidoConMascota()).thenReturn(mascota);
		when(this.vista_mock.isPedidoConBaul()).thenReturn(baul);
		when(this.vista_mock.getCantKm()).thenReturn(cant_km);
		when(this.vista_mock.getTipoZona()).thenReturn(tipo_zona);
		
		assertEquals(0,Empresa.getInstance().getPedidos().size());
		assertNotNull(Empresa.getInstance().getViajeDeCliente(usuario_actual));
		this.controlador.nuevoPedido();
		assertEquals("no dice que el Cliente tiene viaje pendiente",Mensajes.CLIENTE_CON_VIAJE_PENDIENTE.getValor(),this.op.getMensaje());
		assertEquals(0,Empresa.getInstance().getPedidos().size());
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().getClientes().clear();
		Empresa.getInstance().getChoferes().clear();
		Empresa.getInstance().getVehiculos().clear();
		Empresa.getInstance().getPedidos().clear();
	}

}
