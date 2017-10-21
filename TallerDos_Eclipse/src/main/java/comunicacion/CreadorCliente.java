package comunicacion;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class CreadorCliente extends Observable implements Runnable {

	private ServerSocket ss;

	public CreadorCliente() {
		try {
			ss = new ServerSocket(8000);
			System.out.println("Servidor Iniciado");
			Thread hilo = new Thread(this);
			hilo.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket nuevoSocket = ss.accept();
				setChanged();
				notifyObservers(nuevoSocket);
				clearChanged();
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}