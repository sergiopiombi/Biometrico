package horario;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;

//import org.adempiere.utils.Miscfunc;
//import org.compiere.model.X_EMP_ControlBiometrico;
//import org.compiere.util.DB;


import smack.comm.SBXPCProxy;
import smack.comm.output.GeneralLogDataOutput;
import smack.comm.data.SysUtil;



/**
 * 
 * @author Sergio
 * 
 */
public class ControlBiometrico{

	/**
	 * Prepare - e.g., get Parameters.
	 */
	protected void prepare() {

		

	} // prepare

	/**
	 * Perform process.
	 * 
	 * @return Message (translated text)
	 * @throws Exception
	 *             if not successful
	 */

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
				
				//X_EMP_ControlBiometrico cb = null;
				
				int enrollnumber=0, hora=0, minutos = 0;
								
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
						
										
					//cb = new X_EMP_ControlBiometrico(this.getCtx(),0,this.get_TrxName());
					Timestamp f = null;
					Date d = null;
					
					
					//if(cb != null)
				//	{
				//		cb.setAD_Org_ID(0);
				//		cb.setEnrollNumber(Integer.parseInt(String.valueOf(output.dwEnrollNumber)));
				//		cb.setHora(Integer.parseInt(String.valueOf(output.dwHour)));
				//		cb.setMinutos(Integer.parseInt(String.valueOf(output.dwMinute)));
				//		cb.setSegundos(Integer.parseInt(String.valueOf(output.dwSecond)));
				//		cb.setMachineNumber(Integer.parseInt(String.valueOf(output.dwEMachineNumber)));
						
						d = new Date();
						d.setYear(Integer.parseInt(String.valueOf(output.dwYear)) - 1900);
						d.setMonth(Integer.parseInt(String.valueOf(output.dwMonth)) -1);
						d.setDate(Integer.parseInt(String.valueOf(output.dwDay)));
						d.setHours(0);
						d.setMinutes(0);
						d.setSeconds(0);
						
				//		f = Miscfunc.DateToTimestamp(d);
						
				//		cb.setFecha(f);
						
						
					//	if(cb.save())
				//		{
							i++;
							
							System.out.println("Movimientos: " + output.dwEnrollNumber + " - " +
									output.dwHour + ":" + output.dwMinute + ":" + output.dwSecond + " - " +
									output.dwYear + "/" + output.dwMonth + "/" + output.dwDay + "-" +
									output.dwEMachineNumber + "-" + output.dwTMachineNumber + "-" + 
									output.dwVerifyMode);

				//		}
					}
					
				
				}
			//	System.out.println("Total: " + i);

		//	}else
		//	{
				errorCode = (int)SBXPCProxy.GetLastError(numero_maquina).dwValue;
		//	}

			System.out.println(SysUtil.ErrorPrint(errorCode));

			ret = SBXPCProxy.EnableDevice(numero_maquina, true);

			System.out.println("Habilito dispositivo: " + ret);
			
		//	DB.commit(true, get_TrxName());
			
			ok = true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		//	try {
		//		DB.rollback(true, this.get_TrxName());
//			} catch (SQLException e) {
				// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
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

	protected String doIt() throws Exception
	{
		String value = "Termino Proceso correctamente";
		

		PreparedStatement d = null;
		ResultSet rs = null;
				
		try
		{

			String sql = "SELECT * FROM EMP_ConfiguracionControlBiometrico where isactive = 'Y'";
			
		//	d = DB.prepareStatement(sql,this.get_TrxName());
		//	rs = d.executeQuery();
			
			//while(rs.next())
		//	{
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
		//	}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			value = "Error: No Pudo Completar Proceso";
		}
		finally
		{
		//	DB.close(rs,d);
			rs = null;
			d  = null;			
		}
		
		return value;
	}

}