package Clases;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import smack.comm.SBXPCProxy;
import smack.comm.output.GeneralLogDataOutput;
import smack.comm.data.SysUtil;
import Utilidades.generaXLS;



/**
 * 
 * @author Sergio.Piombi
 * 
 */
public class ControlBiometrico{
	
	private static final String PATH = System.getProperty("user.home") + File.separator + "reportes" + File.separator + "config.txt";
	
	private String ip = "";
	private Long puerto = new Long(0);
	private Long pass = new Long(0);
	private Long numero_maquina = new Long(1);


	private boolean Open() 
	{                                            
		boolean ret = false;
		
		try
		{
			ret = SBXPCProxy.ConnectTcpip(  numero_maquina, ip, puerto,pass);
			System.out.println("Abrio conexion: " + ret);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return ret;
	}                                           

	private void Close()
	{                                             
		try
		{
			SBXPCProxy.Disconnect(numero_maquina);
			System.out.println("Cerro Conexion");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}                                            

	private boolean ReadYSave(String nombrePlanilla) 
	{                                               
		
		boolean ok = false;
		List<DatosFichada> arDatosFichada = new ArrayList<DatosFichada>(); 
		
		try
		{
			boolean ret;
			int errorCode;

			System.out.println(SysUtil.WORKING);

			ret = SBXPCProxy.EnableDevice(numero_maquina, false);

			if(!ret)
			{
				System.out.println(SysUtil.NO_DEVICE);
				return false;
			}

			ret = SBXPCProxy.ReadAllGLogData(numero_maquina);

			if(ret)
			{
				errorCode = 0;

				System.out.println("Leyendo Datos...");

				GeneralLogDataOutput output;
				int i = 0;
				
				int enrollnumber=0, hora=0, minutos = 0;
				DatosFichada df = null;
								
				while(true)
				{
					output = SBXPCProxy.GetAllGLogData(numero_maquina);

					if(!output.isSuccess()) 
						break;


					if(i==0)
					{
						enrollnumber = Integer.parseInt(String.valueOf(output.dwEnrollNumber));
						hora = Integer.parseInt(String.valueOf(output.dwHour));
						minutos = Integer.parseInt(String.valueOf(output.dwMinute));
					}


					if(i > 0 && enrollnumber==Integer.parseInt(String.valueOf(output.dwEnrollNumber))
							&& (hora == Integer.parseInt(String.valueOf(output.dwHour)) &&
							Integer.parseInt(String.valueOf(output.dwMinute)) - minutos < 10 ))
						continue;
					else if (i > 0)
					{
						enrollnumber = Integer.parseInt(String.valueOf(output.dwEnrollNumber));
						hora = Integer.parseInt(String.valueOf(output.dwHour));
						minutos = Integer.parseInt(String.valueOf(output.dwMinute));
					}

					
					
					df = new DatosFichada();
					df.setNrodocumento(enrollnumber);
					df.setHora(hora);
					df.setMinutos(minutos);
					df.setSegundos(Integer.parseInt(String.valueOf(output.dwSecond)));
					df.setNromaquina(Integer.parseInt(String.valueOf(output.dwEMachineNumber)));
										
					
					Calendar c = Calendar.getInstance();
					c.set(Integer.parseInt(String.valueOf(output.dwYear)), Integer.parseInt(String.valueOf(output.dwMonth))-1, Integer.parseInt(String.valueOf(output.dwDay)));
					
					Timestamp fecha =  DateToTimestamp(c.getTime());
					df.setFecha(fecha);
					
					// Lleno el array con los datos
					arDatosFichada.add(df);
					
					

					i++;

					System.out.println("Movimientos: " + output.dwEnrollNumber + " - " +
							output.dwHour + ":" + output.dwMinute + ":" + output.dwSecond + " - " +
							output.dwYear + "/" + output.dwMonth + "/" + output.dwDay + "-" +
							output.dwEMachineNumber + "-" + output.dwTMachineNumber + "-" + 
							output.dwVerifyMode + " " + String.valueOf(output.dwEMachineNumber));
				}

			generaXLS g = new generaXLS();
			g.doIt(arDatosFichada,nombrePlanilla);
				
				
			}else {
				errorCode = (int)SBXPCProxy.GetLastError(numero_maquina).dwValue;
				System.out.println(SysUtil.ErrorPrint(errorCode));
			}

			ret = SBXPCProxy.EnableDevice(numero_maquina, true);

			System.out.println("Habilito dispositivo: " + ret);
			
			ok = true;
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
			
		return ok;
	}       
	
	public Timestamp DateToTimestamp(Date fecha) {
		return fecha == null ? null : new Timestamp(fecha.getTime());
	}

	private void Empty() 
	{                                                
		try
		{

			boolean ret;
			int errorCode;

			System.out.println(SysUtil.WORKING);


			ret = SBXPCProxy.EnableDevice(numero_maquina, false);
			ret = false;
			if(!ret)
			{
				System.out.println(SysUtil.NO_DEVICE);
				return;
			}

			ret = SBXPCProxy.EmptyGeneralLogData(numero_maquina);

			if(ret)
				errorCode = 0;
			else
				errorCode = (int)SBXPCProxy.GetLastError(numero_maquina).dwValue;

			System.out.println(SysUtil.ErrorPrint(errorCode));

			ret = SBXPCProxy.EnableDevice(numero_maquina, true);

			System.out.println("Habilito dispositivo: " + ret);
		}
		catch(Exception ex)
		{
			ex.printStackTrace(); 
		}
	}          

	public String doIt() throws Exception
	{
		String value = "Termino Proceso correctamente";
		
		try
		{
			List<PropiedadesHorario> arPH =  getConfiguracion();
			
			for(PropiedadesHorario ph : arPH)
			{
				
				ip = ph.getIp();
				puerto = ph.getPuerto();
				pass = ph.getPass();
				numero_maquina = ph.getNumero_maquina();
				
				
				if(Open())
				{
					if(ReadYSave(ph.getNombreEquipo()));
						//Empty();
					else
						value = "Error - No Pudo Grabar Movimientos - Intente Nuevamente";
						
					Close();
				}
				else
					value = "No Pudo Conectar con Aparato numero " + numero_maquina;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			value = "Error: No Pudo Completar Proceso";
		}
		
		
		return value;
	}
	
	
	
	private List<PropiedadesHorario> getConfiguracion() {
		FileReader     fr = null;
		BufferedReader br = null;
		List<PropiedadesHorario> arPH = new ArrayList<PropiedadesHorario>();

		try
		{
			fr = new FileReader(PATH);
			br = new BufferedReader(fr);

			String line="";
			PropiedadesHorario ph = null;
			while ((line = br.readLine()) != null){
				
				if(!line.isEmpty()){
				
					if(ph == null)
						ph = new PropiedadesHorario();
					
					if(line.contains("@IP"))
						ph.setIp(line.split("=")[1]);
					if(line.contains("@PUERTO"))
						ph.setPuerto(Long.parseLong( (line.split("=")[1])));
					if(line.contains("@PASSWORD"))
						ph.setPass(Long.parseLong( (line.split("=")[1])));
					if(line.contains("@NROMAQUINA"))
						ph.setNumero_maquina(Long.parseLong( (line.split("=")[1])));
					if(line.contains("@PLANILLA"))
						ph.setNombreEquipo(line.split("=")[1]);
				}	
				
				if(line.contains("@~@")) {
					arPH.add(ph);
					ph=null;
				}
				
			}	

		}
		catch(Exception ex) {
			ex.printStackTrace();

		}
		finally
		{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return arPH;
	}

	
	
}