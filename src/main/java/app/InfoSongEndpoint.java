package app;

import io.spring.soap.getinfosongs.GetInfoSongIRequest;
import io.spring.soap.getinfosongs.GetInfoSongResponse;
import io.spring.soap.getinfosongs.SongTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



import java.sql.*;

@Endpoint
public class InfoSongEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/SOAP/GetInfoSongs";

    @Autowired
    public InfoSongEndpoint() {

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInfoSongRequest")
    @ResponsePayload
    public GetInfoSongResponse getInfoSong (@RequestPayload GetInfoSongIRequest request) {
        GetInfoSongResponse response = new GetInfoSongResponse();


        // TEST
        SongTypeInfo al = new SongTypeInfo();
        al.setTitle("Bad Moon");
        al.setArtiste("Hollywood Undead");
        al.setAlbum("Five");
        al.setDuree("2:30");

        response.setSongInfo(al);


        return response;
    }
}