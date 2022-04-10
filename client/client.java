import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class client 
{
    public static void updateSharableDirectory()
    {
        ArrayList<Map> holdList = new ArrayList<Map>();
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
        System.out.println(holdList);
    }

    public static void main(String[] args) 
    {
        updateSharableDirectory();
    }
}