package file;

import java.io.File;
import java.io.FileWriter;

public class FileRecord {

    public FileRecord()
    {

    }

    public void saveXML(String data)
    {
        //Create file
        File file = new File("data.xml");

        try{
            FileWriter fr = new FileWriter(file);
            System.out.println(data.length());
            fr.write(data);
            fr.close();
        }catch (Exception e)
        {
            System.out.println("File XML not saved");
            e.printStackTrace();
        }



    }
}
