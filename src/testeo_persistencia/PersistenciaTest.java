package testeo_persistencia;

import modeloDatos.Chofer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistencia.EmpresaDTO;
import persistencia.IPersistencia;
import persistencia.PersistenciaBIN;
import persistencia.UtilPersistencia;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static org.junit.Assert.*;

public class PersistenciaTest {

    EmpresaDTO empresaDTO;
    EmpresaLlenoFixture1 empresaLlenoFixture1;
    EmpresaVaciaFixture2 empresaVaciaFixture2;
    static String nombreArchivo = "test/data/test.bin";
    IPersistencia<Serializable> persistencia;

    public PersistenciaTest() {
    }

    @Before
    public void setUp() {
        persistencia = new PersistenciaBIN();
        empresaDTO = new EmpresaDTO();
        empresaLlenoFixture1 = new EmpresaLlenoFixture1();
        empresaVaciaFixture2 = new EmpresaVaciaFixture2();
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    @After
    public void tearDown() {
        empresaLlenoFixture1.tearDown();
        empresaVaciaFixture2.tearDown();
    }

    @Test
    public void testCrearArchivo() {
        try {
            persistencia.abrirOutput(nombreArchivo);
            File archivo = new File(nombreArchivo);
            assertTrue("Debería existir el archivo test.bin", archivo.exists());
            persistencia.cerrarOutput();
        } catch (IOException e) {
            fail("No debería lanzar excepcion: " + e.getMessage());
        }
    }

    //verifica la lectoescritura de una empresa vacía.
    @Test
    public void testEmpresaVacioConArchivo() {
        try {
            empresaVaciaFixture2.setUp();
            File archivo = new File(nombreArchivo);

            persistencia.abrirOutput(nombreArchivo);
            assertTrue("Debería existir el archivo test.bin", archivo.exists());
            empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
            persistencia.escribir(empresaDTO);
            persistencia.cerrarOutput();

            persistencia.abrirInput(nombreArchivo);
            assertTrue("Debería existir el archivo test.bin", archivo.exists());

            EmpresaDTO empresaDTO1 = (EmpresaDTO) persistencia.leer();
            assertEquals("Las empresas deberían ser vacíos", this.empresaDTO.getChoferes().isEmpty(), empresaDTO1.getChoferes().isEmpty());
            persistencia.cerrarInput();
        } catch (IOException e) {
            fail("No debería lanzar excepcion IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            fail("No debería lanzar excepcion ClassNotFoundExeption: " + e.getMessage());

        }
    }

    //verifica la lectoescritura de una empresa con datos.
    @Test
    public void testEmpresaConDatosConArchivo() {
        try {
            empresaLlenoFixture1.setUp();
            File archivo = new File(nombreArchivo);

            persistencia.abrirOutput(nombreArchivo);
            assertTrue("Debería existir el archivo test.bin", archivo.exists());
            empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
            persistencia.escribir(empresaDTO);
            persistencia.cerrarOutput();

            persistencia.abrirInput(nombreArchivo);

            assertTrue("Debería existir el archivo test.bin", archivo.exists());

            EmpresaDTO empresaDTO1 = (EmpresaDTO) persistencia.leer();
            persistencia.cerrarInput();

            Chofer chofer1 = this.empresaDTO.getChoferes().get(empresaLlenoFixture1.getChofer().getNombre());
            Chofer chofer2 = empresaDTO1.getChoferes().get(empresaLlenoFixture1.getChofer().getNombre());

            assertEquals("Las empresas deberían ser iguales", chofer1, chofer2);
        } catch (IOException e) {
            fail("No debería lanzar excepcion IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            fail("No debería lanzar excepcion ClassNotFoundExeption: " + e.getMessage());

        } catch (Exception e) {
            fail("No debería lanzar excepcion EXCEPTION: " + e.getMessage());

        }
    }

    //verifica si se lanza una excepción al intentar leer un archivo inexistente.
    @Test
    public void testLecturaSinArchivo() {
        try {
            persistencia.abrirInput(nombreArchivo);
            fail("debería haber lanzado una excepcion de tipo IOException");
            persistencia.cerrarInput();
        } catch (IOException e) {
            //
        }
    }


}
