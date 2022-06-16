package smack.comm.data;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


import smack.comm.data.GeneralLogData;


/**
 *
 * @author smackbio
 * @version 1.0
 */
public class GLogDataListModel extends AbstractTableModel
{
    final String[] COLUMN_TEXT = 
                    {
                        "PhotoNo",
                        "EnrollNo",
                        "EMachineNo",
                        "VeriMode",
                        "DateTime",
                        "DaiGong",
                    };
    List<GeneralLogData> logList;

    public void addSLogData(GeneralLogData logData)
    {
        logList.add(logData);
        fireTableRowsInserted(logList.size()-1, logList.size()-1);
    }

    public void removeAll()
    {
        logList.removeAll(logList);
        fireTableDataChanged();
    }

    public GLogDataListModel(){
        logList = new ArrayList<GeneralLogData>();
    }

    public int getRowCount() {
        return logList.size();
    }

    public int getColumnCount() {
        return COLUMN_TEXT.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        GeneralLogData logData = logList.get(rowIndex);
        
        int attendStatus, antipassStatus, daigong, verifyMode;
        
        antipassStatus = (int)logData.dwVerifyMode / 65536;
        daigong = antipassStatus / 4;
        antipassStatus = antipassStatus % 4;
        verifyMode = (int)logData.dwVerifyMode % 65536;
        attendStatus = verifyMode / 256;
        verifyMode = verifyMode % 256;
        
        String strAttendStatus, strAntipassStatus, strVerifyMode;
        switch(attendStatus)
        {
            case 0: strAttendStatus = "_Duty On";        break;
            case 1: strAttendStatus = "_Duty Off";       break;
            case 2: strAttendStatus = "_Overtime On";    break;
            case 3: strAttendStatus = "_Overtime Off";   break;
            case 4: strAttendStatus = "_Go In";          break;
            case 5: strAttendStatus = "_Go Out";         break;
            default: strAttendStatus = "";
        }
        
        switch(antipassStatus)
        {
            case 1: strAntipassStatus = ":AP_In";     break;
            case 2: strAntipassStatus = ":AP_Out";    break;
            default: strAntipassStatus = "";
        }
        
        switch(verifyMode)
        {
            case 0: strVerifyMode = "FP+ID"; break;
            case 1: strVerifyMode = "FP"; break;
            case 2: strVerifyMode = "Password"; break;
            case 3: strVerifyMode = "Card"; break;
            case 4: strVerifyMode = "FP+Card"; break;
            case 5: strVerifyMode = "FP+Pwd"; break;
            case 6: strVerifyMode = "Card+Pwd"; break;
            case 7: strVerifyMode = "FP+Card+Pwd"; break;
            case 10: strVerifyMode = "Hand Lock"; break;
            case 11: strVerifyMode = "Prog Lock"; break;
            case 12: strVerifyMode = "Prog Open"; break;
            case 13: strVerifyMode = "Prog Close"; break;
            case 14: strVerifyMode = "Auto Recover"; break;
            case 20: strVerifyMode = "Lock Over"; break;
            case 21: strVerifyMode = "Illegal Open"; break;
            case 22: strVerifyMode = "Duress alarm"; break;
            case 23: strVerifyMode = "Tamper detect"; break;
            case 51: strVerifyMode = "FP"; break;
            case 52: strVerifyMode = "Password"; break;
            case 53: strVerifyMode = "Password"; break;
            case 101: strVerifyMode = "FP"; break;
            case 102: strVerifyMode = "Password"; break;
            case 103: strVerifyMode = "Password"; break;
            case 151: strVerifyMode = "FP"; break;
            case 152: strVerifyMode = "Password"; break;
            case 153: strVerifyMode = "Password"; break;
            default: strVerifyMode = ""; break;
        }

        switch(columnIndex)
        {
            case 0:
                return (logData.dwTMachineNumber == -1? "No Photo" : logData.dwTMachineNumber);
            case 1:
                return logData.dwEnrollNumber;
            case 2:
                return logData.dwEMachineNumber;
            case 3:
            case 4:
            {
                NumberFormat formatter = new DecimalFormat("00");
                String ret = String.valueOf(logData.dwYear) + "/" + formatter.format(logData.dwMonth) + "/" + formatter.format(logData.dwDay) + " ";
                ret += formatter.format(logData.dwHour) + ":" + formatter.format(logData.dwMinute) + ":" + formatter.format(logData.dwSecond);
                return ret;
            }
            case 5:
                return strVerifyMode + strAttendStatus + strAntipassStatus;
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_TEXT[columnIndex];
    }
}
