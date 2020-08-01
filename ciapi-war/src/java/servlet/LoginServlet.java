package servlet;

import activity.UserActivity;
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

/**
 * Обрабатывает POST-запрос на процедуру идентификации пользователя 
 * по имени пользователя (email) и паролю.
 * Переход к сервлету осуществляется со всех основных страниц через html-форму, 
 * доступную пользователю, не прошедшему процедуру идентификации, 
 * а также через переадресацию с защищенных страниц подсистемой безопасности. 
 * В случае успешного прохождения идентификации выполняется переход на главную страницу. 
 * Если в процессе проверки пользовательского ввода или в ходе обращения к подсистеме 
 * взаимодействия с базой данных возникает ошибка, сервлет возвращает пользователю 
 * на странице JSP login сведения об ошибке. 
 * 
 * @author Теплякова Е.А.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private String errorMessage;
    @EJB
    private UserActivity userActivity;
    private UserEntry user = null;

    @Override
    public void init() {
        errorMessage = null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        request.removeAttribute("errorMessage");

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        request.removeAttribute("errorMessage");

        String userName = request.getParameter("userName").trim();
        String password = request.getParameter("password").trim();

        if (userName == null || userName.isEmpty()
                || password == null || password.isEmpty()) {
            errorMessage = "Для входа введите логин и пароль";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }

        try {
            user = userActivity.findUser(userName, password);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
        }

        if (user == null) {
            errorMessage = "Неверный логин или пароль";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }

        AppUtils.storeLoginedUser(request.getSession(), user);

        int redirectId = -1;
        try {
            redirectId = Integer.parseInt(request.getParameter("redirectId"));
        } catch (NullPointerException | NumberFormatException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
        }
        String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
        if (requestUri != null) {
            response.sendRedirect(requestUri);
        } else {
            response.sendRedirect(request.getContextPath() + "/start");
        }
    }
}
