package XSL;

import bd.DataBase;
import file.FileRecord;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class XSLparser {

    private ArrayList<String> listAlbum;
    private FileRecord file_XML;
    TransformerFactory tFactory;
    Transformer transformer;
    Scanner scanner;
    DataBase db;

    public XSLparser(FileRecord file_XML)
    {
        listAlbum = new ArrayList<>();
        this.file_XML=file_XML;
        db = new DataBase();
    }

    public ArrayList<String> parseAlbum(String nameArtist)
    {

        try
        {

            tFactory = TransformerFactory.newInstance();
            transformer = tFactory.newTransformer(new StreamSource("get_albums.xsl"));
            transformer.transform(new StreamSource(file_XML.getName()), new StreamResult("data.txt"));
            scanner = new Scanner(new FileInputStream(new File("data.txt")));


            db.connect();

            ResultSet rs, generatedKeys;
            int id_artist = -1;
            rs = db.execQuerry("SELECT * FROM Artist WHERE name_artist ='"+nameArtist+"'");

            while (rs.next()) { id_artist = rs.getInt(1); }


            if (id_artist == -1)
            {
                generatedKeys = db.execPrepareQuerry("INSERT INTO Artist (name_artist) VALUES ('"+nameArtist+"')");
                if (generatedKeys.next())
                    id_artist = generatedKeys.getInt(1);
            }

            String albumResult="";
            while (scanner.hasNextLine())
            {
                albumResult=scanner.nextLine();
                if (!albumResult.contains("<?xml version=\"1.0\" encoding=\"UTF-8\""))
                {

                    listAlbum.add(albumResult);
                    System.out.println(albumResult);
                    albumResult = albumResult.replaceAll("\\'", " ");
                    db.execPrepareQuerry("INSERT IGNORE INTO Album (title_album, id_artist) VALUES ('"+albumResult+"',"+ id_artist +")");
                }
            }
            db.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return listAlbum;
    }

    public void parseTitle(String titleAlbum)
    {

        try
        {
            db.connect();

            tFactory = TransformerFactory.newInstance();
            transformer = tFactory.newTransformer(new StreamSource("song.xsl"));
            transformer.transform(new StreamSource("save_data.xml"), new StreamResult("data.txt"));
            scanner = new Scanner(new FileInputStream(new File("data.txt")));

            ResultSet rs;
            int id_album = -1;
            rs = db.execQuerry("SELECT * FROM Album WHERE title_album ='"+titleAlbum+"'");

            while (rs.next()) { id_album = rs.getInt(1); }

            String result = "";
            while (scanner.hasNextLine())
            {
                result = scanner.nextLine();
                if (!result.contains("<?xml version=\"1.0\" encoding=\"UTF-8\""))
                {
                    result = result.replaceAll("\\'", " ");
                    String[] tokens = result.split(";");
                    System.out.println(result);
                    db.execPrepareQuerry("INSERT IGNORE INTO Title (title, title_duration, id_album) VALUES ('"+tokens[0]+"','"+tokens[1]+"',"+id_album+")");
                }
            }

            db.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
