package servlet;

import entity.Idea;
import entity.User;
import entry.UserEntry;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mapper.UserMapper;
import repository.IdeaFacadeLocal;
import util.AppUtils;

@WebServlet(name = "BecomeACoordinatorServlet", urlPatterns = {"/becomeCoordinator"})
public class BecomeACoordinatorServlet extends HttpServlet {

    @EJB
    private UserMapper userMapper;
    @EJB
    private IdeaFacadeLocal ideaFacade;
    private String ideaId = null;
    private UserEntry loginedUser = null;
    private Idea currentIdea;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        ideaId = request.getParameter("ideaId");
        if (ideaId != null) {
            try {
                long id = Long.parseLong(ideaId);
                loginedUser = AppUtils.getLoginedUser(request.getSession());
                User coordinator = userMapper.mapUserEntryToUser(loginedUser);
                currentIdea = ideaFacade.find(id);
                if (currentIdea != null && currentIdea.getCoordinator() == null) {
                    
                    currentIdea.setCoordinator(coordinator);
                    ideaFacade.edit(currentIdea);
                    // TODO: notify user about successful subscription
                    System.out.println("Идее " + id + " назначен координатор " + coordinator.getName());
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/ideas?ideaId=" + ideaId);
                    if (requestDispatcher != null) {
                        requestDispatcher.forward(request, response);
                    }
                } else if (currentIdea.getCoordinator() != null) {
                    System.out.println("У идеи " + id + " уже есть координатор " + currentIdea.getCoordinator().getName());
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/ideas?ideaId=" + ideaId);
                    if (requestDispatcher != null) {
                        requestDispatcher.forward(request, response);
                    }
                } else {
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/notfound.jsp");
                    if (requestDispatcher != null) {
                        requestDispatcher.forward(request, response);
                    }
                }
            } catch (NumberFormatException nfe) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", nfe);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/notfound.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            }
        } else {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/notfound.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
