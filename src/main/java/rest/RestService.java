package rest;

import java.io.*;
import java.net.*;
import file.FileRecord;
import jdk.nashorn.internal.runtime.Context;

import java.util.Scanner;


public class RestService {

    public RestService()
    {

    }

    public void launchRest(String name)
    {
        try {

            /* TEST */
            URL url = new URL("http://ws.audioscrobbler.com//2.0/?method=artist.gettoptracks&artist=U2&api_key=99ee41d32b502e0928a4f8fedd78517c");
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

                FileRecord fileXML = new FileRecord("data.xml");
                fileXML.saveXML(data);
            }
            else
                System.out.println("Error HTTP");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
