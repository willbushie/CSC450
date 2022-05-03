import java.io.DataOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class CORE 
{
    private static ArrayList<String> theConnectedClientIPs = new ArrayList<String>();
    private static ArrayList<PrintStream> theClientPrintStreams = new ArrayList<PrintStream>();
    private static int currentClientPort = 3000;
    
    public static synchronized void addPrintStream(PrintStream ps)
    {
        CORE.theClientPrintStreams.add(ps);
    }

    static public synchronized int getNextClientPort()
    {
        return CORE.currentClientPort++;
    }

    public static void broadcastStringToClients(String s)
    {
        for(PrintStream ps : CORE.theClientPrintStreams)
        {
            ps.println(s);
        }
    }

    public static String getConnectedClientIPsString()
    {
        String answer = "";
        for(int i = 0; i < CORE.theConnectedClientIPs.size(); i++)
        {
            if(answer.length()==0)
            {
                answer = answer + CORE.theConnectedClientIPs.get(i);
            }
            else
            {
                answer = answer + "," + CORE.theConnectedClientIPs.get(i);
            }
        }
        return answer;
    }

    public static synchronized void changeConnectedClientIPs(String ip, boolean shouldAdd)
    {
        //if shouldAdd is true, we are adding the ip, else we are removing the IP
        if(shouldAdd)
        {
            CORE.theConnectedClientIPs.add(ip);
        }
        else
        {
            for(int i = 0; i < CORE.theConnectedClientIPs.size(); i++)
            {
                if(CORE.theConnectedClientIPs.get(i).equals(ip))
                {
                    CORE.theConnectedClientIPs.remove(i);
                    return;
                }
            }
        }
    }
}
