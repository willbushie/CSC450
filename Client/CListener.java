import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;

public class CListener extends Thread
{
    private Socket tracker;
    private Scanner receivingInfo;
    private PrintStream sendingInfo; 

    /**
     * Start method for the thread - setting thread attribtues.
     */
    public CListener(Socket tracker)
    {
        try
        {
            this.tracker = tracker;
            this.sendingInfo = new PrintStream(this.tracker.getOutputStream());
            this.receivingInfo = new Scanner(this.tracker.getInputStream());
        }
        catch (Exception e)
        {
            System.err.println("An error occured.");
            e.printStackTrace();
        }
    }

    /**
     * Running method of the Client Listener
     */
    public void run()
    {
        System.out.println("Tracker has connected to conduct an update...");

        String message = "";
        while(true)
        {
            message = this.receivingInfo.nextLine();
            if(message.equals("disconnection"))
            {
                this.sendingInfo.println("ready for IP");
                message = this.receivingInfo.nextLine();
                ClientCORE.removeIP(message);
                break;
            }
            else if(message.equals("connection"))
            {
                this.sendingInfo.println("ready for IP");
                message = this.receivingInfo.nextLine();
                ClientCORE.addIP(message);
                break;
            }
        }

        System.out.print("Disconnecting from Tracker...");
    }
}