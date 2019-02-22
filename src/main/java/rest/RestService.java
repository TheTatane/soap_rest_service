package rest;

import XSL.XSLparser;
import bd.DataBase;
import dom.AlbumType;
import dom.Dom;
import java.io.*;
import java.net.*;
import file.FileRecord;

import javax.xml.parsers.ParserConfigurationException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import java.io.IOException;


public class RestService {

    private FileRecord fileXML;

    public RestService() { }

    /* For LastFM */
    public String buildRequestFM (String nameArtist, String nameAlbum, String SAOPservice)
    {
        String method_API = "";
        switch (SAOPservice)
        {
            //http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=cher&api_key=99ee41d32b502e0928a4f8fedd78517c&limit=4&fbclid=IwAR3vLd_7u5y2-szsiclvzFlnZOlKlm7IggrdKxLCaDm7dtrXIDTvKpX26VM
            case "getAlbumsByAuthor" : method_API="artist.gettopalbums&artist="+nameArtist+"&api_key=99ee41d32b502e0928a4f8fedd78517c&limit=4"; break;
            case "getSongsByAlbums" : method_API="album.getinfo&api_key=99ee41d32b502e0928a4f8fedd78517c&artist="+nameArtist+"&album="+nameAlbum; break;
            default: System.out.println("No match"); break;
        }
        return  "http://ws.audioscrobbler.com/2.0/?method="+method_API;
    }

    /* For MusicBrainz */
    public String buildRequestMB (String name, String SAOPservice)
    {
        String method_API = "";
        switch (SAOPservice)
        {
            case "getSongsByAlbums" : method_API="recording/?query=reid:"+name; break;
            case "getAlbumsByAuthor" : method_API="release/?query=artist:"+name+"&limit=4"; break;
            default: System.out.println("No match"); break;
        }
        return  "https://musicbrainz.org/ws/2/"+method_API;
    }


    public void launchRest(String nameArtist, String nameAlbum, String SAOPservice, String option)
    {
        try {

            String url_custom = "";

            if(option.equals("dom"))
                url_custom  = buildRequestMB(nameArtist, SAOPservice);
            else if(option.equals("xsl"))
                url_custom = buildRequestFM(nameArtist,nameAlbum, SAOPservice);
            else
                throw new IOException();

            System.out.println(url_custom);

            URL url = new URL(url_custom);
            //Connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //Set GET mode
            con.setRequestMethod("GET");

            boolean ok = false;


            //While result is not good
            while (!ok)
            {
                String data = "";
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    //Result
                    Scanner scanner = new Scanner(new InputStreamReader(con.getInputStream()));

                    while (scanner.hasNextLine())
                    {
                        String XString = scanner.nextLine();
                        if (XString.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"") && !XString.contains("{\"created\":\"")) {
                            ok = true;
                        } else {
                            con = (HttpURLConnection) url.openConnection();
                            //Set GET mode
                            con.setRequestMethod("GET");
                        }
                        data += XString;
                    }

                    if(ok)
                    {
                        fileXML = new FileRecord("save_data.xml");
                        fileXML.saveXML(data);
                    }
                }
                else
                {
                    System.out.println("Error HTTP");
                    throw new IOException();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseDom (String name, String SAOPservice, String option)
    {

        try {

            ArrayList<AlbumType> codeAlbums = new ArrayList<>();

            launchRest(name,"",SAOPservice,option);
            Dom dom = new Dom(fileXML, name);

            codeAlbums = dom.domArtistMb();
            System.out.println(codeAlbums);

            for(int i = 0 ; i<codeAlbums.size(); i++)
            {
                launchRest(codeAlbums.get(i).getId(),"","getSongsByAlbums",option);
                Dom dom2 = new Dom(fileXML, name);
                dom2.domAlbumsMb(codeAlbums.get(i).getTitle());
            }

            System.out.println("END parseDom");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void parseXSL(String nameArtist, String nameAlbum, String SAOPservice, String option)
    {
        launchRest(nameArtist,nameAlbum,SAOPservice,option);
        ArrayList<String> listAlbum = new ArrayList<>();

        try {

            XSLparser xsl1 = new XSLparser(fileXML);
            listAlbum = xsl1.parseAlbum(nameArtist);

            for(int i = 0 ; i<listAlbum.size(); i++)
            {
                launchRest(nameArtist,listAlbum.get(i),"getSongsByAlbums",option);
                XSLparser xsl2 = new XSLparser(fileXML);
                xsl2.parseTitle(listAlbum.get(i));
            }
            System.out.println("END parseXSL");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    }

}
