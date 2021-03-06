package servlet;

import activity.IdeaActivity;
import entry.IdeaEntry;
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
import util.AppUtils;
import validation.EntryValidator;

/**
 * Обрабатывает GET-запрос на подписку пользователя на идею в качестве координатора. 
 * Сервлет получает из http-сессии текущего пользователя, а также идентификатор текущей идеи, 
 * обращается к подсистеме взаимодействия с базой данных для получения объекта IdeaEntry. 
 * Далее проводится проверка, действительно ли для идеи не назначен координатор. 
 * В зависимости от результата делается запись в базу данных о том, 
 * что текущий пользователь стал координатором данной идеи, 
 * либо выводится информация о том, что у данной идеи уже есть координатор, 
 * и идет переадресация на страницу идеи.
 * 
 * @author Теплякова Е.А.
 */
@WebServlet(name = "BecomeACoordinatorServlet", urlPatterns = {"/becomeCoordinator"})
public class BecomeACoordinatorServlet extends HttpServlet {

    @EJB
    private IdeaActivity ideaActivity;
    private String ideaId = null;
    private UserEntry loginedUser = null;
    private IdeaEntry currentIdea;
    
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
                currentIdea = ideaActivity.findById(id);
                if (currentIdea != null && currentIdea.getCoordinator() == null) {
                    
                    currentIdea.setCoordinator(loginedUser);
                    EntryValidator.validate(currentIdea);
                    ideaActivity.updateIdea(id, currentIdea);
                    // TODO: notify user about successful subscription
                    System.out.println("Идее " + id + " назначен координатор " + loginedUser.getName());
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

}
