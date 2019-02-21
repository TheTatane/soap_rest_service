package app;

import bd.DataBase;
import io.spring.soap.getinfosongs.GetInfoSongRequest;
import io.spring.soap.getinfosongs.GetInfoSongResponse;
import io.spring.soap.getinfosongs.SongTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



import java.sql.*;
import java.util.ArrayList;

@Endpoint
public class InfoSongEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/SOAP/GetInfoSongs";

    @Autowired
    public InfoSongEndpoint() {

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInfoSongRequest")
    @ResponsePayload
    public GetInfoSongResponse getInfoSong (@RequestPayload GetInfoSongRequest request) {
        GetInfoSongResponse response = new GetInfoSongResponse();


        // TEST
        ArrayList<SongTypeInfo> SongInfoList = new ArrayList<SongTypeInfo>();
        ResultSet rs;
        System.out.println(request.getNameTitle());
        try
        {
            // TEST BDD
            DataBase bdd = new DataBase();
            bdd.connect();
            rs = bdd.execQuerry("SELECT tt.title, al.title_album, ar.name_artist, ac.title_duration\n" +
                    "FROM Album al, AlbumContent ac, Title tt, Artist ar\n" +
                    "WHERE al.id_album=ac.id_album\n" +
                    "AND ac.id_title=tt.id_title\n" +
                    "AND ar.id_artist=al.id_artist\n"+
                    "AND tt.title='"+request.getNameTitle()+"'");
            while (rs.next())
            {
                SongTypeInfo st = new SongTypeInfo();
                st.setTitle(rs.getNString(1));
                st.setAlbum(rs.getNString(2));
                st.setArtiste(rs.getNString(3));
                st.setDuree(rs.getNString(4));
                SongInfoList.add(st);
            }
            bdd.close();

        } catch (Exception e)
        {
            e.printStackTrace();;
        }

        if ((SongInfoList.size() == 0))
        {
            //TO DO REST requête
            System.out.println("Not Found Title");
        }


        response.setSongInfo(SongInfoList);


        return response;
    }
}