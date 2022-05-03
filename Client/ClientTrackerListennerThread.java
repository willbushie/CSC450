import java.util.Scanner;

public class ClientTrackerListennerThread extends Thread
{
    private Scanner trackerInput;

    public ClientTrackerListennerThread(Scanner trackerInput)
    {
        this.trackerInput = trackerInput;
    }

    public void run()
    {
        //we want to constantly listen for a new incoming list of connected IPs
        while(true)
        {
            String listOfIPS = this.trackerInput.nextLine();
            System.out.println(listOfIPS);
            ClientCORE.updateTheConnectedClientIPs(listOfIPS);
        }
    }
}
