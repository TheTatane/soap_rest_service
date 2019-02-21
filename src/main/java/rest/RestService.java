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
    public String buildRequestLFM (String name, String SAOPservice)
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


    /* For MusicBrainz */
    public String buildRequestMB (String name, String SAOPservice)
    {
        String method_API = "";
        switch (SAOPservice)
        {
            case "getSongsByAuthor" : method_API=""; break;
            case "getAlbumsByAuthor" : method_API=""; break;
            case "getInfoForSongTitle" : method_API=""; break;
            default: System.out.println("No match"); break;

        }
        return  "https://musicbrainz.org/ws/2/recording?query="+method_API;
    }


    public void launchRest(String name, String SAOPservice)
    {
        try {

            /* TEST */
            String url_custom = buildRequestLFM(name,SAOPservice);
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

                Dom dom = new Dom (fileXML, name);

                switch (SAOPservice)
                {
                    case "getSongsByAuthor" :  break;
                    case "getAlbumsByAuthor" :  dom.domArtist();  break;
                    case "getInfoForSongTitle" :  break;
                    default: System.out.println("No match"); break;

                }

            }
            else
                System.out.println("Error HTTP");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
