package servlet;

import client.IdeaClient;
import entry.CommentEntry;
import entry.IdeaEntry;
import java.io.IOException;
import java.util.ArrayList;
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
    
    private String ideaCoordinatorName = null;
    private String ideaLocationName = null;
    private List<String> photoRefs = null;
    private List<CommentEntry> comments = new ArrayList<>();
    private long votesFor = 0;
    private long votesAgainst = 0;
    
    @Override
    public void init() {
        ideaClient = new IdeaClient();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        // TODO: Получить id из request с предыдущей jsp, где выбираем идею
        ideaId = "1";
        
        if (ideaId != null && !ideaId.trim().isEmpty()) {
            try {
                IdeaEntry idea = ideaClient.getIdeaById_JSON(IdeaEntry.class, ideaId);
                comments = ideaClient.getCommentsByIdeaId_JSON(ideaId);
                
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
                    // TODO: Подтянуть комментарии
                    request.setAttribute("comments", comments);
                    
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/idea.jsp");
                    if (requestDispatcher != null) {
                        requestDispatcher.forward(request, response);
                    }
                }
                
            } catch (ClientErrorException cee) {
                // TODO: handle it
                System.out.println(cee.getStackTrace());
            }
        } else {
            // TODO: handle the situation
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
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
    }
}
