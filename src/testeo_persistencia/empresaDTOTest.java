package testeo_persistencia;

import modeloDatos.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistencia.EmpresaDTO;

import java.util.ArrayList;
import java.util.HashMap;


import static org.junit.Assert.*;

/**
 * Testea los setters y getters de la clase empresaDTO <br>
 * para verificar si se almacenan correctamente los datos
 */
public class empresaDTOTest {

    EmpresaLlenoFixture1 empresaLlenoFixture1 = new EmpresaLlenoFixture1();
    EmpresaDTO empresaDTO = new EmpresaDTO();

    @Before
    public void setUp() throws Exception {
        empresaLlenoFixture1.setUp();
    }

    @After
    public void tearDown() {
        empresaLlenoFixture1.tearDown();
    }

    @Test
    public void testSetChoferes() {
        try {
            HashMap<String, Chofer> choferes;
            empresaDTO.setChoferes(empresaLlenoFixture1.empresa.getChoferes());
            choferes = empresaDTO.getChoferes();
            assertEquals("Deberian ser iguales los choferes", empresaLlenoFixture1.empresa.getChoferes(), choferes);
        } catch (Exception e) {
            fail("No deberia de dar excepcion");
        }
    }

    @Test
    public void testSetChoferesDesocupados() {
        try {
            Chofer choferDesocupado = new ChoferPermanente("11223344", "Raul", 2022, 0);
            empresaLlenoFixture1.empresa.agregarChofer(choferDesocupado);

            ArrayList<Chofer> choferesDesocupados = new ArrayList<>();
            choferesDesocupados.add(choferDesocupado);
            empresaLlenoFixture1.empresa.setChoferesDesocupados(choferesDesocupados);

            empresaDTO.setChoferesDesocupados(empresaLlenoFixture1.empresa.getChoferesDesocupados());

            choferesDesocupados = empresaDTO.getChoferesDesocupados();
            assertEquals("Deberian ser iguales los choferes desocupados", empresaLlenoFixture1.empresa.getChoferesDesocupados(), choferesDesocupados);
            assertTrue("La lista deberia de contener a choferDesocupado", choferesDesocupados.contains(choferDesocupado));
        } catch (Exception e) {
            fail("No deberia de dar excepcion");
        }
    }

    @Test
    public void testSetClientes() {
        try {
            HashMap<String, Cliente> clientes;
            empresaDTO.setClientes(empresaLlenoFixture1.empresa.getClientes());
            clientes = empresaDTO.getClientes();
            assertEquals("Deberian ser iguales los clientes", empresaLlenoFixture1.empresa.getClientes(), clientes);
        } catch (Exception e) {
            fail("No deberia de dar excepcion");
        }
    }

    @Test
    public void testSetPedidos() {
        try {
            HashMap<Cliente, Pedido> pedidos;
            empresaDTO.setPedidos(empresaLlenoFixture1.empresa.getPedidos());
            pedidos = empresaDTO.getPedidos();
            assertEquals("Deberian ser iguales los pedidos", empresaLlenoFixture1.empresa.getPedidos(), pedidos);
        } catch (Exception e) {
            fail("No deberia de dar excepcion");
        }
    }

    @Test
    public void testSetUsuarioLogeado() {
        try {
            empresaDTO.setUsuarioLogeado(empresaLlenoFixture1.empresa.getUsuarioLogeado());
            assertEquals("Deberian ser iguales los usuarios logeados", empresaDTO.getUsuarioLogeado(), empresaLlenoFixture1.empresa.getUsuarioLogeado());
        } catch (Exception e) {
            fail("No deberia de dar excepcion");
        }
    }

    @Test
    public void testSetVehiculos() {
        try {
            HashMap<String, Vehiculo> vehiculos;
            empresaDTO.setVehiculos(empresaLlenoFixture1.empresa.getVehiculos());
            vehiculos = empresaDTO.getVehiculos();
            assertEquals("Deberian ser iguales los vehiculos", empresaLlenoFixture1.empresa.getVehiculos(), vehiculos);
        } catch (Exception e) {
            fail("No deberia de dar excepcion");
        }
    }

    @Test
    public void testSetVehiculosDesocupados() {
        try {
            Vehiculo vehiculoDesocupado = new Moto("AA55EE");
            empresaLlenoFixture1.empresa.agregarVehiculo(vehiculoDesocupado);

            ArrayList<Vehiculo> vehiculosDesocupados = new ArrayList<>();
            vehiculosDesocupados.add(vehiculoDesocupado);
            empresaLlenoFixture1.empresa.setVehiculosDesocupados(vehiculosDesocupados);

            empresaDTO.setVehiculosDesocupados(empresaLlenoFixture1.empresa.getVehiculosDesocupados());

            assertEquals("Deberian ser iguales los vehiculos desocupados", empresaLlenoFixture1.empresa.getVehiculosDesocupados(), vehiculosDesocupados);
            assertTrue("La lista deberia contener a vehiculoDesocupado", vehiculosDesocupados.contains(vehiculoDesocupado));

        } catch (Exception e) {
            fail("No deberia de dar excepcion");
        }
    }

    @Test
    public void testSetViajesIniciados() {
        try {
            empresaLlenoFixture1.empresa.crearViaje(empresaLlenoFixture1.getPedido(), empresaLlenoFixture1.getChofer(), empresaLlenoFixture1.getVehiculo());
            empresaDTO.setViajesIniciados(empresaLlenoFixture1.empresa.getViajesIniciados());
            assertEquals("Deberian ser iguales los viajes iniciados", empresaDTO.getViajesIniciados(), empresaLlenoFixture1.empresa.getViajesIniciados());
        } catch (Exception e) {
            fail("No deberia de dar excepcion");
        }
    }

    @Test
    public void testSetViajesTerminados() {
        try {
            empresaLlenoFixture1.empresa.crearViaje(empresaLlenoFixture1.getPedido(), empresaLlenoFixture1.getChofer(), empresaLlenoFixture1.getVehiculo());
            empresaLlenoFixture1.empresa.pagarYFinalizarViaje(4);
            empresaDTO.setViajesTerminados(empresaLlenoFixture1.empresa.getViajesTerminados());
            assertEquals("Deberian ser iguales los viajes terminados", empresaDTO.getViajesTerminados(), empresaLlenoFixture1.empresa.getViajesTerminados());
        } catch (Exception e) {
            fail("No deberia dar excepcion");
        }
    }

}
