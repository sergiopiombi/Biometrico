package horario;


import Clases.ControlBiometrico;

public class main {

	public static void main(String[] args) {
		try {
			
			System.out.println("Comenzando proceso de lectura de fichadas");
			ControlBiometrico cb = new ControlBiometrico();
			cb.doIt();
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		System.out.println("Finaliza proceso de lectura de fichadas");
		
		// System.exit(0);				

	}	

}
