package app;

import bd.DataBase;
import io.spring.soap.getalbums.Album;
import io.spring.soap.getalbums.GetAlbumRequest;
import io.spring.soap.getalbums.GetAlbumResponse;
import io.spring.soap.getinfosongs.SongTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



import java.sql.*;
import java.util.ArrayList;

@Endpoint
public class AlbumsEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/SOAP/GetAlbums";

    @Autowired
    public AlbumsEndpoint() {

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAlbumRequest")
    @ResponsePayload
    public GetAlbumResponse getAlbbum(@RequestPayload GetAlbumRequest request) {
        GetAlbumResponse response = new GetAlbumResponse();

        // TEST
        ArrayList<Album> AlbumList = new ArrayList<Album>();
        ResultSet rs;
        System.out.println(request.getName());
        try
        {
            // TEST BDD
            DataBase bdd = new DataBase();
            bdd.connect();
            rs = bdd.execQuerry("SELECT DISTINCT al.title_album, al.year_release, ar.name_artist\n" +
                    "FROM Album al, AlbumContent ac, Artist ar\n" +
                    "WHERE al.id_album=ac.id_album\n" +
                    "AND ar.id_artist=al.id_artist\n" +
                    "AND ar.name_artist='"+request.getName()+"'");
            while (rs.next())
            {
                Album al = new Album();
                al.setNameAlbum(rs.getNString(1));
                al.setAnnee(rs.getInt(2));
                al.setNameArtiste(rs.getNString(3));
                AlbumList.add(al);
            }
            bdd.close();

        } catch (Exception e)
        {
            e.printStackTrace();;
        }

        if ((AlbumList.size() == 0))
        {
            //TO DO REST requête
            System.out.println("Not Found Album");
        }

        response.setAlbum(AlbumList);

        return response;
    }
}