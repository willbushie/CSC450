import java.util.ArrayList;

public class ClientCORE 
{
    private static ArrayList<String> theConnectedClientIPs= new ArrayList<String>();

    public static void updateTheConnectedClientIPs(String commaDilimitedListOfIPs)
    {
        ClientCORE.theConnectedClientIPs.clear();
        String[] theIPs = commaDilimitedListOfIPs.split(",");
        for(int i = 0 ; i < theIPs.length; i++)
        {
            ClientCORE.theConnectedClientIPs.add(theIPs[i]);
        }
        System.out.println("Client IP List Updated");
    }
}
