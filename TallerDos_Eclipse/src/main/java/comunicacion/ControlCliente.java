package comunicacion;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import comun.Mensaje;

public class ControlCliente extends Observable implements Runnable {

	private Socket s;
	private ObjectOutputStream salida;
	private ObjectInputStream entrada;
	private int indice;
	private boolean vida = true;

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public ControlCliente(Socket s, int indice) {
		this.s = s;
		this.indice = indice;
		try {
			salida = new ObjectOutputStream(s.getOutputStream());
			entrada = new ObjectInputStream(s.getInputStream());
			System.out.println("Conectado_Cliente: " + indice);
			Thread hilo = new Thread(this);
			hilo.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (vida) {
			try {
				Mensaje nuevoMensaje = (Mensaje) entrada.readObject();
				setChanged();
				notifyObservers(nuevoMensaje);
				clearChanged();
				Thread.sleep(16);;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Cerro cliente "+indice);
				setChanged();
				notifyObservers("cerrado");
				clearChanged();
				vida = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void enviarMensaje(Mensaje envio) {
		try {
			salida.writeObject(envio);
			salida.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}