package controlador.leer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import testeo_gui.ayuda.FalsoOptionPane;
import util.Constantes;
import vista.IVista;
import vista.Ventana;

public class Leer_no_existe {

	Controlador controlador;
	String file_name_nuevo;
	FalsoOptionPane op;
	
	@Before
	public void setUp() throws Exception {
		this.controlador = new Controlador();
		this.op = new FalsoOptionPane();
		this.controlador.getVista().setOptionPane(this.op);
		this.file_name_nuevo = "./nueva/asdasd/dasd/asd/archivo.bin";
		this.controlador.setFileName(file_name_nuevo);
	}

	
	@Test
	public void leer_no_existe_archivo() {
		this.controlador.leer();
		assertTrue(this.op.getMensaje().contains("(No such file")); //cartel diciendo que no existe tal archivo
	}

}
