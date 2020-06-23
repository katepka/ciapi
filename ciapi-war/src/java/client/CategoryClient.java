package client;

import entry.CategoryEntry;
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
import util.AppConstants;

public class CategoryClient {

    private WebTarget webTarget;
    private Client client;

    public CategoryClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(AppConstants.BASE_URI).path("categories");
    }
    
    public List<CategoryEntry> getAllCategories_JSON() {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(MediaType.APPLICATION_JSON).get(new GenericType<List<CategoryEntry>>() {});
    }

    public List<CategoryEntry> getAllCategories_XML() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("");
        return resource.request(MediaType.APPLICATION_XML).get(new GenericType<List<CategoryEntry>>() {});
    }
        
    public <T> T getCategoryById_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T getCategoryById_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response updateCategory_XML(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_XML)
                        .put(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Response.class);
    }

    public Response updateCategory_JSON(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_JSON)
                        .put(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public List<IdeaEntry> getIdeasByCategoryId_XML(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}/ideas", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_XML).get(new GenericType<List<IdeaEntry>>() {});
    }

    public List<IdeaEntry> getIdeasByCategoryId_JSON(String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(MessageFormat.format("{0}/ideas", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_JSON).get(new GenericType<List<IdeaEntry>>() {});
    }

    public Response createCategory_XML(Object requestEntity) throws ClientErrorException {
        return webTarget.path("").request(MediaType.APPLICATION_XML)
                        .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML), Response.class);
    }

    public Response createCategory_JSON(Object requestEntity) throws ClientErrorException {
        return webTarget.path("").request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public Response deleteCategory_XML(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_XML)
                        .delete(Response.class);
    }

    public Response deleteCategory_JSON(Object requestEntity, String id) throws ClientErrorException {
        return webTarget.path(MessageFormat.format("{0}", new Object[]{id}))
                        .request(MediaType.APPLICATION_JSON)
                        .delete(Response.class);
    }

    public void close() {
        client.close();
    }
    
}
