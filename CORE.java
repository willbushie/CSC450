import java.io.DataOutputStream;
import java.util.ArrayList;

public class CORE 
{
    private static ArrayList<DataOutputStream> theClientDOSsss= new ArrayList<DataOutputStream>();




    // connected IPs are placed inside of this list
    private static ArrayList<String> connectedIP = new ArrayList<String>();

    /** 
     * Removes an IP address from the this.connectedIP list
     */
    public synchronized static void removeIP(String IP)
    {
        connectedIP.remove(IP);
        System.out.println(connectedIP);
    }
    
    /** 
     * Removes an IP address from the this.connectedIP list
     */
    public synchronized static void addIP(String IP)
    {
        connectedIP.add(IP);
        System.out.println(connectedIP);
    }

    /**
     * Return the list of IPs
     */
    public static ArrayList<String> returnListOfIP()
    {
        return connectedIP;
    }

    /** 
     * Removes an IP address from the this.connectedIP list
     */
    /* private static void broadcastUpdate(String type, String IPtoAction)
    {
        
    } */




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
