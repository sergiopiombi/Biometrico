package smack.comm.input;

/**
 *
 * @author smackbio
  * @version 1.0
*/
public class SetUserNameInput extends GetUserNameInput{
    private String lpszUserName;
    public long nameLength;
    
    public void setUserName(String userName)
    {
        lpszUserName = userName;
        nameLength = lpszUserName.length();
    }
    
    public String getUserName() { return lpszUserName; }
}
