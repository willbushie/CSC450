import java.io.*;
import java.net.*;

public class chatServer
{
    private static DataInputStream streamIn =  null;
    public static void main(String[] args) throws Exception
    {
        ServerSocket s = new ServerSocket(2222);
        System.out.println("Listenning for Connection...");
        Socket client = s.accept(); //blocks
        System.out.println("Connection Established...");
        //PrintStream clientOutput = new PrintStream(client.getOutputStream());
        //Scanner clientInput = new Scanner(client.getInputStream());
        //clientOutput.println("What is your name?");
        while (true)
        {
            streamIn = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            String line = streamIn.readUTF();
            System.out.println(line);
        }
        /* client.close();
        streamIn.close(); */
    }
}
