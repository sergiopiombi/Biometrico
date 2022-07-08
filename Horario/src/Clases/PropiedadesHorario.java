package Clases;


public class PropiedadesHorario {
	
	public PropiedadesHorario() {
		super();
	}
	
	
	private String ip = "";
	private Long puerto = new Long(0);
	private Long pass = new Long(0);
	private Long numero_maquina = new Long(1);
	private String nombreEquipo = "";
	
	
	
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the puerto
	 */
	public Long getPuerto() {
		return puerto;
	}
	/**
	 * @param puerto the puerto to set
	 */
	public void setPuerto(Long puerto) {
		this.puerto = puerto;
	}
	/**
	 * @return the pass
	 */
	public Long getPass() {
		return pass;
	}
	/**
	 * @param pass the pass to set
	 */
	public void setPass(Long pass) {
		this.pass = pass;
	}
	/**
	 * @return the numero_maquina
	 */
	public Long getNumero_maquina() {
		return numero_maquina;
	}
	/**
	 * @param numero_maquina the numero_maquina to set
	 */
	public void setNumero_maquina(Long numero_maquina) {
		this.numero_maquina = numero_maquina;
	}
	/**
	 * @return the nombreEquipo
	 */
	public String getNombreEquipo() {
		return nombreEquipo;
	}
	/**
	 * @param nombreEquipo the nombreEquipo to set
	 */
	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}
	

	
	
}
