package Clases;

import java.sql.Timestamp;

public class DatosFichada {
	
	private int hora;
	private int minutos;
	private int segundos;
	private int nromaquina;
	private int nrodocumento;
	private Timestamp fecha;
	
	
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
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public DatosFichada() {
		super();
	}
	
	
}
