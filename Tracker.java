import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Tracker 
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket listener = new ServerSocket(6881);
        System.out.println("Ready for oncoming connections...");
        while(true)
        {
            (new TrackerThread(listener.accept())).start();
        }
    }
}
