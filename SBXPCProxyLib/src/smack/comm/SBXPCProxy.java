package smack.comm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import smack.comm.input.*;
import smack.comm.output.*;

/**
 * SBXPCProxy wraps SBXPCDLL's all functions
 * 
 * @author Smackbio
 * @version 1.0
 * 
 */

public class SBXPCProxy {
    private static List<SBXPCXMLEventListener> listenerList = new ArrayList<SBXPCXMLEventListener>();
    
    public static native boolean SetMachineType(long dwMachineNumber, String lpszMachineType);
    public static native GetEnrollDataOutput GetEnrollData(GetEnrollDataInput input);
    public static native boolean SetEnrollData(SetEnrollDataInput input);
    public static native boolean DeleteEnrollData(GetEnrollDataInput input);
    public static native boolean ReadSuperLogData(long dwMachineNumber, boolean bReadMark);
    public static native SuperLogDataOutput GetSuperLogData(long dwMachineNumber);
    public static native boolean ReadGeneralLogData(long dwMachineNumber, boolean bReadMark);
    public static native GeneralLogDataOutput GetGeneralLogData(long dwMachineNumber);
    public static native boolean ReadAllSLogData(long dwMachineNumber);
    public static native SuperLogDataOutput GetAllSLogData(long dwMachineNumber);
    public static native boolean ReadAllGLogData(long dwMachineNumber);
    public static native GeneralLogDataOutput GetAllGLogData(long dwMachineNumber);
    public static native OneLongOutput GetDeviceStatus(DeviceInfoStatusInput input);
    public static native OneLongOutput GetDeviceInfo(DeviceInfoStatusInput input);
    public static native boolean SetDeviceInfo(DeviceInfoStatusInput input, long dwValue);
    public static native boolean EnableDevice(long dwMachineNumber, boolean bFlag);
    public static native boolean EnableUser(GetEnrollDataInput userInfo, boolean bFlag);
    public static native GetDeviceTimeOutput GetDeviceTime(long dwMachineNumber);
    public static native boolean SetDeviceTime(long dwMachineNumber);
    public static native boolean PowerOnAllDevice(long dwMachineNumber);
    public static native boolean PowerOffDevice(long dwMachineNumber);
    public static native boolean ModifyPrivilege(GetEnrollDataInput userInfo, long dwMachinePrivilege);
    public static native boolean ReadAllUserID(long dwMachineNumber);
    public static native GetAllUserIDOutput GetAllUserID(long dwMachineNumber);
    public static native OneStringOutput GetSerialNumber(long dwMachineNumber);
    public static native long GetBackupNumber(long dwMachineNumber);
    public static native OneStringOutput GetProductCode(long dwMachineNumber);
    public static native boolean ClearKeeperData(long dwMachineNumber);
    public static native boolean EmptyEnrollData(long dwMachineNumber);
    public static native boolean EmptyGeneralLogData(long dwMachineNumber);
    public static native boolean EmptySuperLogData(long dwMachineNumber);
    public static native OneStringOutput GetUserName(GetUserNameInput input);
    public static native boolean SetUserName(SetUserNameInput input);
    public static native OneStringOutput GetCompanyName(long dwMachineNumber);
    public static native boolean SetCompanyName(long dwMachineNumber, long vKind, String CompanyName);
    public static native OneLongOutput GetDoorStatus(long dwMachineNumber);
    public static native boolean SetDoorStatus(long dwMachineNumber, long dwValue);
    public static GetBellTimeOutput GetBellTime(long dwMachineNumber, long bellSettingCount)
    {
        long settingLength = bellSettingCount * 3;
        return GetBellTime(dwMachineNumber, settingLength, true);        
    }
    private static native GetBellTimeOutput GetBellTime(long dwMachineNumber, long bellSettingCount, boolean isLengthOrCount);
    public static boolean SetBellTime(long dwMachineNumber, long dwValue, byte[] dwBellInfo)
    {
        return SetBellTime(dwMachineNumber, dwValue, dwBellInfo, dwBellInfo.length);
    }
    private static native boolean SetBellTime(long dwMachineNumber, long dwValue, byte[] dwBellInfo, long length);
    
