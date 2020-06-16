package controller;

import activity.CategoryActivity;
import activity.IdeaActivity;
import entity.Category;
import entry.CategoryEntry;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import repository.CategoryFacadeLocal;
import validation.EntryValidator;

@Path("categories")
public class CategoryController {

    CategoryFacadeLocal categoryFacade = lookupCategoryFacadeLocal();
    
    CategoryActivity categoryActivity = lookupCategoryActivityBean();
    
    IdeaActivity ideaActivity = lookupIdeaActivityBean();

    @Context
    private UriInfo context;
    
    public CategoryController() {
    }

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllCategories() {
        List<CategoryEntry> categories = categoryActivity.findAll();
        if (categories == null || categories.isEmpty()) {
            // Status code 404
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            GenericEntity<List<CategoryEntry>> entities 
                    = new GenericEntity<List<CategoryEntry>>(categories){};
            // Status code 201
            return Response.ok().entity(entities).build();
        }
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCategoryById(@PathParam("id") String id) {
        CategoryEntry category = null; 
        try {
            long categoryId = Long.parseLong(id);
            category = categoryActivity.findById(categoryId);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            if (category == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(category).build();
            }
        }
    }
    
    @GET
    @Path("{id}/ideas")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getIdeasByCategoryId(@PathParam("id") String id) {
        List<IdeaEntry> ideas = null;
        try {
            int categoryId = Integer.parseInt(id);
            ideas = ideaActivity.findByCategory(categoryId);
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
    
    @POST
    @Path("/")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createCategory(CategoryEntry entry) {
        EntryValidator.validate(entry);
        Category category = categoryActivity.createCategory(entry);
        if (category == null) {
            // Status code 
            return Response.noContent().build();
        } else {
            // Sends back new URI
            return Response.created(context.getAbsolutePath()).build();
        }
    }
    
    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteCategory(@PathParam("id") String id) {
        try {
            long categoryId = Long.parseLong(id);
            Category category = categoryFacade.remove(categoryId);
            if (category == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(category).build();
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
    public Response updateCategory(CategoryEntry entry, @PathParam("id") String id) {
        EntryValidator.validate(entry);
        try {
            long categoryId = Long.parseLong(id);
            CategoryEntry category = categoryActivity.updateCategory(categoryId, entry);
            if (category == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok().entity(category).build();
            }
        } catch (NumberFormatException nfe) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    private CategoryActivity lookupCategoryActivityBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CategoryActivity) c.lookup("java:global/ciapi/ciapi-ejb/CategoryActivity!activity.CategoryActivity");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private CategoryFacadeLocal lookupCategoryFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CategoryFacadeLocal) c.lookup("java:global/ciapi/ciapi-ejb/CategoryFacade!repository.CategoryFacadeLocal");
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
