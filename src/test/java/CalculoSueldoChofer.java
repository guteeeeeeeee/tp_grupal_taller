import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.ChoferTemporario;
import modeloNegocio.Empresa;

public class CalculoSueldoChofer {

	ChoferTemporario chofer_temporario;
	ChoferPermanente chofer_permanente;
	ChoferPermanente chofer_permanente_mayor_20_antiguedad;
	ChoferPermanente chofer_posterior;
	double sueldo_basico;
	
	@Before
	public void setUp() throws Exception {
		this.sueldo_basico = 500000.0;
		this.chofer_temporario = new ChoferTemporario("123123","marcelo hanson");
		this.chofer_permanente = new ChoferPermanente("23123","hugo tomas",2020,4);
		this.chofer_permanente_mayor_20_antiguedad = new ChoferPermanente("5897","luciano taborda",1980,7);
		this.chofer_posterior = new ChoferPermanente("5821","marcelo pelegrino",2027,2);
	}

	@Test
	public void calculo_sueldo_temporario() {
		assertEquals(this.sueldo_basico,this.chofer_temporario.getSueldoBasico(),1.0);
		assertEquals(this.chofer_temporario.getSueldoBasico(),this.chofer_temporario.getSueldoBruto(),1.0);
		assertEquals(this.sueldo_basico * 0.86,this.chofer_temporario.getSueldoNeto(),1.0);
	}
	
	@Test
	public void calculo_sueldo_permanente() {
		//antiguedad
		double bruto_calculo = this.chofer_permanente.getSueldoBasico() + (0.05 * this.chofer_permanente.getSueldoBasico()) * this.chofer_permanente.getAntiguedad();
		//hijos
		bruto_calculo = bruto_calculo + (0.07 * this.chofer_permanente.getSueldoBasico()) * this.chofer_permanente.getCantidadHijos();
		assertEquals(this.sueldo_basico,this.chofer_permanente.getSueldoBasico(),1.0);
		assertEquals(bruto_calculo,this.chofer_permanente.getSueldoBruto(),1.0);
		assertEquals(bruto_calculo * 0.86, this.chofer_permanente.getSueldoNeto(),1.0);
	}
	
	@Test
	public void calculo_sueldo_permanente_mas_de_20_antiguedad(){ //calcula mas de 20 de antiguedad !!
		//antiguedad
		double bruto_calculo = this.chofer_permanente_mayor_20_antiguedad.getSueldoBasico() + (0.05 * this.chofer_permanente_mayor_20_antiguedad.getSueldoBasico()) * 20.;
		//hijos
		bruto_calculo = bruto_calculo + (0.07 * this.chofer_permanente_mayor_20_antiguedad.getSueldoBasico()) * this.chofer_permanente_mayor_20_antiguedad.getCantidadHijos();
		assertEquals(this.sueldo_basico,this.chofer_permanente_mayor_20_antiguedad.getSueldoBasico(),1.0);
		assertEquals(bruto_calculo,this.chofer_permanente_mayor_20_antiguedad.getSueldoBruto(),1.0);
		assertEquals(bruto_calculo * 0.86, this.chofer_permanente_mayor_20_antiguedad.getSueldoNeto(),1.0);
	}
	
	@Test
	public void calculo_sueldo_permanente_ingreso_posterior_al_anio_actual() {
		//antiguedad
		double bruto_calculo = this.chofer_posterior.getSueldoBasico();
		//hijos
		bruto_calculo = bruto_calculo + (0.07 * this.chofer_posterior.getSueldoBasico()) * this.chofer_posterior.getCantidadHijos();
		assertEquals(this.sueldo_basico,this.chofer_posterior.getSueldoBasico(),1.0);
		assertEquals(bruto_calculo,this.chofer_posterior.getSueldoBruto(),1.0); //calcula mal porque toma la antiguedad negativa entonces resta en el calculo del sueldo
		assertEquals(bruto_calculo * 0.86, this.chofer_posterior.getSueldoNeto(),1.0);
	}
	
	@Test
	public void calculo_sueldo_temporario_cambia_sueldo_basico() {
		double nuevo_sueldo_basico = 1000.0;
		this.chofer_temporario.setSueldoBasico(nuevo_sueldo_basico);
		double bruto_calculo = this.chofer_temporario.getSueldoBasico();
		assertEquals(nuevo_sueldo_basico,this.chofer_temporario.getSueldoBasico(),1.0);
		assertEquals(bruto_calculo,this.chofer_temporario.getSueldoBruto(),1.0);
		assertEquals(bruto_calculo * 0.86, this.chofer_temporario.getSueldoNeto(),1.0);
	}
	
	@Test
	public void calculo_sueldo_permanente_cambia_sueldo_basico() {
		double nuevo_sueldo_basico = 1000.0;
		this.chofer_permanente.setSueldoBasico(nuevo_sueldo_basico);
		//antiguedad
		double bruto_calculo = this.chofer_permanente.getSueldoBasico() + (0.05 * this.chofer_permanente.getSueldoBasico()) * this.chofer_permanente.getAntiguedad();
		//hijos
		bruto_calculo = bruto_calculo + (0.07 * this.chofer_permanente.getSueldoBasico()) * this.chofer_permanente.getCantidadHijos();
		assertEquals(nuevo_sueldo_basico,this.chofer_permanente.getSueldoBasico(),1.0);
		assertEquals(bruto_calculo,this.chofer_permanente.getSueldoBruto(),1.0);
		assertEquals(bruto_calculo * 0.86, this.chofer_permanente.getSueldoNeto(),1.0);
	}
	
	@After
	public void limpio() {
		this.chofer_permanente.setSueldoBasico(500000.0);
		this.chofer_temporario.setSueldoBasico(500000.0);
	}
}