    public static native boolean ConnectSerial(long dwMachineNumber, long dwCommPort, long dwBaudRate);
    public static native boolean ConnectTcpip(long dwMachineNumber, String lpszIPAddress, long dwPortNumber, long dwPassWord);
    public static native void Disconnect(long dwMachineNumber);
    public static native OneLongOutput GetLastError(long dwMachineNumber);
    public static native OneStringOutput GeneralOperationXML(long dwMachineNumber, String lpszReqNResXML);
    public static native GetDeviceLongInfoOutput GetDeviceLongInfo(long dwMachineNumber, long dwInfo);
    private static native boolean SetDeviceLongInfo(long dwMachineNumber, long dwInfo, int[] dwValue, long size);
    public static boolean SetDeviceLongInfo(long dwMachineNumber, long dwInfo, int[] dwValue)
    {
        return SetDeviceLongInfo(dwMachineNumber, dwInfo, dwValue, dwValue.length);
    }
    public static native boolean ModifyDuressFP(long dwMachineNumber, long dwEnrollNumber, long dwBackupNumber, long dwDuressSetting);
    public static native OneStringOutput GetMachineIP( String lpszProduct, long dwMachineNumber );
    public static native OneStringOutput GetDepartName(long dwMachineNumber, long dwDepartNumber, long dwDepartOrDaigong);
    public static native boolean SetDepartName(long dwMachineNumber, long dwDepartNumber, long dwDepartOrDaigong, String lpszDepartName);

    public static native boolean StartEventCapture( long dwCommType, long dwParam1, long dwParam2 );
    public static native void StopEventCapture();

    //xml helper interfaces
    public static native int XML_ParseInt(String lpszXML, String lpszTagname);
    public static native long XML_ParseLong(String lpszXML, String lpszTagname);
    public static native boolean XML_ParseBoolean(String lpszXML, String lpszTagname);
    public static native OneStringOutput XML_ParseString(String lpszXML, String lpszTagname);
    public static native XMLParseBinaryByteOutput XML_ParseBinaryByte(String lpszXML, String lpszTagname, long dwLen);
    public static native XMLParseBinaryShortOutput XML_ParseBinaryWord(String lpszXML, String lpszTagname, long dwLen);
    public static native XMLParseBinaryLongOutput XML_ParseBinaryLong(String lpszXML, String lpszTagname, long dwLen);
    public static native OneStringOutput XML_ParseBinaryUnicode(String lpszXML, String lpszTagname, long dwLen);

    public static native OneStringOutput XML_AddInt(String lpszXML, String lpszTagname, int nValue);
    public static native OneStringOutput XML_AddLong(String lpszXML, String lpszTagname, long dwValue);
    public static native OneStringOutput XML_AddBoolean(String lpszXML, String lpszTagname, boolean bValue);
    public static native OneStringOutput XML_AddString(String lpszXML, String lpszTagname, String lpszValue);
    public static native OneStringOutput XML_AddBinaryByte(String lpszXML, String lpszTagname, byte[] dwData, long dwLen);
    public static native OneStringOutput XML_AddBinaryWord(String lpszXML, String lpszTagname, short[] dwData, long dwLen);
    public static native OneStringOutput XML_AddBinarylong(String lpszXML, String lpszTagname, int[] dwData, long dwLen);
    public static native OneStringOutput XML_AddBinaryUnicode(String lpszXML, String lpszTagname, String lpszData);
    public static native OneStringOutput XML_AddBinaryGlyph(String lpszXML, String lpszMessage, long width, long height, String lpszFontDescriptor);

    protected static void fireXMLEvent(String xml)
    {
        Iterator<SBXPCXMLEventListener> iter = listenerList.iterator();
        while(iter.hasNext())
            iter.next().OnReceiveEventXML(xml);
    }
    
    public static void addXMLListener(SBXPCXMLEventListener listener)
    {
        listenerList.add(listener);
    }
    
    public static Object removeXMLListener(SBXPCXMLEventListener listener)
    {
        return listenerList.remove(listener);
    }
    
    static
    {
    	try
       	{
       		//System.loadLibrary("SBXPCJavaProxy");
       		 System.load("C:/d/ControlHorario/jar/SBXPCJavaProxy.dll");
       	}
       	catch(Exception ex)
       	{
       		ex.printStackTrace();
       		System.out.println("No Puede Cargar libreria SBXPCJavaProxy.dll");
       	}
    }
}
