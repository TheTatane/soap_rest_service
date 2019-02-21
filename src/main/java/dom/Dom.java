package dom;


import bd.DataBase;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import file.FileRecord;
import org.w3c.dom.*;


public class Dom {

    private DocumentBuilder builder;
    private Document document;
    private FileRecord file_XML;
    private ArrayList<AlbumType> listAlbums;
    private ArrayList<String> listAlbumsTitle;
    private ArrayList<String> listAlbumsCode;
    private ArrayList<String> listTitle;
    private String nameRequest;

    public Dom (FileRecord file_XML, String name) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();

        listTitle = new ArrayList<>();
        listAlbums = new ArrayList<>();
        listAlbumsTitle = new ArrayList<>();
        listAlbumsCode = new ArrayList<>();
        nameRequest=name;
        this.file_XML = file_XML;
    }

    public ArrayList<AlbumType> domArtistMb()
    {
        try {

            document= builder.parse(new File(file_XML.getName()));

            //TEST Affiche la version de XML
            System.out.println(document.getXmlVersion());


            //TEST Affiche la racine récupérée
            Element racine = document.getDocumentElement();
            System.out.println(racine.getNodeName());

            String XMLschema = afficheAlbums(racine, "");
            String insertQuerry ="";
            System.out.println(listAlbumsTitle.size());
            System.out.println(listAlbumsCode.size());

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

            //Display
            listAlbumsTitle.forEach((n) -> System.out.println("Album : " + n));

            int finalId_artist = id_artist;

            for (int i=0; i<4;i++)
                db.execPrepareQuerry("INSERT IGNORE INTO Album (title_album, id_artist) VALUES ('"+listAlbumsTitle.get(i)+"',"+ finalId_artist +")");

            for (int i = 0 ; i<listAlbumsCode.size(); i++)
            {
                AlbumType al = new AlbumType();
                al.setTitle(listAlbumsTitle.get(i));
                al.setId(listAlbumsCode.get(i));
                listAlbums.add(al);
            }

            db.close();


        } catch (Exception e)
        {
            e.printStackTrace();
        }


        return listAlbums;
    }


    private String afficheAlbums(Node nd, String tab){
        String str = "";

        if(nd instanceof Element){

            Element element = (Element)nd;

            if(nd.getNodeName().equals("release") && nd.getAttributes() != null && nd.getAttributes().getLength() > 0)
            {
                System.out.println("OK ID");
                //Récupération de la liste des attributs
                NamedNodeMap ListeAttributs = nd.getAttributes();

                //Affichage des attributs pour chaque noeud
                for(int i = 0; i < ListeAttributs.getLength(); i++)
                {
                    Node noeud = ListeAttributs.item(i);
                    if (noeud.getNodeName().equals("id"))
                        listAlbumsCode.add(noeud.getNodeValue());
                }
            }

            if(nd.getChildNodes().getLength() == 1)
            {
                System.out.println("OK Node");
                if(nd.getParentNode().getNodeName().equals("release-group") && nd.getNodeName().equals("title"))
                {
                    System.out.println("OK Node"+ nd.getTextContent());
                    listAlbumsTitle.add(nd.getTextContent());
                }

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
                    str += "\n " + tab2 + afficheAlbums(nd2, tab2);
            }
        }

        return str;
    }

    public void domAlbumsMb(String titleAlbum)
    {
        try {

            document= builder.parse(new File(file_XML.getName()));

            //TEST Affiche la version de XML
            System.out.println(document.getXmlVersion());


            //TEST Affiche la racine récupérée
            Element racine = document.getDocumentElement();
            System.out.println(racine.getNodeName());

            String XMLschema = afficheTitle(racine, "");
            AtomicReference<String> insertQuerryTitle = new AtomicReference<>("");

            //Insert Artiste if not in DataBase
            ResultSet rs;
            ResultSet generatedKeys;
            int id_album = -1;
            DataBase db = new DataBase();
            db.connect();

            //Display
            listTitle.forEach((n) -> System.out.println("Titles : " + n));

            rs = db.execQuerry("SELECT * FROM Album WHERE title_album ='"+titleAlbum+"'");

            while (rs.next()) { id_album = rs.getInt(1); }

            System.out.println(id_album);

            int finalid_album = id_album;

            listTitle.forEach((n) -> {
                try {
                    db.execPrepareQuerry("INSERT IGNORE INTO Title (title, title_duration, id_album) VALUES ('"+n+"','none',"+finalid_album+")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            db.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private String afficheTitle(Node nd, String tab){
        String str = "";

        if(nd instanceof Element){

            Element element = (Element)nd;

            if(nd.getChildNodes().getLength() == 1)
            {
                System.out.println("OK Node 2");
                if(nd.getParentNode().getNodeName().equals("recording") && nd.getNodeName().equals("title"))
                {
                    System.out.println("OK Node Title "+ nd.getTextContent());
                    listTitle.add(nd.getTextContent());
                }

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
                    str += "\n " + tab2 + afficheTitle(nd2, tab2);
            }
        }

        return str;
    }

}
