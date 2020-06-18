package client;

import entry.CommentEntry;
import entry.IdeaEntry;
import java.text.MessageFormat;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class IdeaClient {

    private WebTarget webTarget;
    private Client client;

    public IdeaClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(ClientConstants.BASE_URI).path("ideas");
    }

    public Response createIdea_XML(Object requestEntity) throws ClientErrorException {
        return webTarget.path("").request(MediaType.APPLICATION_XML)
                        .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Response.class);
    }

    public Response createIdea_JSON(IdeaEntry requestEntity) throws ClientErrorException {
        return webTarget.path("").request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public Response updateIdea_XML(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_XML)
                        .put(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Response.class);
    }

    public Response updateIdea_JSON(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public Response deleteIdea_XML(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_XML)
                        .delete(Response.class);
    }

    public Response deleteIdea_JSON(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_JSON)
                        .delete(Response.class);
    }

    public <T> T getIdeaById_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T getIdeaById_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public List<IdeaEntry> getAllIdeas_XML() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(MediaType.APPLICATION_XML).get(new GenericType<List<IdeaEntry>>() {});
    }

    public List<IdeaEntry> getAllIdeas_JSON() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(MediaType.APPLICATION_JSON).get(new GenericType<List<IdeaEntry>>() {});
    }
    
    public List<CommentEntry> getCommentsByIdeaId_XML(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}/comments", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML).get(new GenericType<List<CommentEntry>>() {});
    }

    public List<CommentEntry> getCommentsByIdeaId_JSON(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}/comments", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_JSON).get(new GenericType<List<CommentEntry>>() {});
    }

    public void close() {
        client.close();
    }
    
}
