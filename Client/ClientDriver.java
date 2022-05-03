import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Scanner;

public class ClientDriver 
{
    //the below info would have been gleened from a .torrent file
    private static String TRACKERIP = "localhost";
    private static int TRACKERPORT = 6881;

    public static void main(String[] args) throws Exception
    {
        Socket s = new Socket(TRACKERIP, TRACKERPORT);
        Scanner socketInput = new Scanner(s.getInputStream());
        PrintStream socketOutput = new PrintStream(s.getOutputStream());
        socketOutput.println(Inet4Address.getLocalHost().getHostAddress());
        String myPortNumber = socketInput.nextLine();
        String listOfConnectedClients = socketInput.nextLine();
        ClientCORE.updateTheConnectedClientIPs(listOfConnectedClients);
        
        //maintain the connection with the tracker to get updates on
        //connected clients
        (new ClientTrackerListennerThread(socketInput)).start();
        
        //create a server thread to establish connections with the Swarm
        (new ClientServerThread(Integer.parseInt(myPortNumber))).start();

        //In our main thread here, we want to start the torrent process of sharing bits
        while(true){}
        
    }
}
