import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class chatServer
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket s = new ServerSocket(2222);
            System.out.println("Listenning for Connections...");
            //ArrayList<ChatWorkerThread> clients = new ArrayList<ChatWorkerThread>();
            while(true)
            {
                Socket client = s.accept(); //blocks
                DataInputStream chatInput = new DataInputStream(client.getInputStream());
                DataOutputStream chatOutput = new DataOutputStream(client.getOutputStream());
                ChatWorkerThread clientInstance = new ChatWorkerThread(client,chatInput,chatOutput);
                //clients.add(clientInstance);
                clientInstance.start();
            }
        }
        catch (Exception error)
        {
            System.err.println("There was an issue with server.");
            error.printStackTrace();
        }
    }
}