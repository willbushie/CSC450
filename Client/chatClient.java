import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class chatClient 
{
    public static void main(String[] args)
    {
        try
        {    
            Socket s = new Socket("localhost", 2222); 
            boolean keepConnection = true;
            Scanner scn = new Scanner(System.in);
            DataInputStream clientInput = new DataInputStream(s.getInputStream());
            DataOutputStream clientOutput = new DataOutputStream(s.getOutputStream());
            while(true)
            {
                /* Scanner clientInput = new Scanner(s.getInputStream());
                String prompt = clientInput.nextLine();
                System.out.println(prompt);
                Scanner localInput = new Scanner(System.in);
                if (localInput.equals("/exit"))
                {
                    keepConnection = false;
                }
                else
                {
                    PrintStream clientOutput = new PrintStream(s.getOutputStream());
                    clientOutput.println(localInput.nextLine());    
                } */

                System.out.println(clientInput.readUTF()); //System.out.println(dis.readUTF())
                String toSend = scn.nextLine();
                if (toSend.equals("/exit"))
                {
                    s.close();
                    scn.close();
                    clientInput.close();
                    clientOutput.close();
                    break;
                }
                else
                {
                    clientOutput.writeUTF(toSend);
                }
            }
        }
        catch(Exception error)
        {
            System.err.println("There was an issue with the client application.");
            error.printStackTrace();
        }
    }
}