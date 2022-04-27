public class ClientCORE 
{
    private static int nextPort = 3000;
    
    public synchronized static int getNextPortNumber()
    {
        return nextPort++;
    }
}
