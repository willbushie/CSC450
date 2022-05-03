import java.net.ServerSocket;
import java.net.Socket;

public class ClientServerThread extends Thread 
{
    private int listennnnningPort;

    public ClientServerThread(int portNumber)
    {
        this.listennnnningPort = portNumber;
    }

    public void run()
    {
        try
        {
            ServerSocket ss = new ServerSocket(this.listennnnningPort);
            //listen for incoming peer connections
            while(true)
            {
                Socket aNewPeer = ss.accept();
                (new ClientSendThread(aNewPeer)).start(); //listens for incoming byte requests
                (new ClientRequestThread(aNewPeer)).start(); //sends byte requests to other clients
            }
        }
        catch(Exception e)
        {
            //whatever
        }
         
    }

}
