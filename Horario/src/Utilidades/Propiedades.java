package Utilidades;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.Properties;



public class Propiedades {
	public static final String ARCHIVO = "";

	public Propiedades() {
		super();
		// TODO Auto-generated constructor stub
		
	}
	
	public void pp() {
	//	try(OutputStream outputStream = new FileOutputStream(ARCHIVO)){

			Properties pr = new Properties();



			pr.setProperty("ip", "192.168.0.17");
			pr.setProperty("puerto", "5005");
			pr.setProperty("pass", "0");
			pr.setProperty("numero_maquina", "1");

	//		pr.store(outputStream, null);

			System.out.println(pr);


		//}catch(Exception ex) {
			//System.out.println(ex.printStackTrace());
		//}
	}

}
