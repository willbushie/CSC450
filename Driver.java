import java.io.*;
import java.net.*;

public class Driver
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket connection = new ServerSocket(2222);
        while(true)
        {   
            Socket client = connection.accept();
            TorrentThread tt = new TorrentThread(client);
            tt.start();
        }
        /*
        FileInputStream fis;
        DataInputStream dis;
        FileOutputStream fos;
        DataOutputStream dos;

        try
        {
            fis = new FileInputStream("cambria.jpeg");
            dis = new DataInputStream(fis);
            fos = new FileOutputStream("cambriaCopy.jpeg");
            dos = new DataOutputStream(fos);
            while(true)
            {
                byte b = dis.readByte();
                dos.writeByte(b);
            }
        }
        catch(EOFException e)
        {
            System.out.println("End of File");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        */
    }
}