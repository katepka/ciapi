package client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RegistrationClient {

    private WebTarget webTarget;
    private Client client;

    public RegistrationClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(ClientConstants.BASE_URI).path("registration");
    }

    public Response registrateUser_XML(Object requestEntity) throws ClientErrorException {
        return webTarget.path("").request(MediaType.APPLICATION_XML)
                                 .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Response.class);
    }

    public Response registrateUser_JSON(Object requestEntity) throws ClientErrorException {
        return webTarget.path("").request(MediaType.APPLICATION_JSON)
                                 .post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public void close() {
        client.close();
    }
    
}
