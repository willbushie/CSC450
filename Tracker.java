import java.net.ServerSocket;
import java.net.Socket;

public class Tracker 
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket listener = new ServerSocket(6881);
        while(true)
        {
            (new TrackerThread(listener.accept())).start();
        }
    }
}
