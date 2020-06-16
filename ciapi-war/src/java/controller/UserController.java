package controller;

import activity.IdeaActivity;
import activity.UserActivity;
import entity.User;
import entry.IdeaEntry;
import entry.UserEntry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import repository.UserFacadeLocal;
import validation.EntryValidator;

@Path("users")
public class UserController {
    
    UserActivity userActivity = lookupUserActivityBean();
    
    UserFacadeLocal userFacade = lookupUserFacadeLocal();
    
    IdeaActivity ideaActivity = lookupIdeaActivityBean();
    

    @Context
    private UriInfo context;

    public UserController() {
    }
    
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllUsers() {
        List<UserEntry> users = userActivity.findAll();
        if (users == null || users.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            GenericEntity<List<UserEntry>> entities 
                    = new GenericEntity<List<UserEntry>>(users){};
            return Response.ok().entity(entities).build();
        }
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserById(@PathParam("id") String id) {
        UserEntry user = null; 
        try {
            long userId = Long.parseLong(id);
            user = userActivity.findById(userId);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(user).build();
            }
        }
    }
    
    @GET
    @Path("{id}/ideas")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getIdeasByAuthorId(@PathParam("id") String id) {
        List<IdeaEntry> ideas = null;
        try {
            long authorId = Long.parseLong(id);
            ideas = ideaActivity.findByAuthor(authorId);
            if (ideas != null && !ideas.isEmpty()) {
                GenericEntity<List<IdeaEntry>> entities = new GenericEntity<List<IdeaEntry>>(ideas){};
                return Response.ok().entity(entities).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteUser(@PathParam("id") String id) {
        try {
            long userId = Long.parseLong(id);
            User user = userFacade.remove(userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(user).build();
            }
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateUser(UserEntry entry, @PathParam("id") String id) {
        EntryValidator.validate(entry);
        try {
            long userId = Long.parseLong(id);
            UserEntry user = userActivity.updateUser(userId, entry);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(user).build();
            }
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    

    private UserActivity lookupUserActivityBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (UserActivity) c.lookup("java:global/ciapi/ciapi-ejb/UserActivity!activity.UserActivity");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UserFacadeLocal lookupUserFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (UserFacadeLocal) c.lookup("java:global/ciapi/ciapi-ejb/UserFacade!repository.UserFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private IdeaActivity lookupIdeaActivityBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (IdeaActivity) c.lookup("java:global/ciapi/ciapi-ejb/IdeaActivity!activity.IdeaActivity");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
