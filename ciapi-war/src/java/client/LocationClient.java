package client;

import entry.LocationEntry;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import util.AppConstants;


public class LocationClient {

    private WebTarget webTarget;
    private Client client;

    public LocationClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(AppConstants.BASE_URI).path("locations");
    }

    public List<LocationEntry> getAllLocations_XML() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(MediaType.APPLICATION_XML)
                       .get(new GenericType<List<LocationEntry>>() {});
    }

    public List<LocationEntry> getAllLocations_JSON() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(MediaType.APPLICATION_JSON)
                       .get(new GenericType<List<LocationEntry>>() {});
    }

    public void close() {
        client.close();
    }
    
}
