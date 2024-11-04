package testeo_persistencia;

import modeloDatos.*;
import modeloNegocio.Empresa;

import static org.junit.Assert.fail;
import static util.Constantes.ZONA_STANDARD;

public class EmpresaLlenoFixture1 {

    Empresa empresa = Empresa.getInstance();
    Chofer chofer;
    Cliente cliente;
    Pedido pedido;
    Usuario usuario;
    Vehiculo vehiculo;

    public EmpresaLlenoFixture1() {
    }

    public void setUp() throws Exception {
        try {
            empresa.agregarCliente("Rober73", "asdasd", "Roberto");
            empresa.login("Rober73", "asdasd");

            chofer = new ChoferPermanente("123456789", "Carlos", 2019, 2);
            empresa.agregarChofer(chofer);

            vehiculo = new Auto("AA11BB", 2, false);
            empresa.agregarVehiculo(vehiculo);

            cliente = empresa.getClientes().get("Rober73");
            pedido = new Pedido(cliente, 2, false, false, 10, ZONA_STANDARD);
            empresa.agregarPedido(pedido);
            empresa.validarPedido(pedido);
            usuario = empresa.getUsuarioLogeado();

        } catch (Exception e) {
            fail("No deberia de dar excepcion");
        }
    }

    public void tearDown() {
        empresa.getChoferes().clear();
        empresa.getChoferesDesocupados().clear();
        empresa.getVehiculos().clear();
        empresa.getVehiculosDesocupados().clear();
        empresa.getClientes().clear();
        empresa.getViajesIniciados().clear();
        empresa.getViajesTerminados().clear();
        empresa.getPedidos().clear();
        empresa.logout();
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }
}
