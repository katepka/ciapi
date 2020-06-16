package client;

import java.text.MessageFormat;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class StatusClient {

    private WebTarget webTarget;
    private Client client;

    public StatusClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(ClientConstants.BASE_URI).path("statuses");
    }

    public Response deleteStatus_XML(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                .request(MediaType.APPLICATION_XML)
                .delete(Response.class);
    }

    public Response deleteStatus_JSON(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);
    }

    public Response updateStatus_XML(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                        .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                        .put(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Response.class);
    }

    public Response updateStatus_JSON(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public <T> T getIdeasByStatusId_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}/ideas", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML)
                       .get(responseType);
    }

    public <T> T getIdeasByStatusId_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}/ideas", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_JSON)
                       .get(responseType);
    }

    public <T> T getAllStatuses_XML(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(MediaType.APPLICATION_XML)
                       .get(responseType);
    }

    public <T> T getAllStatuses_JSON(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(MediaType.APPLICATION_JSON)
                .get(responseType);
    }

    public <T> T getStatus_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML)
                .get(responseType);
    }

    public <T> T getStatus_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_JSON)
                .get(responseType);
    }

    public Response createStatus_XML(Object requestEntity) throws ClientErrorException {
        return webTarget.path("").request(MediaType.APPLICATION_XML)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Response.class);
    }

    public Response createStatus_JSON(Object requestEntity) throws ClientErrorException {
        return webTarget.path("").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public void close() {
        client.close();
    }
    
}
