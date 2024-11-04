package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;

public class CalcularCostoViajeMoto {

	Chofer chofer;
	Vehiculo moto;
	Cliente cliente;
	@Before
	public void setUp() throws Exception {
		this.chofer = new ChoferTemporario("3213213","fernando");
		this.moto = new Moto("213213");;
		this.cliente = new Cliente("aa","aa","aa");
	}
	
	@Test
	public void test_pedido_estandar() {
		Pedido pedido = new Pedido(cliente,1,false,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,moto);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(1600.,viaje.getValor(),1.); //mal calculado
	} 
	
	@Test
	public void test_pedido_calle_sin_asfaltar() {
		Pedido pedido = new Pedido(cliente,1,false,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,moto);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(1950.,viaje.getValor(),1.);
	} 

	@Test
	public void test_pedido_zona_peligrosa() {
		Pedido pedido = new Pedido(cliente,1,false,false,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,moto);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2100,viaje.getValor(),1.);
	}

}
