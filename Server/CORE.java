import java.io.PrintStream;
import java.util.ArrayList;

public class CORE 
{
    private static ArrayList<PrintStream> theClientStreams = new ArrayList<PrintStream>();
    
    public static void addClientThreadPrintStream(PrintStream ps)
    {
        System.out.println("adding client thread");
        CORE.theClientStreams.add(ps);
    }

    public static void removeClientThreadPrintStream(PrintStream ps)
    {
        System.out.print("removing client thread");
        CORE.theClientStreams.remove(ps);
    }

    public static void broadcastMessage(String message)
    {
        System.out.println("About to broadcast....");
        for (PrintStream ps : CORE.theClientStreams)
        {
            System.out.println(CORE.theClientStreams);
            ps.println(message);
        }
    }
}
