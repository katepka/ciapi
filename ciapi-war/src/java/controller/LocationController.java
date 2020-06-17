package controller;

import activity.LocationActivity;
import entry.LocationEntry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("locations")
public class LocationController {

    LocationActivity locationActivity = lookupLocationActivityBean();

    @Context
    private UriInfo context;

    public LocationController() {
    }
    
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllLocations() {
        List<LocationEntry> locations = locationActivity.findAll();
        if (locations == null || locations.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            GenericEntity<List<LocationEntry>> entities 
                    = new GenericEntity<List<LocationEntry>>(locations){};
            return Response.ok().entity(entities).build();
        }
    }

    private LocationActivity lookupLocationActivityBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LocationActivity) c.lookup("java:global/ciapi/ciapi-ejb/LocationActivity!activity.LocationActivity");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
