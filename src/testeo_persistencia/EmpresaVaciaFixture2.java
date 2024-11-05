package testeo_persistencia;

import modeloDatos.*;
import modeloNegocio.Empresa;

import static org.junit.Assert.fail;
import static util.Constantes.ZONA_STANDARD;

public class EmpresaVaciaFixture2 {

    Empresa empresa = Empresa.getInstance();
    Chofer chofer;
    Cliente cliente;
    Pedido pedido;
    Usuario usuario;
    Vehiculo vehiculo;

    public EmpresaVaciaFixture2() {
    }

    public void setUp()  {
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
