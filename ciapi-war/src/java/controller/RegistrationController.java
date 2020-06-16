package controller;

import activity.UserActivity;
import entity.User;
import entry.UserEntry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import validation.EntryValidator;

@Path("registration")
public class RegistrationController {

    UserActivity userActivity = lookupUserActivityBean();

    @Context
    private UriInfo context;

    public RegistrationController() {
    }

    // TODO: Сделать нормальную регистрацию
    @POST
    @Path("/")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response registrateUser(UserEntry entry) {
        EntryValidator.validate(entry);
        User user = userActivity.createUser(entry);
        if (user == null) {
            return Response.noContent().build();
        } else {
            return Response.created(context.getAbsolutePath()).build();
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
}
