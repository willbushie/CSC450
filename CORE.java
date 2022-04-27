import java.io.DataOutputStream;
import java.util.ArrayList;

public class CORE 
{
    private static ArrayList<DataOutputStream> theClientDOSsss= new ArrayList<DataOutputStream>();

    public synchronized static void addDOS(DataOutputStream dos)
    {
        CORE.theClientDOSsss.add(dos);
    }
    
    public static synchronized void removeReceivers()
    {
        for(DataOutputStream dos : CORE.theClientDOSsss)
        {
            try {
                dos.close();
            } catch (Exception e) {
                //TODO: handle exception
            }
            
        }
        CORE.theClientDOSsss.clear();
    }

    public synchronized static void broadCastByte(byte b)
    {
        try
        {
            for(DataOutputStream dos : CORE.theClientDOSsss)
            {
                dos.writeByte(b);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
