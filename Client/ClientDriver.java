import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;

public class ClientDriver
{
    //the below info would have been gleened from a .torrent file
    private static String TRACKERIP = "localhost";
    private static int TRACKERPORT = 6881;

    /**
     * Completely disconnect from the Tracker
     */
    /* private static void hardDisconnect()
    {

    } */

    public static void main(String[] args) throws Exception
    {
        String myIP = Inet4Address.getLocalHost().getHostAddress();
        // connect to the tracker & communicate
        Socket s = new Socket(TRACKERIP, TRACKERPORT);
        PrintStream sendingInfo = new PrintStream(s.getOutputStream());
        Scanner recievingInfo = new Scanner(s.getInputStream());

        // send action request
        System.out.println("sending action request...");
        sendingInfo.println("connect");

        // wait for response
        String response = "";
        while(true)
        {
            response = recievingInfo.nextLine();
            if(response.equals("ready for IP"))
            {
                System.out.println("receieved response: " + response);
                break;
            }
        }

        // once action is received, and ready for IP, send IP
        System.out.println("sending IP... " + myIP);
        sendingInfo.println(myIP);

        // after sending IP, prepare to receive list of IP addresses
        System.out.println("Waiting for IP List...");
        while(true)
        {
            response = recievingInfo.nextLine();
            if(response.equals("end") == false)
            {
                //System.out.println("Adding to list... " + response);
                if(response.equals(myIP) == false)
                {
                    ClientCORE.addIP(response);
                }
            }
            else if(response.equals("end"))
            {
                break;
            }
        }
        System.out.println(ClientCORE.returnIPList());
        // soft disconnect from tracker
        s.close();

        // listen for updates from the tracker if they come in
        ServerSocket listener = new ServerSocket(6682);
        while(true)
        {
            System.out.println("Preparing listener for when tracker reconnects...");
            (new CListener(listener.accept())).start();
        }

    
        /* String torrentName = "cambria.jpeg";
        PrintStream textOutputOverSocket = new PrintStream(s.getOutputStream());
        textOutputOverSocket.println(torrentName);
        textOutputOverSocket.println("ip address") //HOW DO WE GET OUT IP!!!!!!
        textOutputOverSocket.println("" + ClientCORE.getNextPortNumber()); */
    }
}