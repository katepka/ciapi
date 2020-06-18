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
import java.util.Arrays;
import java.util.List;
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
                // TODO: handle it
                System.out.println(Arrays.toString(cee.getStackTrace()));
            }
            try {
                locations = locationClient.getAllLocations_JSON();
            } catch (ClientErrorException cee) {
                // TODO: handle it
                System.out.println(Arrays.toString(cee.getStackTrace()));
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
                        // TODO: приделать ссылку
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
                    // TODO: Подтянуть голоса
                    request.setAttribute("votesFor", votesFor);
                    request.setAttribute("votesAgainst", votesAgainst);
                                      
                }
                
            } catch (ClientErrorException cee) {
                // TODO: handle it
                System.out.println(Arrays.toString(cee.getStackTrace()));
            }
            try {
                comments = ideaClient.getCommentsByIdeaId_JSON(ideaId);
                request.setAttribute("comments", comments);
            } catch (ClientErrorException cee) {
                // TODO: handle it
                System.out.println(Arrays.toString(cee.getStackTrace()));
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
        // TODO: получить координаты с карты
        String lat = "34.9";
        String lon = "67.0";
        String radius = "99";
        // TODO: photo
        
        // TODO: определять автора по сессии:
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
            newIdea.setCategory(category);
        } else {
            // TODO: убрать этот костыль
            CategoryEntry category = categoryClient.getCategoryById_JSON(CategoryEntry.class, "1");
            newIdea.setCategory(category);
        }
        if (locationId != null) {
            // TODO: поиск местоположения по id в базе
        } else {
            LocationEntry location = new LocationEntry();
            location.setLat(Float.parseFloat(lat));
            location.setLon(Float.parseFloat(lon));
            location.setName(locationName);
            location.setRadius(Float.parseFloat(radius));
            newIdea.setLocation(location);
        }
        
        System.out.println(newIdea.toString() + newIdea.getLocation().getLat() + newIdea.getLocation().getLon() 
                + newIdea.getLocation().getName());
        
        try {
            System.out.println(ideaClient.createIdea_JSON(newIdea).getStatus());
        } catch (ClientErrorException cee) {
            // TODO: handle it
            System.out.println(Arrays.toString(cee.getStackTrace()));
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
