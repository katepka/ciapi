package controller;

import activity.RoleActivity;
import entry.RoleEntry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("roles")
public class RoleController {

    RoleActivity roleActivity = lookupRoleActivityBean();
   
    @Context
    private UriInfo context;

    public RoleController() {
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRole(@PathParam("id") String id) {
        RoleEntry role = null;
        try {
            long roleId = Long.parseLong(id);
            role = roleActivity.findById(roleId);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
        } finally {
            if (role == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(role).build();
            }
        }
    }

    private RoleActivity lookupRoleActivityBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoleActivity) c.lookup("java:global/ciapi/ciapi-ejb/RoleActivity!activity.RoleActivity");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
