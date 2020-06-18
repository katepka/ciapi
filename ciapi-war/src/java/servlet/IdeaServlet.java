package servlet;

import client.CategoryClient;
import client.IdeaClient;
import client.LocationClient;
import client.UserClient;
import entry.CategoryEntry;
import entry.CommentEntry;
import entry.IdeaEntry;
import entry.LocationEntry;
import entry.StatusEntry;
import entry.UserEntry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;

@WebServlet(name = "IdeaServlet", urlPatterns = {"/ideas"})
public class IdeaServlet extends HttpServlet {
    
    private String ideaId = null;
    private IdeaClient ideaClient;
    private CategoryClient categoryClient;
    private LocationClient locationClient;
    private UserClient userClient; 
    
    private String ideaCoordinatorName = null;
    private String ideaLocationName = null;
    private List<String> photoRefs = null;
    private List<CommentEntry> comments = new ArrayList<>();
    private List<CategoryEntry> categories = new ArrayList<>();
    private List<LocationEntry> locations = new ArrayList<>();
    private long votesFor = 0;
    private long votesAgainst = 0;
    
    private IdeaEntry newIdea = null;
    
    @Override
    public void init() {
        ideaClient = new IdeaClient();
        categoryClient = new CategoryClient();
        locationClient = new LocationClient();
        userClient = new UserClient();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        // ================= Формирование страницы newidea.jsp ======================
        
        String createIdea = request.getParameter("createIdea");
        if (createIdea != null && !createIdea.isEmpty()) {
            try {
                categories = categoryClient.getAllCategories_JSON();
            } catch (ClientErrorException cee) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            }
            try {
                locations = locationClient.getAllLocations_JSON();
            } catch (ClientErrorException cee) {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
            }
            request.setAttribute("categories", categories);
            request.setAttribute("locations", locations);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/newidea.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }
        
        
        // ================= Формирование страницы idea.jsp ======================
        
        ideaId = request.getParameter("ideaId");
        
        if (ideaId != null && !ideaId.trim().isEmpty()) {
            try {
                IdeaEntry idea = ideaClient.getIdeaById_JSON(IdeaEntry.class, ideaId);

                if (idea != null) {
                    if (idea.getCoordinator() == null) {
                        // TODO: add ref
                        ideaCoordinatorName = "Разыскивается. Стать координатором";
                    } else {
                        ideaCoordinatorName = idea.getCoordinator().getName();
                    }
                    if (idea.getLocation().getName() != null) {
                        ideaLocationName = idea.getLocation().getName();
                    } else {
                        ideaLocationName = "-";
                    }
                    request.setAttribute("idea", idea);
                    request.setAttribute("ideaCoordinatorName", ideaCoordinatorName);
                    request.setAttribute("ideaLocationName", ideaLocationName);
                    // TODO: get votes from database:
                    request.setAttribute("votesFor", votesFor);
                    request.setAttribute("votesAgainst", votesAgainst);
                                      
                }
                
            } catch (ClientErrorException cee) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            }
            try {
                comments = ideaClient.getCommentsByIdeaId_JSON(ideaId);
                request.setAttribute("comments", comments);
            } catch (ClientErrorException cee) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            }
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/idea.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        } else {
            // TODO: handle the situation when there isn't ideaId
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        // ======= Отправка данных со страницы newidea.jsp на сервере ==========
        
        newIdea = new IdeaEntry();
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String categoryName = request.getParameter("categorySelected");
        String categoryId = request.getParameter("categoryId");
        String locationName = request.getParameter("locationNameSelected");
        String locationId = request.getParameter("locationId");
        // TODO: get coordinates from map
        String lat = "34.9";
        String lon = "67.0";
        String radius = "99";
        // TODO: get photos from form
        
        // TODO: identify the author by session:
        UserEntry author = userClient.getUserById_JSON(UserEntry.class, "1");
        newIdea.setAuthor(author);
        
        StatusEntry status = new StatusEntry();
        status.setId(1L);
        newIdea.setStatus(status);
        
        if (title != null) {
            newIdea.setTitle(title);
        }
        if (description != null) {
            newIdea.setDescription(description);
        }
        if (categoryId != null) {
            CategoryEntry category = categoryClient.getCategoryById_JSON(CategoryEntry.class, categoryId);
            if (category != null) {
                newIdea.setCategory(category);
            } else {
                // TODO: handle the situation when category isn't found
            }
        } else {
            // TODO: handle the situation when categoryId is null    
        }
        LocationEntry location = new LocationEntry();
        if (locationId != null) {
            location.setId(Long.parseLong(locationId));
        } else {
            location.setLat(Float.parseFloat(lat));
            location.setLon(Float.parseFloat(lon));
            location.setName(locationName);
            location.setRadius(Float.parseFloat(radius));
        }
        newIdea.setLocation(location);        
        try {
            int statusCode = ideaClient.createIdea_JSON(newIdea).getStatus();
            switch (statusCode) {
                case 200:
                case 201:
                case 202:
                    {
                        // TODO: if idea was created where should i forward to?
                        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/main.jsp");
                        if (requestDispatcher != null) {
                            requestDispatcher.forward(request, response);
                        }       break;
                    }
                case 404:
                    {
                        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/notfound.jsp");
                        if (requestDispatcher != null) {
                            requestDispatcher.forward(request, response);
                        }       break;
                    }
                case 500:
                case 501:
                case 502:
                case 503:
                case 504:
                case 505:
                    {
                        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                        if (requestDispatcher != null) {
                            requestDispatcher.forward(request, response);
                        }       break;
                    }
                default:
                    break;
            }
            
        } catch (ClientErrorException cee) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
            
        }
        
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    @Override
    public void destroy() {
        if (ideaClient != null) {
            ideaClient.close();
        }
        if (categoryClient != null) {
            categoryClient.close();
        }
        if (locationClient != null) {
            locationClient.close();
        }
        if (userClient != null) {
            userClient.close();
        }
    }
}
