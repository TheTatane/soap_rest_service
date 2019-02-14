package app;

import io.spring.soap.getalbums.Album;
import io.spring.soap.getalbums.GetAlbumRequest;
import io.spring.soap.getalbums.GetAlbumResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



import java.sql.*;

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
        Album al = new Album();
        al.setNameAlbum("Five");
        al.setNameArtiste("Hollywood Undead");
        al.setAnnee(2017);

        response.setAlbum(al);

        return response;
    }
}