import java.util.ArrayList;

public class ClientCORE 
{
    private static int nextPort = 3000;
    

    // a list containing only the connected IPs
    private static ArrayList<String> connectedIP = new ArrayList<String>();

    /**
     * Add an IP address to the connectedIP list.
     */
    public synchronized static void addIP(String IP)
    {
        connectedIP.add(IP);
    }

    /**
     * Remove an IP address to the connectedIP list.
     */
    public synchronized static void removeIP(String IP)
    {
        connectedIP.remove(IP);
    }

    /**
     * Add an IP address to the connectedIP list.
     */
    public synchronized static ArrayList<String> returnIPList()
    {
        return connectedIP;
    }



    public synchronized static int getNextPortNumber()
    {
        return nextPort++;
    }
}