package controlador.NuevoVehiculo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Auto;
import modeloDatos.Combi;
import modeloNegocio.Empresa;
import testeo_gui.FalsoOptionPane;
import vista.IVista;
import vista.Ventana;

public class NuevoVehiculo_con_patente_ya_cargada {

	Controlador controlador;
	IVista vista_mock;
	String patente;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.patente = "ccc111";
		
		Auto auto = new Auto("ccc111",2,false);
		Empresa.getInstance().agregarVehiculo(auto);
		
		this.vista_mock = mock(Ventana.class);
		this.controlador = new Controlador();
		this.controlador.setVista(vista_mock);
		this.op = new FalsoOptionPane();
		when(this.vista_mock.getOptionPane()).thenReturn(op);
	}

	@Test
	public void test_agregar_nuevo_vehiculo_con_auto_cargado() {
		String tipo = "COMBI";
		
		when(this.vista_mock.getTipoVehiculo()).thenReturn(tipo);
		when(this.vista_mock.getPatente()).thenReturn(this.patente);
		when(this.vista_mock.getPlazas()).thenReturn(10);
		when(this.vista_mock.isPedidoConMascota()).thenReturn(false);
		
		assertEquals(1,Empresa.getInstance().getVehiculos().size());
		this.controlador.nuevoVehiculo();
		assertEquals("Vehiculo ya Registrado",this.op.getMensaje());
		assertEquals(1,Empresa.getInstance().getVehiculos().size());
	}

}
