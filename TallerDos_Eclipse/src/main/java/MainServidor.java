import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comunicacion.ControlServidor;

public class MainServidor {
	 static ControlServidor cs;
	public static void main(String[] args) {
		FileInputStream serviceAccount;
		
		try {
			serviceAccount = new FileInputStream("tallerdos2dos-firebase-adminsdk-sqb4y-4e20d73eac.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
			  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
			  .setDatabaseUrl("https://tallerdos2dos.firebaseio.com/")
			  .build();
			
			

			FirebaseApp.initializeApp(options);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cs= new ControlServidor();
		
		final FirebaseDatabase database = FirebaseDatabase.getInstance();


	}

}
