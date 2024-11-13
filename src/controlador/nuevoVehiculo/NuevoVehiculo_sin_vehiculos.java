package controlador.nuevoVehiculo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import util.Constantes;
import vista.IVista;
import vista.Ventana;

public class NuevoVehiculo_sin_vehiculos {
	
	Controlador controlador;
	IVista vista_mock;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.vista_mock = mock(Ventana.class);
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(this.vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_agregar_nuevo_vehiculo() {
		String patente = "mmm111";
		String tipo = Constantes.MOTO;
		when(this.vista_mock.getTipoVehiculo()).thenReturn(tipo);
		when(this.vista_mock.getPatente()).thenReturn(patente);
		assertEquals(0,Empresa.getInstance().getVehiculos().size());
		this.controlador.nuevoVehiculo();
		assertEquals(1,Empresa.getInstance().getVehiculos().size());
		assertEquals(patente,Empresa.getInstance().getVehiculos().get(patente).getPatente());
		assertEquals(1,Empresa.getInstance().getVehiculos().get(patente).getCantidadPlazas());
		verify(this.vista_mock).actualizar();
	}
	
	@Test
	public void test_agregar_nuevo_vehiculo_cajablanca_c2() {
		String patente = "aabbcc";
		String tipo = "AUTO";
		when(this.vista_mock.getTipoVehiculo()).thenReturn(tipo);
		when(this.vista_mock.getPatente()).thenReturn(patente);
		when(this.vista_mock.getPlazas()).thenReturn(4);
		assertEquals(0,Empresa.getInstance().getVehiculos().size());
		this.controlador.nuevoVehiculo();
		assertEquals(1,Empresa.getInstance().getVehiculos().size());
		assertEquals(patente,Empresa.getInstance().getVehiculos().get(patente).getPatente());
		assertEquals(4,Empresa.getInstance().getVehiculos().get(patente).getCantidadPlazas());
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().getVehiculos().clear();
	}

}
