import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;
import java.lang.reflect.Array;

public class TrackerThread extends Thread
{
    private Socket theClient;
    private Scanner receivingInfo;
    private PrintStream sendingInfo;

    /**
     * Start method for the thread - setting thread attributes.
     */
    public TrackerThread(Socket theClient)
    {
        try
        {
            this.theClient = theClient;
            this.sendingInfo = new PrintStream(this.theClient.getOutputStream());
            this.receivingInfo = new Scanner(this.theClient.getInputStream());
        }
        catch (Exception e)
        {
            System.err.println("An error occured.");
            e.printStackTrace();
        }
    }

    /**
     * Running method of the tracker.
     * Spun up upon request when a (new/existing) Client connects wanting to connect/disconnet.
     */
    public void run()
    {
        System.out.println("Tracker Thread Started....");

        // receive action from client (connect/disconnect)
        String message = "";
        while(true)
        {
            message = this.receivingInfo.nextLine();
            if(message.equals("connect"))
            {
                //System.out.println("action is: " + message);
                sendingInfo.println("ready for IP");
                message = this.receivingInfo.nextLine();
                //System.out.println("received IP: " + message);
                CORE.addIP(message);
                break;
            }
            else if(message.equals("disconnect"))
            {
                //System.out.println("action is: " + message);
                sendingInfo.println("ready for IP");
                message = this.receivingInfo.nextLine();
                //System.out.println("received IP: " + message);
                CORE.removeIP(message);
                break;
            }
        }

        // send the list of connected IPs over to the client
        ArrayList<String> IPList = new ArrayList<String>();
        IPList = CORE.returnListOfIP();
        System.out.println("Sending IP List...");
        for (int count = 0; count < IPList.size(); count++) 
        {
            sendingInfo.println(IPList.get(count));
        }
        sendingInfo.println("end");
        System.out.println("IP List fully sent...");

        System.out.println("Thread exiting...");
    }
}