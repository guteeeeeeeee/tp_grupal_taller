package testeo_gui.ayuda;

import vista.IOptionPane;

public class FalsoOptionPane implements IOptionPane {
	
	private String mensaje = null;
	
	public FalsoOptionPane() {
		super();
	}
	
	@Override
	public void ShowMessage(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
}
