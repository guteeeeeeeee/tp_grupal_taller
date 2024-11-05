package controlador.NuevoChofer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.ChoferPermanente;
import modeloNegocio.Empresa;
import testeo_gui.FalsoOptionPane;
import vista.IVista;
import vista.Ventana;

public class NuevoChofer_sin_choferes {

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
	public void test_agregar_chofer_temporario() {
		String dni = "12345";
		String nombre = "jorge";
		String tipo = "TEMPORARIO";
		
		when(this.vista_mock.getTipoChofer()).thenReturn(tipo);
		when(this.vista_mock.getNombreChofer()).thenReturn(nombre);
		when(this.vista_mock.getDNIChofer()).thenReturn(dni);
		
		assertEquals(0,Empresa.getInstance().getChoferes().size());
		this.controlador.nuevoChofer();
		assertEquals(1,Empresa.getInstance().getChoferes().size());
		assertEquals(dni,Empresa.getInstance().getChoferes().get(dni).getDni());
		assertEquals(nombre,Empresa.getInstance().getChoferes().get(dni).getNombre());
	}
	
	@Test
	public void test_agregar_chofer_permanente() {
		String dni = "12345";
		String nombre = "jorge";
		int anio = 2020;
		int cant_hijos = 4;
		String tipo = "PERMANENTE";
		
		when(this.vista_mock.getTipoChofer()).thenReturn(tipo);
		when(this.vista_mock.getNombreChofer()).thenReturn(nombre);
		when(this.vista_mock.getDNIChofer()).thenReturn(dni);
		when(this.vista_mock.getAnioChofer()).thenReturn(anio);
		when(this.vista_mock.getHijosChofer()).thenReturn(cant_hijos);
		
		assertEquals(0,Empresa.getInstance().getChoferes().size());
		this.controlador.nuevoChofer();
		assertEquals(1,Empresa.getInstance().getChoferes().size());
		assertEquals(dni,Empresa.getInstance().getChoferes().get(dni).getDni());
		assertEquals(nombre,Empresa.getInstance().getChoferes().get(dni).getNombre());
		assertEquals(anio,((ChoferPermanente)Empresa.getInstance().getChoferes().get(dni)).getAnioIngreso());
		assertEquals(cant_hijos,((ChoferPermanente)Empresa.getInstance().getChoferes().get(dni)).getCantidadHijos());
	}
	
	@After
	public void limpiar() {
		Empresa.getInstance().getChoferes().clear();
	}

}
