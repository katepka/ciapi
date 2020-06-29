package servlet;

import client.UserClient;
import entity.Idea;
import entry.IdeaEntry;
import entry.UserEntry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import mapper.IdeaMapper;
import repository.IdeaFacadeLocal;
import util.AppUtils;

@WebServlet(name = "AccountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {

    @EJB
    private IdeaFacadeLocal ideaFacade;
    @EJB
    private IdeaMapper ideaMapper;
    private UserEntry loginedUser;
    private UserClient userClient = null;
    private List<IdeaEntry> ideas = new ArrayList<>();
    private List<IdeaEntry> ideasImpl = new ArrayList<>();
    private List<Idea> list = new ArrayList<>();
    
    @Override
    public void init() {
        userClient = new UserClient();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        request.removeAttribute("ideas");
        request.removeAttribute("ideasImpl");
        
        loginedUser = AppUtils.getLoginedUser(request.getSession());
        request.setAttribute("user", loginedUser);
        
        try {
            ideas = userClient.getIdeasByAuthorId_JSON(loginedUser.getId().toString());
        } catch (ClientErrorException cee) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
        }
        if (ideas != null) {
            request.setAttribute("ideas", ideas);
        }
        
        list = ideaFacade.findByCoordinator(loginedUser.getId());
        if (list != null) {
            ideasImpl = ideaMapper.mapIdeaListToIdeaEntryList(list);
        }
        if (ideas != null) {
            request.setAttribute("ideasImpl", ideasImpl);
        }
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/account.jsp");
        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
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
        if (userClient != null) {
            userClient.close();
        }
    }

}
