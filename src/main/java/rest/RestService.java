package rest;

import dom.AlbumType;
import dom.Dom;
import java.io.*;
import java.net.*;
import file.FileRecord;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Scanner;


public class RestService {

    private FileRecord fileXML;

    public RestService() { }

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


    public void launchRest(String name, String SAOPservice)
    {
        try {

            String url_custom = buildRequestMB(name, SAOPservice);
            URL url = new URL(url_custom);
            //Connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //Set GET mode
            con.setRequestMethod("GET");

            boolean ok = false;
            String data = "";

            //While result is not good
            while (!ok)
            {
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    //Result
                    Scanner scanner = new Scanner(new InputStreamReader(con.getInputStream()));

                    while (scanner.hasNextLine())
                    {
                        String XString = scanner.nextLine();
                        if (XString.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")) {
                            data += XString + "\n";
                            ok = true;
                        } else {
                            con = (HttpURLConnection) url.openConnection();
                            //Set GET mode
                            con.setRequestMethod("GET");
                        }

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

    public void parseDom (String name, String SAOPservice)
    {

        try {

            ArrayList<AlbumType> codeAlbums = new ArrayList<>();

            launchRest(name,SAOPservice);
            Dom dom = new Dom(fileXML, name);

            codeAlbums = dom.domArtistMb();
            System.out.println(codeAlbums);

            for(int i = 0 ; i<codeAlbums.size(); i++)
            {
                launchRest(codeAlbums.get(i).getId(),"getSongsByAlbums");
                Dom dom2 = new Dom(fileXML, name);
                dom2.domAlbumsMb(codeAlbums.get(i).getTitle());
            }

            System.out.println("END parseDom");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

}
