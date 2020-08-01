package servlet;

import activity.IdeaActivity;
import entry.IdeaEntry;
import entry.UserEntry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.AppUtils;

/**
 * Обрабатывает GET-запрос на формирование страницы личного кабинета пользователя. 
 * Получает из http-сессии текущего пользователя, обращается к подсистеме взаимодействия 
 * с базой данных для получения сведений о пользователе, списка идей, 
 * созданных данным пользователем, а также списка идей, на которые подписан пользователь 
 * (является координатором) с формами добавления информации о реализации. 
 * Полученные данные в виде атрибутов запроса перенаправляются на JSP-страницу account, 
 * которая динамически выводит представление в веб-браузере.
 * 
 * @author Теплякова Е.А.
 */
@WebServlet(name = "AccountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {

    @EJB
    private IdeaActivity ideaActivity;
    private UserEntry loginedUser;
    private List<IdeaEntry> ideas = new ArrayList<>();
    private List<IdeaEntry> ideasImpl = new ArrayList<>();
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        request.removeAttribute("ideas");
        request.removeAttribute("ideasImpl");
        
        loginedUser = AppUtils.getLoginedUser(request.getSession());
        request.setAttribute("user", loginedUser);
        
        ideas = ideaActivity.findByAuthor(loginedUser.getId());
        if (ideas != null) {
            request.setAttribute("ideas", ideas);
        }
        
        ideasImpl = ideaActivity.findByCoordinator(loginedUser.getId());
        
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
}
