package testeo_persistencia;

import modeloNegocio.Empresa;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
    IPersistencia<Serializable> persistencia = new PersistenciaBIN();

    public PersistenciaTest() {
    }

    @Before
    public void setUp() {
        empresaDTO = new EmpresaDTO();
        empresaLlenoFixture1 = new EmpresaLlenoFixture1();
        empresaVaciaFixture2 = new EmpresaVaciaFixture2();
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    @After
    public void tearDown() {}

    @Test
    public void testCrearArchivo(){
        try{
            persistencia.abrirOutput(nombreArchivo);
            File archivo = new File(nombreArchivo);
            assertTrue("Debería existir el archivo test.bin", archivo.exists());
            persistencia.cerrarOutput();
        } catch (IOException e) {
            fail("No debería lanzar excepcion: " + e.getMessage());
        }
    }

    //verifica la lectoescritura de un diccionario vacío.
    //la clase Diccionario debe tener sobreescritos los métodos equals y haschode. De otra forma
    //no puedo comparar dos instancias distintas de la misma clase aunque tengan el mismo estado.
    @Test
    public void testEmpresaVacioArchivo()
    {
        try
        {
            empresaVaciaFixture2.setUp();

            persistencia.abrirOutput(nombreArchivo);
            empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();
            persistencia.escribir(empresaDTO);
            persistencia.cerrarOutput();

            persistencia.abrirInput(nombreArchivo);
            File archivo = new File(nombreArchivo);
            assertTrue("Debería existir el archivo test.bin", archivo.exists());

            EmpresaDTO empresaDTO1 = (EmpresaDTO) persistencia.leer();
            assertEquals("Los diccionarios deberían ser vacíos", this.empresaDTO, empresaDTO1);
        }
        catch (IOException e)
        {
            fail("No debería lanzar excepcion: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
