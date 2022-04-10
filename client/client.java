import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class client 
{
    private ArrayList<Map<String,String>> shareableDirectory = new ArrayList<Map<String,String>>();

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

    public void displayShareable()
    {
        for (int i = 0; i < this.shareableDirectory.size(); i++)
        {
            System.out.println(this.shareableDirectory.get(i));
        }
    }

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

    public static void main(String[] args) 
    {
        client c = new client();
        c.updateSharableDirectory();
    }
}