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


import java.sql.ResultSet;
import java.util.ArrayList;


@Endpoint
public class SongEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/SOAP/GetSongs";
    ArrayList<SongType> SongList;
    @Autowired
    public SongEndpoint() {

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSongRequest")
    @ResponsePayload
    public GetSongResponse getSong(@RequestPayload GetSongRequest request) {
        GetSongResponse response = new GetSongResponse();

        SongList = new ArrayList<>();
        execQuerry(request.getName());

        if ((SongList.size() == 0))
        {
            //TO DO REST requête
            RestService rest = new RestService();
            int tmp = (int) ( Math.random() * 2 + 1);
            if(tmp==1)
                rest.parseDom(request.getName(),"getAlbumsByAuthor", "dom");
            else if (tmp==2)
                rest.parseXSL(request.getName(),"","getAlbumsByAuthor", "xsl");

            System.out.println("PASSAGE 2");
            execQuerry(request.getName());
        }

        response.setSong(SongList);

        return response;
    }

    private void execQuerry(String name)
    {
        ResultSet rs;
        try
        {
            System.out.println("DEBUG 2");
            DataBase bdd = new DataBase();
            bdd.connect();

            rs = bdd.execQuerry("SELECT DISTINCT tt.title, al.title_album, ar.name_artist\n" +
                    "FROM Album al, Title tt, Artist ar\n" +
                    "WHERE al.id_album=tt.id_album\n" +
                    "AND ar.id_artist=al.id_artist\n" +
                    "AND ar.name_artist='"+name+"'\n");

            while (rs.next())
            {
                SongType st = new SongType();
                st.setTitle(rs.getNString(1));
                st.setAlbum(rs.getNString(2));
                st.setArtiste(rs.getNString(3));
                SongList.add(st);
            }
            bdd.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}