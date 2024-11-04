package modeloDatos.Viaje;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.*;

public class GetValor_viaje {

	@Before
	public void setUp() throws Exception {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo moto = new Moto("mmm111");
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,1,false,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,moto);
	}

	@Test
	public void test_standard_con_mascota_con_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,5,true,true,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(4250.0,viaje.getValor(),1.0);
	}
	
	@Test
	public void test_standard_con_mascota_sin_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,2,true,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(2900.0,viaje.getValor(),1.0);
	}

	@Test
	public void test_standard_sin_mascota_con_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,10,false,true,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(3750.0,viaje.getValor(),1.0);
	}
	
	@Test
	public void test_standard_sin_mascota_sin_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,1,false,false,5,"ZONA_STANDARD");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(1600.0,viaje.getValor(),1.0);
	}
	
/////////////////////
	@Test
	public void test_sin_asfaltar_con_mascota_con_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,5,true,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(5000.0,viaje.getValor(),1.0);
	}
	
	@Test
	public void test_sin_asfaltar_con_mascota_sin_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,2,true,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(3350.0,viaje.getValor(),1.0);
	}

	@Test
	public void test_sin_asfaltar_sin_mascota_con_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,10,false,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(5000.0,viaje.getValor(),1.0);
	}
	
	@Test
	public void test_sin_asfaltar_sin_mascota_sin_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,1,false,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(1950.0,viaje.getValor(),1.0);
	}
	/////////////////////
	@Test
	public void test_peligrosa_con_mascota_con_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,5,true,true,5,"ZONA_PELIGROSA");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(4750.0,viaje.getValor(),1.0);
	}

	@Test
	public void test_peligrosa_con_mascota_sin_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,2,true,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(3400.0,viaje.getValor(),1.0);
	}

	@Test
	public void test_peligrosa_sin_mascota_con_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,10,false,true,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(4250.0,viaje.getValor(),1.0);
	}

	@Test
	public void test_peligrosa_sin_mascota_sin_baul() {
		Chofer chofer = new ChoferTemporario("123","juan");
		Vehiculo combi = new Combi("mmm111",5,true);
		Cliente cliente = new Cliente("pepe1","123","pepe");
		Pedido pedido = new Pedido(cliente,1,false,false,5,"ZONA_SIN_ASFALTAR");
		Viaje viaje = new Viaje(pedido,chofer,combi);
		assertEquals(2100.0,viaje.getValor(),1.0);
	}
}
