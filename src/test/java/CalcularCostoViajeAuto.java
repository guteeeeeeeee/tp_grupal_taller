import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;

public class CalcularCostoViajeAuto {

	Chofer chofer;
	Vehiculo auto_sin;
	Vehiculo auto_con; //pf
	Cliente cliente;
	
	@Before
	public void setUp() throws Exception {
		this.chofer = new ChoferTemporario("3213213","fernando");
		this.auto_sin = new Auto("5894",4,false);
		this.auto_con = new Auto("213213",4,true);
		this.cliente = new Cliente("aa","aa","aa");
	}
	
	//con 4 pasajeros
	//zona standard
	@Test
	public void test_pedido_estandar_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,4,false,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(1900.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido_estandar_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,4,true,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3300.,viaje.getValor(),1.); //mal calculado
	} 
	@Test
	public void test_pedido_estandar_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,4,false,true,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2550.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido_estandar_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,4,true,true,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3950.,viaje.getValor(),1.); //mal calculado
	}
	//calles sin asfaltar
	@Test
	public void test_pedido_sin_asfaltar_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,4,false,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2550.,viaje.getValor(),1.);
	}
	
	@Test
	public void test_pedido_sin_asfaltar_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,4,true,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3950.,viaje.getValor(),1.); //mal calculado
	}
	 
	@Test
	public void test_pedido_sin_asfaltar_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,4,false,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3200.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido_sin_asfaltar_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,4,true,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(4600.,viaje.getValor(),1.); //mal calculado
	}

	//zona peligrosa
	@Test
	public void test_pedido_zona_peligrosa_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,4,false,false,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2400.,viaje.getValor(),1.);
	}
		
	@Test
	public void test_pedido_zona_peligrosa_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,4,true,false,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3800.,viaje.getValor(),1.); //mal calculado
	}
	 
	@Test
	public void test_pedido_zona_peligrosa_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,4,false,true,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3050.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido_zona_peligrosa_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,4,true,true,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(4450.,viaje.getValor(),1.); //mal calculado
	}
	
	//con 2 pasajeros
	//zona standard
	@Test
	public void test_pedido2_estandar_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,2,false,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(1700.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido2_estandar_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,2,true,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2900.,viaje.getValor(),1.); //mal calculado
	}
	@Test
	public void test_pedido2_estandar_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,2,false,true,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2150.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido2_estandar_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,2,true,true,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3350.,viaje.getValor(),1.); //mal calculado
	}
	//calles sin asfaltar
	@Test
	public void test_pedido2_sin_asfaltar_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,2,false,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2150.,viaje.getValor(),1.);
	}
	
	@Test
	public void test_pedido2_sin_asfaltar_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,2,true,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3350.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido2_sin_asfaltar_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,2,false,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3800.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido2_sin_asfaltar_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,2,true,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(4600.,viaje.getValor(),1.); //mal calculado
	}
	
	//zona peligrosa
	@Test
	public void test_pedido2_zona_peligrosa_sin_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,2,false,false,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2200.,viaje.getValor(),1.);
	}
		
	@Test
	public void test_pedido2_zona_peligrosa_con_mascota_sin_baul() {
		Pedido pedido = new Pedido(cliente,2,true,false,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3400.,viaje.getValor(),1.); //mal calculado
	}
	 
	@Test
	public void test_pedido2_zona_peligrosa_sin_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,2,false,true,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,auto_sin);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(2650.,viaje.getValor(),1.); //mal calculado
	}
	
	@Test
	public void test_pedido2_zona_peligrosa_con_mascota_con_baul() {
		Pedido pedido = new Pedido(cliente,2,true,true,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,auto_con);
		assertEquals(1000.,viaje.getValorBase(),1.);
		assertEquals(3850.,viaje.getValor(),1.); //mal calculado
	}
	
}
