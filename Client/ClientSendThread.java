import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSendThread extends Thread
{
    private Socket s;

    public ClientSendThread(Socket s)
    {
        this.s = s;
    }

    public void run()
    {
        try
        {
            System.out.println("Client Send Thread Started");
            Scanner input = new Scanner(this.s.getInputStream());
            DataOutputStream outputBytes = new DataOutputStream(this.s.getOutputStream());
            PrintStream outputText = new PrintStream(this.s.getOutputStream());

            while(true)
            {
                //wait for a request
                String byteNumber = input.nextLine(); //this line blocks

                //check to see if we have that byteNumber and response YES or NO
                boolean doIHaveByte = false;

                if(doIHaveByte)
                {
                    outputText.println("YES");
                    //actually send the byte
                    byte b = 5; //placeholder for the actual byte
                    outputBytes.writeByte(b);
                }
                else
                {
                    outputText.println("NO");
                }
            }
        }
        catch(Exception e){}
    }
}