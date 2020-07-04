package controller;

import activity.IdeaActivity;
import activity.StatusActivity;
import entity.Status;
import entry.IdeaEntry;
import entry.StatusEntry;
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
import security.arch.Secured;
import validation.EntryValidator;
import repository.StatusFacadeLocal;

@Path("statuses")
public class StatusController {

    StatusFacadeLocal statusFacade = lookupStatusFacadeLocal();

    StatusActivity statusActivity = lookupStatusActivityBean();
    
    IdeaActivity ideaActivity = lookupIdeaActivityBean();

    @Context
    private UriInfo context;
        

    public StatusController() {
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStatus(@PathParam("id") String id) {
        StatusEntry status = null;
        try {
            long statusId = Long.parseLong(id);
            status = statusActivity.findById(statusId);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
        } finally {
            if (status == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(status).build();
            }
        }
    }
    

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllStatuses() {
        List<StatusEntry> statuses = statusActivity.findAll();
        if (statuses == null || statuses.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            GenericEntity<List<StatusEntry>> entities 
                    = new GenericEntity<List<StatusEntry>>(statuses){};
            return Response.ok().entity(entities).build();
        }
    }

    @POST
    @Path("/")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createStatus(StatusEntry entry) {
        EntryValidator.validate(entry);
        Status status = statusActivity.createStatus(entry);
        if (status == null) {
            return Response.noContent().build();
        } else {
            return Response.created(context.getAbsolutePath()).build();
        }
    }
    
    @DELETE
    @Path("{id}")
//    @Secured
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStatus(@PathParam("id") String id) {
        try {
            long statusId = Long.parseLong(id);
            Status status = statusFacade.remove(statusId);
            if (status == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(status).build();
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
    public Response updateStatus(StatusEntry entry, @PathParam("id") String id) {
        EntryValidator.validate(entry);
        try {
            long statusId = Long.parseLong(id);
            StatusEntry status = statusActivity.updateStatus(statusId, entry);
            if (status == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(status).build();
            }
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("{id}/ideas")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getIdeasByStatusId(@PathParam("id") String id) {
        List<IdeaEntry> ideas = null;
        try {
            long statusId = Long.parseLong(id);
            ideas = ideaActivity.findByStatus(statusId);
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

    
    private StatusActivity lookupStatusActivityBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StatusActivity) c.lookup("java:global/ciapi/ciapi-ejb/StatusActivity!activity.StatusActivity");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private StatusFacadeLocal lookupStatusFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StatusFacadeLocal) c.lookup("java:global/ciapi/ciapi-ejb/StatusFacade!repository.StatusFacadeLocal");
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
