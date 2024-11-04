package testeo_grupo_taller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Combi;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;

public class CalcularCostoViajeCombi {

	Chofer chofer;
	Vehiculo combi_sin; 
	Vehiculo combi_con; //pf
	Cliente cliente;
	
	@Before
	public void setUp() throws Exception {
		this.chofer = new ChoferTemporario("3213213","fernando");
		this.combi_sin = new Combi("5894",10,false);
		this.combi_con = new Combi("213213",10,true);
		this.cliente = new Cliente("aa","aa","aa");
	}
	
	//con 5 pasajeros
	//zona standard
	@Test
	public void test_pedido_estandar_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,5,false,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2000.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido_estandar_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,5,true,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3500.,viaje.getValor(),1.); //mal calculado
	} 
	@Test
	public void test_pedido_estandar_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,5,false,true,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2750.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido_estandar_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,5,true,true,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(4250.,viaje.getValor(),1.); //mal calculado
	}
	
	//calles sin asfaltar
	@Test
	public void test_pedido_sin_asfaltar_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,5,false,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2750.,viaje.getValor(),1.);
	}
		
	@Test
	public void test_pedido_sin_asfaltar_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,5,true,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(4250.,viaje.getValor(),1.);
	}
		 
	@Test
	public void test_pedido_sin_asfaltar_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,5,false,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3500.,viaje.getValor(),1.); //mal calculado
	}
		
	@Test
	public void test_pedido_sin_asfaltar_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,5,true,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(5000.,viaje.getValor(),1.); //mal calculado
	}
	
	//zona peligrosa
	@Test
	public void test_pedido_zona_peligrosa_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,5,false,false,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2500.,viaje.getValor(),1.);
	}
		
	@Test
	public void test_pedido_zona_peligrosa_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,5,true,false,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(4000.,viaje.getValor(),1.);
	}
	 
	@Test
	public void test_pedido_zona_peligrosa_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,5,false,true,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3250.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido_zona_peligrosa_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,5,true,true,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(4750.,viaje.getValor(),1.); //mal calculado
	}
	//con 10 pasajeros
	//zona standard
	@Test
	public void test_pedido2_estandar_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,10,false,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3000.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido2_estandar_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,10,true,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(5000.,viaje.getValor(),1.); //mal calculado
	} 
	@Test
	public void test_pedido2_estandar_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,10,false,true,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(4250.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido2_estandar_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,10,true,true,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(6250.,viaje.getValor(),1.); //mal calculado
	}
	
	//calles sin asfaltar
	@Test
	public void test_pedido2_sin_asfaltar_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,10,false,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3750.,viaje.getValor(),1.);
	}
		
	@Test
	public void test_pedido2_sin_asfaltar_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,10,true,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(5750.,viaje.getValor(),1.);
	}
		 
	@Test
	public void test_pedido2_sin_asfaltar_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,10,false,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(5000.,viaje.getValor(),1.); //mal calculado
	}
		
	@Test
	public void test_pedido2_sin_asfaltar_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,10,true,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(7000.,viaje.getValor(),1.); //mal calculado
	}
	
	//zona peligrosa
	@Test
	public void test_pedido2_zona_peligrosa_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,10,false,false,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3000.,viaje.getValor(),1.);
	}
		
	@Test
	public void test_pedido2_zona_peligrosa_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,10,true,false,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(5000.,viaje.getValor(),1.);
	}
	 
	@Test
	public void test_pedido2_zona_peligrosa_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,10,false,true,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,combi_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(4250.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido2_zona_peligrosa_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,10,true,true,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,combi_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(6250.,viaje.getValor(),1.); //mal calculado
	}
}