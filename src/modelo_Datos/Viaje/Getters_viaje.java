package modelo_Datos.Viaje;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;
import modeloNegocio.Empresa;

public class Getters_viaje {

	Chofer chofer;
	Vehiculo moto;
	Pedido pedido;
	Cliente cliente;
	Viaje viaje;
	
	@Before
	public void setUp() throws Exception {
		this.chofer = new ChoferTemporario("123","juan");
		this.moto = new Moto("mmm111");
		this.cliente = new Cliente("pepe1","123","pepe");
		this.pedido = new Pedido(this.cliente,1,false,false,5,"ZONA_SIN_ASFALTAR");
		this.viaje = new Viaje(this.pedido,this.chofer,this.moto);
	}

	@Test
	public void test_getters() {
		assertEquals(this.chofer,this.viaje.getChofer());
		assertEquals(this.moto,this.viaje.getVehiculo());
		assertEquals(this.chofer,this.viaje.getChofer());
		assertEquals(this.pedido,this.viaje.getPedido());
		assertFalse(this.viaje.isFinalizado());
		this.viaje.finalizarViaje(5);
		assertTrue(this.viaje.isFinalizado());
		assertEquals(5,this.viaje.getCalificacion());
		this.viaje.setValorBase(200);
		assertEquals(200,this.viaje.getValorBase(),0.1);
	}

}
