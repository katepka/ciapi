package servlet;

import activity.UserActivity;
import entity.User;
import entry.RoleEntry;
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

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/registrate"})
public class RegistrationServlet extends HttpServlet {

    @EJB
    private UserActivity userActivity;
    
    private final String VALID_EMAIL = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
                                      "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
                                      "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
                                      "+(?:[a-zA-Z]){2,}\\.?)$";
    private boolean isValidInput = true;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        request.removeAttribute("emailMustBeValid");
        request.removeAttribute("nameMustBeValid");
        request.removeAttribute("passwordMustBeValid");
        request.removeAttribute("passwordRepeatMustBeValid");
        
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/registration.jsp");
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
        request.removeAttribute("emailMustBeValid");
        request.removeAttribute("nameMustBeValid");
        request.removeAttribute("passwordMustBeValid");
        request.removeAttribute("passwordRepeatMustBeValid");
        
        String email = request.getParameter("email").trim();
        String name = request.getParameter("name").trim();
        String password = request.getParameter("password").trim();
        
        String passwordRepeat = request.getParameter("passwordRepeat").trim();
        
        if (request.getParameter("registrate") != null) {
            if (email == null || email.isEmpty()
                    || !email.matches(VALID_EMAIL)) {
                request.setAttribute("emailMustBeValid", "Введите валидный email");
                isValidInput = false;
            }
            if (name == null || name.isEmpty()) {
                request.setAttribute("nameMustBeValid", "Введите Ваше имя");
                isValidInput = false;
            }
            if (password == null || password.isEmpty()
                    || passwordRepeat == null || passwordRepeat.isEmpty()) {
                request.setAttribute("passwordMustBeValid", "Введите пароль");
                isValidInput = false;
            }
            if (!password.equals(passwordRepeat)) {
                request.setAttribute("passwordRepeatMustBeValid", "Пароли должны совпадать");
                isValidInput = false;
            }
            if (!isValidInput) {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/registration.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
            } else {
                UserEntry user = new UserEntry();
                user.setEmail(email);
                user.setName(name);
                user.setPassword(password);
                RoleEntry role = new RoleEntry();
                role.setId(2L);
                user.setRole(role);
                try {
                    User entity = userActivity.createUser(user);
                    if (entity != null) {
                        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                        if (requestDispatcher != null) {
                            requestDispatcher.forward(request, response);
                        }
                    } else {
                        request.setAttribute("errorMessage", "Ошибка регистрации. Попробуйте снова");
                        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/registration.jsp");
                        if (requestDispatcher != null) {
                            requestDispatcher.forward(request, response);
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
                    request.setAttribute("errorMessage", "Ошибка регистрации. Попробуйте снова");
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/registration.jsp");
                    if (requestDispatcher != null) {
                        requestDispatcher.forward(request, response);
                    }
                }

            }
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
