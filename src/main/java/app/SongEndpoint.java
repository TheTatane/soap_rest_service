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

        // TEST BDD
        DataBase bdd = new DataBase();
        bdd.connect();
        // TEST
        SongType st = new SongType();
        st.setAlbum("Five");
        st.setArtiste("Hollywood Undead");
        st.setTitle("Riot");

        response.setSong(st);

        return response;
    }
}