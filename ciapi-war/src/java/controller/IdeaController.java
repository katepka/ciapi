package controller;

import activity.CommentActivity;
import activity.IdeaActivity;
import entity.Idea;
import entry.CommentEntry;
import entry.IdeaEntry;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import validation.EntryValidator;
import repository.IdeaFacadeLocal;

@Path("ideas")
public class IdeaController {

    CommentActivity commentActivity = lookupCommentActivityBean();

    IdeaActivity ideaActivity = lookupIdeaActivityBean();

    IdeaFacadeLocal ideaFacade = lookupIdeaFacadeLocal();
     
    @Context
    private UriInfo context;

    public IdeaController() {
    }
    
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllIdeas() {
        List<IdeaEntry> ideas = ideaActivity.findAll();
        if (ideas == null || ideas.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            GenericEntity<List<IdeaEntry>> entities 
                    = new GenericEntity<List<IdeaEntry>>(ideas){};
            return Response.ok().entity(entities).build();
        }
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getIdeaById(@PathParam("id") String id) {
        IdeaEntry idea = null; 
        try {
            long ideaId = Long.parseLong(id);
            idea = ideaActivity.findById(ideaId);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            if (idea == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(idea).build();
            }
        }
    }
    
    @GET
    @Path("{id}/comments")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCommentsByIdea(@PathParam("id") String id) {
        List<CommentEntry> comments = null;
        try {
            long ideaId = Long.parseLong(id);
            comments = commentActivity.findByIdea(ideaId);
            if (comments == null || comments.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                GenericEntity<List<CommentEntry>> entities
                        = new GenericEntity<List<CommentEntry>>(comments) {
                };
                return Response.ok().entity(entities).build();
            }
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
        
    @POST
    @Path("/")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createIdea(IdeaEntry entry) {
        EntryValidator.validate(entry);
        Idea idea = ideaActivity.createIdea(entry);
        if (idea == null) {
            return Response.noContent().build();
        } else {
            return Response.created(context.getAbsolutePath()).build();
        }
    }
    
    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteIdea(@PathParam("id") String id) {
        try {
            long ideaId = Long.parseLong(id);
            Idea idea = ideaFacade.remove(ideaId);
            if (idea == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(idea).build();
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
    public Response updateIdea(IdeaEntry entry, @PathParam("id") String id) {
        EntryValidator.validate(entry);
        try {
            long ideaId = Long.parseLong(id);
            IdeaEntry idea = ideaActivity.updateIdea(ideaId, entry);
            if (idea == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(idea).build();
            }
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private IdeaFacadeLocal lookupIdeaFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (IdeaFacadeLocal) c.lookup("java:global/ciapi/ciapi-ejb/IdeaFacade!repository.IdeaFacadeLocal");
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

    private CommentActivity lookupCommentActivityBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CommentActivity) c.lookup("java:global/ciapi/ciapi-ejb/CommentActivity!activity.CommentActivity");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
