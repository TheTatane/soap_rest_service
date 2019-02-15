package rest;

import java.io.*;
import java.net.*;
import file.FileRecord;
import java.util.Scanner;


public class RestService {

    public RestService()
    {

    }

    public void launchRest(String name)
    {
        try {

            /* TEST */
            URL url = new URL("http://ws.audioscrobbler.com//2.0/?method=chart.getTopArtists&api_key=99ee41d32b502e0928a4f8fedd78517c");
            //Connection
            URLConnection urlc = url.openConnection();

            //use post mode
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            //Send query
            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.close();

            //Result
            Scanner scanner = new Scanner(new InputStreamReader(urlc
                    .getInputStream()));
            String data="";

            while (scanner.hasNextLine()) {
                data += scanner.nextLine() + "\n";
            }


            FileRecord fileXML = new FileRecord();
            fileXML.saveXML(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
