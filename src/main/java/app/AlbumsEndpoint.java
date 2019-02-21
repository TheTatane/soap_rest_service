package app;

import bd.DataBase;
import io.spring.soap.getalbums.Album;
import io.spring.soap.getalbums.GetAlbumRequest;
import io.spring.soap.getalbums.GetAlbumResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rest.RestService;


import java.sql.*;
import java.util.ArrayList;

@Endpoint
public class AlbumsEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/SOAP/GetAlbums";
    private ArrayList<Album> AlbumList = new ArrayList<Album>();
    @Autowired
    public AlbumsEndpoint() {

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAlbumRequest")
    @ResponsePayload
    public GetAlbumResponse getAlbbum(@RequestPayload GetAlbumRequest request) {
        GetAlbumResponse response = new GetAlbumResponse();

        // TEST

        execQuerry(request.getName());

        if ((AlbumList.size() == 0))
        {
            //TO DO REST requête
            RestService rest = new RestService();
            rest.launchRest(request.getName(),"getAlbumsByAuthor");

            execQuerry(request.getName());
        }

        response.setAlbum(AlbumList);

        return response;
    }

    private void execQuerry(String name)
    {
        ResultSet rs;
        try
        {
            DataBase bdd = new DataBase();
            bdd.connect();
            rs = bdd.execQuerry("SELECT DISTINCT al.title_album, ar.name_artist\n" +
                    "FROM Album al, Artist ar\n" +
                    "WHERE ar.id_artist=al.id_artist\n" +
                    "AND ar.name_artist='"+name+"'");
            while (rs.next())
            {
                Album al = new Album();
                al.setNameAlbum(rs.getNString(1));
                al.setAnnee(0);
                al.setNameArtiste(rs.getNString(2));
                AlbumList.add(al);
            }
            bdd.close();

        } catch (Exception e)
        {
            e.printStackTrace();;
        }
    }


}