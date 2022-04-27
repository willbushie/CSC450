import java.net.Socket;

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
        
        //get the IP address of our connect client
        //add it to our list of peers, then broadcast
        //the current list of peers to this connected client
        //as well as all previous clients
    }
}
