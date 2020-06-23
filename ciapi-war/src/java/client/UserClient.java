package client;

import entry.IdeaEntry;
import entry.UserEntry;
import java.text.MessageFormat;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.AppConstants;

public class UserClient {

    private WebTarget webTarget;
    private Client client;

    public UserClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(AppConstants.BASE_URI).path("users");
    }

    public List<UserEntry> getAllUsers_XML() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(MediaType.APPLICATION_XML).get(new GenericType<List<UserEntry>>() {});
    }

    public List<UserEntry> getAllUsers_JSON() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserEntry>>() {});
    }

    public <T> T getUserById_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T getUserById_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response deleteUser_XML(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_XML)
                        .delete(Response.class);
    }

    public Response deleteUser_JSON(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_JSON)
                        .delete(Response.class);
    }

    public Response updateUser_XML(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_XML)
                        .put(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Response.class);
    }

    public Response updateUser_JSON(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public List<IdeaEntry> getIdeasByAuthorId_XML(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}/ideas", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML).get(new GenericType<List<IdeaEntry>>() {});
    }

    public List<IdeaEntry> getIdeasByAuthorId_JSON(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}/ideas", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_JSON).get(new GenericType<List<IdeaEntry>>() {});
    }

    public void close() {
        client.close();
    }
    
}
