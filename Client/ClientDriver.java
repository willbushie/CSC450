
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
        //needs to tell the tracker about his IP address
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        /*
        String torrentName = "cambria.jpeg";
        PrintStream textOutputOverSocket = new PrintStream(s.getOutputStream());
        textOutputOverSocket.println(torrentName);
        textOutputOverSocket.println("ip address") //HOW DO WE GET OUT IP!!!!!!
        textOutputOverSocket.println("" + ClientCORE.getNextPortNumber());
        */
    }
}
