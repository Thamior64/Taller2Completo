package comunicacion;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comun.Curso;
import comun.Mensaje;

public class ControlServidor extends Observable implements Observer, Runnable {

	private ArrayList<ControlCliente> clientes;
	private CreadorCliente creador;
	
	private final FirebaseDatabase database = FirebaseDatabase.getInstance();
	private DatabaseReference ref = database.getReference("HOMEWORKS");

	public ControlServidor() {
		clientes = new ArrayList<ControlCliente>();
		creador = new CreadorCliente();
		creador.addObserver(this);
		
		
	}

	public ArrayList<ControlCliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<ControlCliente> clientes) {
		this.clientes = clientes;
	}

	public CreadorCliente getCreador() {
		return creador;
	}

	public void setCreador(CreadorCliente creador) {
		this.creador = creador;
	}

	@Override
	public void update(Observable o, Object arg) {
		// si el objeto que le llega es un socket, debe crear un nuevlo cliente
		if (o instanceof CreadorCliente && arg instanceof Socket) {
			Socket s = (Socket) arg;
			// nuevo cliente
			// el clientes.size() es no es necesario, lo hago yo por dar un
			// indice
			ControlCliente nuevo = new ControlCliente(s, clientes.size());
			nuevo.addObserver(this);
			clientes.add(nuevo);
		}
		
		if(arg instanceof Mensaje){
			Mensaje mensaje = (Mensaje)arg;
			System.out.println(mensaje.getMensaje());
			
			
			
			if(mensaje.getMensaje().contains("curso")){
				String[] array = mensaje.getMensaje().split(":");
				ref.child(array[1]).setValue(new Curso(array[1],""));
			} else if(mensaje.getMensaje().contains("guardar")){
				String[] array = mensaje.getMensaje().split(":");
				ref.child(array[1]).setValue(new Curso(array[1],array[2]));
			}
			
		}
		
		if(arg instanceof String) {
			String msg = (String) arg;
			if(msg.contains("cerrado")){
				clientes.remove(o);
				System.out.println("Clientes actuales: "+clientes.size());
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}