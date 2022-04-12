import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class client 
{
    // class attributes
    private ArrayList<Map<String,String>> shareableDirectory = new ArrayList<Map<String,String>>();
    private String status = ""; // can be either "sender" or "receiver"

    // reads the share directory and updates class attribute shareableDirectory
    public void updateSharableDirectory()
    {
        ArrayList<Map<String,String>> holdList = new ArrayList<Map<String,String>>();
        File share = new File("./share");
        File[] listOfFiles = share.listFiles();
        for (int i = 0; i < listOfFiles.length; i++)
        {
            Map<String,String> holder = new Hashtable<String,String>();
            if (listOfFiles[i].isFile())
            {
                holder.put("type", "file");
                holder.put("filename", listOfFiles[i].getName());
                holder.put("filepath", listOfFiles[i].toString());
                holdList.add(holder);
                //System.out.println("File: " + listOfFiles[i]);
            }
            else if (listOfFiles[i].isDirectory())
            {
                holder.put("type", "file");
                holder.put("filename", listOfFiles[i].getName());
                holder.put("filepath", listOfFiles[i].toString());
                holdList.add(holder);
                //System.out.println("Directory: " + listOfFiles[i]);
            }
        }
        this.shareableDirectory = holdList;
        //System.out.println(holdList);
    }

    // displays the items inside the sharable directory
    public void displayShareable()
    {
        for (int i = 0; i < this.shareableDirectory.size(); i++)
        {
            System.out.println(this.shareableDirectory.get(i));
        }
    }

    // checks for a file inside of the shareable directory
    public boolean checkForFile(String filename)
    {
        for (int i = 0; i < this.shareableDirectory.size(); i++)
        {
            if (this.shareableDirectory.get(i).containsValue(filename))
            {
                return true;
            }
        }
        return false;
    }

    // updates the status of the client (not used yet)
    public void updateStatus(String type)
    {
        this.status = type;
    }

    // sets the DataOutputStream to another client (to send/sender)
    public void setSender()
    {

    }

    // sets the DataInputStream from another client (to download/recieve)
    public void setReceiver()
    {

    }

    public static void main(String[] args) 
    {
        client c = new client();
        c.updateSharableDirectory();
        try
        {
            // create new socket and connect
            Socket s = new Socket("localhost",2222);
        }
        catch (Exception error){}
    }
}