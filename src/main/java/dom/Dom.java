package dom;


import bd.DataBase;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import file.FileRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Dom {

    FileRecord file_XML;
    ArrayList<String> listAlbums;
    String nameRequest;

    public Dom (FileRecord file_XML, String name)
    {
        listAlbums = new ArrayList<String>();
        nameRequest=name;
        this.file_XML = file_XML;
    }

    public void domArtist ()
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document= builder.parse(new File(file_XML.getName()));

            //TEST Affiche la version de XML
            System.out.println(document.getXmlVersion());

            //TEST Affiche l'encodage
            System.out.println(document.getXmlEncoding());

            //TEST Affiche la racine récupérée
            Element racine = document.getDocumentElement();
            System.out.println(racine.getNodeName());

            String XMLschema = affiche(racine, "");
            String insertQuerry ="";
            System.out.println(listAlbums.size());

            //Insert Artiste if not in DataBase
            ResultSet rs;
            ResultSet generatedKeys;
            int id_artist = -1;
            DataBase db = new DataBase();
            db.connect();

            rs = db.execQuerry("SELECT * FROM Artist WHERE name_artist ='"+nameRequest+"'");

            while (rs.next()) { id_artist = rs.getInt(1); }


            if (id_artist == -1)
            {
                generatedKeys = db.execPrepareQuerry("INSERT INTO Artist (name_artist) VALUES ('"+nameRequest+"')");
                if (generatedKeys.next())
                    id_artist = generatedKeys.getInt(1);
            }

            //Affichage
            listAlbums.forEach((n) -> System.out.println("Album : " + n));

            int finalId_artist = id_artist;

            for (int i=0; i<4;i++)
                db.execPrepareQuerry("INSERT IGNORE INTO Album (title_album, id_artist) VALUES ('"+listAlbums.get(i)+"',"+ finalId_artist +")");
               // insertQuerry+="INSERT IGNORE INTO Album (title_album, id_artist) VALUES ('"+listAlbums.get(i)+"',"+ finalId_artist +");\n";

            //listAlbums.forEach((n) -> insertQuerry.set(insertQuerry + "INSERT IGNORE INTO Album (title_album, id_artist) VALUES ('"+n+"',"+ finalId_artist +")\n"));
            //System.out.println(insertQuerry);




        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String affiche(Node nd, String tab){
        String str = "";

        if(nd instanceof Element){

            Element element = (Element)nd;

            if(nd.getNodeName().equals("album"))
                str += " [YEEES] ";

            if(nd.getChildNodes().getLength() == 1)
            {
                if(nd.getParentNode().getNodeName().equals("album") && nd.getNodeName().equals("name"))
                    listAlbums.add(nd.getTextContent());
            }


            //Récupére liste des Noeud enfants du noeud parcouru
            NodeList list = nd.getChildNodes();
            String tab2 = tab + "\t";

            //Parcours de la liste des nœuds
            for(int i = 0; i < nd.getChildNodes().getLength(); i++)
            {
                Node nd2 = list.item(i);

                if (nd2 instanceof Element)
                    //Appel récursif à la méthode pour le traitement du nœud et de ses enfants
                    str += "\n " + tab2 + affiche(nd2, tab2);
            }
        }

        return str;
    }


}
