package comun;

import java.io.Serializable;

public class Mensaje implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int indice;
	private String mensaje;

	public Mensaje(int indice) {
		this.indice = indice;
	}

	public Mensaje(int indice, String mensaje) {
		this.indice = indice;
		this.mensaje = mensaje;
	}

	public Mensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}