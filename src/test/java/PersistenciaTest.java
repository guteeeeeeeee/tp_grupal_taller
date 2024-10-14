import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloDatos.*;
import modeloNegocio.Empresa;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import persistencia.EmpresaDTO;
import persistencia.PersistenciaBIN;
import persistencia.UtilPersistencia;
import util.Constantes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PersistenciaTest {

    private PersistenciaBIN persistencia;
    private final String nombreArchivo = "src/test/data/empresa.bin";

    @Before
    public void setUp() {
        persistencia = new PersistenciaBIN();
        // Borrar el archivo antes de cada prueba
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    @After
    public void tearDown() {
    }

    // Verifica que el archivo de persistencia se crea correctamente
    @Test
    public void testCrearArchivo() {
        try {
            persistencia.abrirOutput(nombreArchivo);
            persistencia.escribir(UtilPersistencia.EmpresaDtoFromEmpresa());  // Convertir Empresa a EmpresaDTO y persistir
            persistencia.cerrarOutput();

            File archivo = new File(nombreArchivo);
            Assert.assertTrue("El archivo de persistencia debería haberse creado", archivo.exists());
        } catch (IOException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    // Verifica la lectura y escritura de una empresa vacía
    @Test
    public void testEmpresaVacia() {
        try {
            // Persistir empresa vacía
            persistencia.abrirOutput(nombreArchivo);
            persistencia.escribir(UtilPersistencia.EmpresaDtoFromEmpresa());
            persistencia.cerrarOutput();

            // Recuperar empresa vacía
            persistencia.abrirInput(nombreArchivo);
            EmpresaDTO empresaRecuperadaDTO = (EmpresaDTO) persistencia.leer();
            persistencia.cerrarInput();

            // Convertir de EmpresaDTO a Empresa
            UtilPersistencia.empresaFromEmpresaDTO(empresaRecuperadaDTO);

            // Compara los atributos de la empresa recuperada con los de la empresa actual
            assertEquals("La lista de choferes debería estar vacía",
                    Empresa.getInstance().getChoferes().size(), empresaRecuperadaDTO.getChoferes().size());

            assertEquals("La lista de vehículos debería estar vacía",
                    Empresa.getInstance().getVehiculos().size(), empresaRecuperadaDTO.getVehiculos().size());

            assertEquals("La lista de clientes debería estar vacía",
                    Empresa.getInstance().getClientes().size(), empresaRecuperadaDTO.getClientes().size());

        } catch (IOException | ClassNotFoundException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    // Verifica la lectura y escritura de una empresa con datos
    @Test
    public void testEmpresaConDatos() {
        try {
            // Agregar datos a la empresa
            agregarDatosEmpresa();

            // Persistir la empresa con datos
            persistencia.abrirOutput(nombreArchivo);
            persistencia.escribir(UtilPersistencia.EmpresaDtoFromEmpresa());
            persistencia.cerrarOutput();

            // Recuperar la empresa con datos
            persistencia.abrirInput(nombreArchivo);
            EmpresaDTO empresaRecuperadaDTO = (EmpresaDTO) persistencia.leer();
            persistencia.cerrarInput();

            // Convertir de EmpresaDTO a Empresa
            UtilPersistencia.empresaFromEmpresaDTO(empresaRecuperadaDTO);

            // Comparar los atributos de la empresa recuperada con los originales
            assertEquals("La cantidad de choferes debería coincidir",
                    Empresa.getInstance().getChoferes().size(), empresaRecuperadaDTO.getChoferes().size());

            assertEquals("La cantidad de vehículos debería coincidir",
                    Empresa.getInstance().getVehiculos().size(), empresaRecuperadaDTO.getVehiculos().size());

            assertEquals("La cantidad de clientes debería coincidir",
                    Empresa.getInstance().getClientes().size(), empresaRecuperadaDTO.getClientes().size());

            // Comparar un chofer en específico para verificar que los datos coinciden
            assertEquals("El DNI del chofer debería coincidir",
                    Empresa.getInstance().getChoferes().get("123456789").getDni(),
                    empresaRecuperadaDTO.getChoferes().get("123456789").getDni());

            // Comparar un vehiculo en específico para verificar que los datos coinciden
            assertEquals("La matrícula del vehículo debería coincidir",
                    Empresa.getInstance().getVehiculos().get("AA11BB").getPatente(),
                    empresaRecuperadaDTO.getVehiculos().get("AA11BB").getPatente());

            // Comparar un cliente en específico para verificar que los datos coinciden
            assertEquals("El nombre real del cliente debería coincidir",
                    Empresa.getInstance().getClientes().get("Rober72").getNombreReal(),
                    empresaRecuperadaDTO.getClientes().get("Rober72").getNombreReal());

        } catch (IOException | ClassNotFoundException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    // Verifica que se lance una excepción al intentar leer un archivo inexistente
    @Test(expected = FileNotFoundException.class)
    public void testDespersistirSinArchivo() throws IOException, ClassNotFoundException {
        // Intentar abrir un archivo que no existe
        persistencia.abrirInput(nombreArchivo);
        persistencia.leer(); // Esto debería lanzar FileNotFoundException
    }

    // Método auxiliar para agregar datos a la empresa
    private void agregarDatosEmpresa() {
        try {
            Empresa.getInstance().agregarChofer(new ChoferPermanente("123456789", "Juan Carlos", 2020, 2));
            Empresa.getInstance().agregarChofer(new ChoferTemporario("1122334455", "Sandro"));
            Empresa.getInstance().agregarChofer(new ChoferPermanente("5544778855", "Rodrigo sad", 2022, 1));

            Empresa.getInstance().agregarVehiculo(new Auto("AA11BB", 4, false));
            Empresa.getInstance().agregarVehiculo(new Moto("BD54FA"));
            Empresa.getInstance().agregarVehiculo(new Moto("EE57FJ"));
            Empresa.getInstance().agregarVehiculo(new Combi("GE69TR", 10, false));

            Empresa.getInstance().agregarCliente("Rober72", "123456", "Roberto Perez");
            Empresa.getInstance().agregarCliente("TutiFruti", "123456", "Omar Pezutti");
            Empresa.getInstance().agregarCliente("Marceee", "123456", "Marcelo Gomez");

            Pedido pedido = new Pedido(Empresa.getInstance().getClientes().get("TutiFruti"),2,false,true,6, Constantes.ZONA_PELIGROSA);
            Empresa.getInstance().agregarPedido(pedido);
            Empresa.getInstance().validarPedido(pedido);

        } catch (Exception e) {
            fail("No debería lanzar excepción al agregar datos a la empresa");
        }
    }
}
