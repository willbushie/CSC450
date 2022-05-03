import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TrackerThread extends Thread
{
    private Socket theClient;

    public TrackerThread(Socket theClient)
    {
        this.theClient = theClient;
    }

    public void run()
    {
        System.out.println("Tracker Thread Started....");
        try
        {
            Scanner clientInput = new Scanner(this.theClient.getInputStream());
            PrintStream clientOutput = new PrintStream(this.theClient.getOutputStream());
            String newClientIP = clientInput.nextLine();
            int newClientPortNumber = CORE.getNextClientPort();
            clientOutput.println(newClientPortNumber);
            String newClientIP_Port = newClientIP + ":" + newClientPortNumber;
            CORE.changeConnectedClientIPs(newClientIP_Port, true);
            String connectedClients = CORE.getConnectedClientIPsString();
            clientOutput.println(connectedClients);
            CORE.broadcastStringToClients(connectedClients);
            CORE.addPrintStream(clientOutput);

            while(true){}
            //get the IP address of our connect client
            //add it to our list of peers, then broadcast
            //the current list of peers to this connected client
            //as well as all previous clients
        }
        catch(Exception e){}
       
    }
}
