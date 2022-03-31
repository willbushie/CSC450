import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatWorkerThread extends Thread
{
    /* private Socket theClientSocket;
    private PrintStream clientOutput;
    private Scanner clientInput; */
    private Socket clientSocket;
    private DataOutputStream clientOutput;
    private DataInputStream clientInput;

    public ChatWorkerThread(ServerSocket clientSocket, DataInputStream chatInput, DataOutputStream chatOutput)
    {
        /* try 
        {
            System.out.println("Client connection Established with server...");
            this.theClientSocket = theClientSocket;
            this.clientOutput = new PrintStream(this.theClientSocket.getOutputStream());    
            this.clientInput = new Scanner(this.theClientSocket.getInputStream());
        } 
        catch (Exception error) 
        {
            System.err.println("Client thread creation was unsuccessful.");
            error.printStackTrace();
        } */
        try
        {
            System.out.println("Client connection established with server...");
            this.clientSocket = clientSocket;
            this.clientInput = chatInput;
            this.clientOutput = chatOutput;
        }
        catch (Exception error) 
        {
            System.err.println("Client thread creation was unsuccessful.");
            error.printStackTrace();
        }
    }

    public void run()
    {
        /* try
        {
            this.clientOutput.println("Connected to server...");         
            while(true)
            {
                String message = clientInput.nextLine();
                System.out.println("read message: " + message);   
                this.clientOutput.println(message); 
            }
        }
        catch (Exception disconnect)
        {
            System.err.println("Client disconnected from server.");
        } */
        try
        {
            this.clientOutput.writeUTF("Connected to server...");         
            while(true)
            {
                String message = clientInput.readUTF();
                System.out.println("read message: " + message);   
                this.clientOutput.writeUTF(message); 
            }
        }
        catch (Exception disconnect)
        {
            System.err.println("Client disconnected from server.");
        }
    }
}