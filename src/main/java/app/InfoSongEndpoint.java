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
import rest.RestService;


import java.sql.*;
import java.util.ArrayList;

@Endpoint
public class InfoSongEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/SOAP/GetInfoSongs";
    private ArrayList<SongTypeInfo> SongInfoList;
    @Autowired
    public InfoSongEndpoint() {

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInfoSongRequest")
    @ResponsePayload
    public GetInfoSongResponse getInfoSong (@RequestPayload GetInfoSongRequest request) {
        GetInfoSongResponse response = new GetInfoSongResponse();


        // TEST
        SongInfoList = new ArrayList<>();
        execQuerry(request.getNameArtiste(),request.getNameTitle());

        if ((SongInfoList.size() == 0))
        {
            //REST requête
            RestService rest = new RestService();
            int tmp = (int) ( Math.random() * 2 + 1);
            if(tmp==1)
                rest.parseDom(request.getNameArtiste(),"getAlbumsByAuthor", "dom");
            else if (tmp==2)
                rest.parseXSL(request.getNameArtiste(),request.getNameTitle(),"getAlbumsByAuthor", "xsl");

            System.out.println("PASSAGE 2");
            execQuerry(request.getNameArtiste(),request.getNameTitle());
        }

        response.setSongInfo(SongInfoList);

        return response;
    }

    private void execQuerry(String name, String title)
    {
        ResultSet rs;
        try
        {
            System.out.println("DEBUG 2");
            DataBase bdd = new DataBase();
            bdd.connect();
            rs = bdd.execQuerry("SELECT DISTINCT tt.title, al.title_album, ar.name_artist, tt.title_duration\n" +
                    "FROM Album al, Title tt, Artist ar\n" +
                    "WHERE al.id_album=tt.id_album\n" +
                    "AND ar.id_artist=al.id_artist\n" +
                    "AND ar.name_artist='"+name+"'\n"+
                    "AND tt.title='"+title+"'");

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

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}