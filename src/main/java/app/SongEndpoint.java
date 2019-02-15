package app;

import bd.DataBase;
import io.spring.soap.getsongs.GetSongRequest;
import io.spring.soap.getsongs.GetSongResponse;
import io.spring.soap.getsongs.SongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rest.RestService;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;


@Endpoint
public class SongEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/SOAP/GetSongs";

    @Autowired
    public SongEndpoint() {

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSongRequest")
    @ResponsePayload
    public GetSongResponse getSong(@RequestPayload GetSongRequest request) {
        GetSongResponse response = new GetSongResponse();

        ArrayList<SongType> SongList = new ArrayList<SongType>();
        ResultSet rs;


        try
        {
            // TEST BDD
            DataBase bdd = new DataBase();
            bdd.connect();
            rs = bdd.execQuerry("SELECT tt.title, al.title_album, ar.name_artist\n" +
                    "FROM Album al, AlbumContent ac, Title tt, Artist ar\n" +
                    "WHERE al.id_album=ac.id_album\n" +
                    "AND ac.id_title=tt.id_title\n" +
                    "AND ar.id_artist=al.id_artist\n"+
                    "AND ar.name_artist='"+request.getName()+"'");
            while (rs.next())
            {
                SongType st = new SongType();
                st.setTitle(rs.getNString(1));
                st.setAlbum(rs.getNString(2));
                st.setArtiste(rs.getNString(3));
                SongList.add(st);
            }
            bdd.close();

            /* TEST */
            RestService restService = new RestService();
            restService.launchRest(" ");

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if ((SongList.size() == 0))
        {
            //TO DO REST requête
            System.out.println("Not Found Song Artist");
        }


        response.setSong(SongList);

        return response;
    }
}