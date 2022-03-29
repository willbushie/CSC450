import java.net.*;
import java.io.*;

public class chatClient
{
	private static BufferedReader console = null;
   	private static BufferedReader streamOut = null;
    public static void main(String[] args) throws Exception
    {
        int portNum = 2222;
        Socket socket = null;
		socket = new Socket("localhost",portNum);
		console   = new BufferedReader(System.in);
      	streamOut = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while (true)
		{
			String line = console.readLine();
			streamOut.writeUTF(line);
			streamOut.flush();
		}
		/* console.close();
		streamOut.close();
		socket.close(); */
    }
}
