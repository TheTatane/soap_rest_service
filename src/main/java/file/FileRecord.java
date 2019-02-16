package file;

import java.io.File;
import java.io.FileWriter;

public class FileRecord {

    private String name_file;
    public FileRecord(String name)
    {
        name_file=name;
    }

    public void saveXML(String data)
    {
        //Create file
        File file = new File(name_file);

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

    public String getName () { return name_file; }
}
