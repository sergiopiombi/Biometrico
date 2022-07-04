package Clases;


import java.util.ArrayList;
import java.util.List;

import org.adempiere.utils.DatosEmpleados;

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

	private boolean ReadYSave() 
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
					
					// Lleno el array con los datos
					arDatosFichada.add(df);
					
					

					i++;

					System.out.println("Movimientos: " + output.dwEnrollNumber + " - " +
							output.dwHour + ":" + output.dwMinute + ":" + output.dwSecond + " - " +
							output.dwYear + "/" + output.dwMonth + "/" + output.dwDay + "-" +
							output.dwEMachineNumber + "-" + output.dwTMachineNumber + "-" + 
							output.dwVerifyMode);
				}


				
			generaXLS g = new generaXLS();
			g.doIt(arDatosFichada);
				
				
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

			// Aca leo y recorro los aparatos de la connfigruacion del archivo.
			// por ahora fijo
			
			int i=0;
			while(i<2)
			{
				ip = "192.168.0.17"; //rs.getString("ip");
				puerto = Long.parseLong("5005"); // String.valueOf(rs.getInt("puerto"))
				pass = Long.parseLong("0"); //Long.parseLong(rs.getString("password"));
				numero_maquina = Long.parseLong("1"); //Long.parseLong(String.valueOf(rs.getInt("numeromaquina")));
				
				if(Open())
				{
					if(ReadYSave())
						Empty();
					else
						value = "Error - No Pudo Grabar Movimientos - Intente Nuevamente";
						
					Close();
				}
				else
					value = "No Pudo Conectar con Aparato numero " + numero_maquina;
				i++;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			value = "Error: No Pudo Completar Proceso";
		}
		
		
		return value;
	}

}