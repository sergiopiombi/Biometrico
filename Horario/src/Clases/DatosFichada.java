package Clases;

import java.util.ArrayList;

public class DatosFichada {
	
	private int hora;
	private int minutos;
	private int segundos;
	private int nromaquina;
	private int nrodocumento;
	
	public int getNrodocumento() {
		return nrodocumento;
	}
	public void setNrodocumento(int nrodocumento) {
		this.nrodocumento = nrodocumento;
	}
	public int getHora() {
		return hora;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
	public int getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	public int getSegundos() {
		return segundos;
	}
	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}
	public int getNromaquina() {
		return nromaquina;
	}
	public void setNromaquina(int nromaquina) {
		this.nromaquina = nromaquina;
	}
	

	public DatosFichada() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void generoDatos(ArrayList<String> datos) {
		
	}
	

	public DatosFichada getDatos() {
		DatosFichada df = new DatosFichada();
		
		df.setNrodocumento(this.getNrodocumento());
		df.setHora(this.getHora());
		df.setMinutos(this.getMinutos());
		df.setSegundos(this.getSegundos());
		df.setNromaquina(this.getNromaquina());
				
		return df;		
	}

}
