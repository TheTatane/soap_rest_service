package rest;

import dom.Dom;
import java.io.*;
import java.net.*;
import file.FileRecord;

import java.util.Scanner;


public class RestService {

    public RestService()
    {

    }

    /* For LAST.FM */
    public String buildRequest (String name, String SAOPservice)
    {
        String method_API = "";
        switch (SAOPservice)
        {
            case "getSongsByAuthor" : method_API="artist.gettoptracks"; break;
            case "getAlbumsByAuthor" : method_API="artist.gettopalbums"; break;
            case "getInfoForSongTitle" : method_API="track.getInfo"; break;
            default: System.out.println("No match"); break;

        }
        return  "http://ws.audioscrobbler.com//2.0/?method="+method_API+"&artist="+name+"&api_key=99ee41d32b502e0928a4f8fedd78517c";
    }


    public void launchRest(String name)
    {
        try {

            /* TEST */
            String url_custom = buildRequest("VALD","getSongsByAuthor");
            URL url = new URL(url_custom);
            //Connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //Set GET mode
            con.setRequestMethod("GET");

            if(con.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                //Result
                Scanner scanner = new Scanner(new InputStreamReader(con.getInputStream()));
                String data="";

                while (scanner.hasNextLine()) {
                    data += scanner.nextLine() + "\n";
                }

                FileRecord fileXML = new FileRecord("save_data.xml");
                fileXML.saveXML(data);

                Dom dom = new Dom (fileXML);
                dom.domArtist();
            }
            else
                System.out.println("Error HTTP");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
