package servlet;

import activity.IdeaActivity;
import client.CategoryClient;
import client.IdeaClient;
import client.LocationClient;
import client.UserClient;
import entity.VoteIdeas;
import entry.CategoryEntry;
import entry.CommentEntry;
import entry.IdeaEntry;
import entry.ImplementationInfoEntry;
import entry.LocationEntry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;
import repository.VotesIdeasFacadeLocal;

@WebServlet(name = "IdeaServlet", urlPatterns = {"/ideas"})
public class IdeaServlet extends HttpServlet {

    @EJB
    private IdeaActivity ideaActivity;
    private String ideaId = null;
    private IdeaClient ideaClient;
    private CategoryClient categoryClient;
    private LocationClient locationClient;
    private UserClient userClient; 
    private String ideaCoordinatorName = null;
    private String ideaLocationName = null;
    private List<String> photoRefs = null;
    private List<CommentEntry> comments = new ArrayList<>();
    private List<VoteIdeas> votes = new ArrayList<>();
    
   
    @EJB
    private VotesIdeasFacadeLocal votesIdeasFacade;
    
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
        
        // ================= Формирование страницы idea.jsp ======================
        ideaCoordinatorName = null;
        ideaLocationName = null;
        ideaId = request.getParameter("ideaId");
        if (ideaId != null && !ideaId.trim().isEmpty()) {
            request.removeAttribute("votesFor");
            request.removeAttribute("votesAgainst");
            try {
                IdeaEntry idea = ideaClient.getIdeaById_JSON(IdeaEntry.class, ideaId);

                if (idea != null) {
                    if (idea.getCoordinator() != null) {
                        ideaCoordinatorName = idea.getCoordinator().getName();
                    }
                    if (idea.getLocation() != null) {
                        if (idea.getLocation().getName() != null) {
                            ideaLocationName = idea.getLocation().getName();
                        }
                    
                    } else {
                        ideaLocationName = "-";
                    }
                    request.setAttribute("idea", idea);
                    request.setAttribute("ideaCoordinatorName", ideaCoordinatorName);
                    request.setAttribute("ideaLocationName", ideaLocationName);

                    /* ================== Вывод оценок к идее ========================*/
                    votes = votesIdeasFacade.findAll();
                    for (VoteIdeas vote : votes) {
                        if (Objects.equals(vote.getIdea().getId(), idea.getId())) {
                            switch (vote.getVote()) {
                                case 1:
                                    idea.incrementVotesFor();
                                    break;
                                case -1:
                                    idea.incrementVotesAgainst();
                                    break;
                            }
                        }
                    }
                    request.setAttribute("votesFor", idea.getVotesFor());
                    request.setAttribute("votesAgainst", idea.getVotesAgainst());
                }
                
                
                /* ================ Вывод комментариев к идее =====================*/
                try {
                    comments = ideaClient.getCommentsByIdeaId_JSON(ideaId);
//                    Collections.sort(comments, CommentEntry.COMPARE_BY_CREATED);
                    request.setAttribute("comments", comments);
                } catch (ClientErrorException cee) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
                }
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/idea.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }

            } catch (ClientErrorException cee) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            }

            
        } else {
            System.out.println("handle the situation when there isn't ideaId");
            // TODO: handle the situation when there isn't ideaId
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        /* ============== Добавление информации о реализации ===================*/  
        ideaId = request.getParameter("ideaId");
        String implInfo = request.getParameter("implInfo").trim();
        if (ideaId != null && implInfo != null) {
            try {
                IdeaEntry idea = ideaClient.getIdeaById_JSON(IdeaEntry.class, ideaId);
                if (idea != null) {
                    ImplementationInfoEntry implInfoEntry = new ImplementationInfoEntry();
                    implInfoEntry.setDescription(implInfo);
                    idea.setImplementationInfo(implInfoEntry);
                    ideaActivity.updateIdea(Long.parseLong(ideaId), idea);
                }
            } catch (ClientErrorException cee) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
            }
        }
        response.sendRedirect(request.getContextPath() + "/ideas?ideaId=" + ideaId);
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
