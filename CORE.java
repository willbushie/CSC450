import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

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
        broadcastUpdate("disconnection", IP);
    }
    
    /** 
     * Removes an IP address from the this.connectedIP list
     */
    public synchronized static void addIP(String IP)
    {
        connectedIP.add(IP);
        System.out.println(connectedIP);
        broadcastUpdate("connection", IP);
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
    public static void broadcastUpdate(String type, String IP)
    {
        System.out.println("Broadcasting to all known IPs an update");
        int PORT = 6682;
        for (int count = 0; count < connectedIP.size(); count++) 
        {
            Socket s = new Socket(connectedIP.get(count),PORT);
            PrintStream sendingInfo = new PrintStream(s.getOutputStream());
            Scanner receivingInfo = new Scanner(s.getInputStream());
            sendingInfo.println(type);
            sendingInfo.println(IP);
        }
    }




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