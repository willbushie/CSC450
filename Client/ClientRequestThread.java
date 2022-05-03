import java.net.Socket;

public class ClientRequestThread extends Thread
{
    private Socket s;

    public ClientRequestThread(Socket s)
    {
        this.s = s;
    }

    public void run()
    {
        System.out.println("Client Request Thread Started");
    }
}
