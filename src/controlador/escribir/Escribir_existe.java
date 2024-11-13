package controlador.escribir;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import testeo_gui.ayuda.FalsoOptionPane;

public class Escribir_existe {

	Controlador controlador;
	String file_name_nuevo;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
		this.op = new FalsoOptionPane();
		this.controlador.getVista().setOptionPane(this.op);
		this.file_name_nuevo = "escribiendo.bin";
		this.controlador.setFileName(file_name_nuevo);
	}

	
	@Test
	public void escribir_existe_archivo() {
		this.controlador.escribir();
		assertNull(this.op.getMensaje()); //no muestra carteles
	}

}
