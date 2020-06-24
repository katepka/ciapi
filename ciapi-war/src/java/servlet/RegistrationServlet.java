package servlet;

import client.RegistrationClient;
import entry.RoleEntry;
import entry.UserEntry;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/registrate"})
public class RegistrationServlet extends HttpServlet {
    
    private final String VALID_EMAIL = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
                                      "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
                                      "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
                                      "+(?:[a-zA-Z]){2,}\\.?)$";
    private boolean isValidInput = true;
    private RegistrationClient registrationClient;
    
    @Override
    public void init() {
        registrationClient = new RegistrationClient();
    }

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
        
        String email = request.getParameter("email").trim();
        String name = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        
        String passwordRepeat = request.getParameter("passwordRepeat").trim();
        System.out.println(password);
        System.out.println(passwordRepeat);
        
        if (request.getParameter("registrate") != null) {
            if (email == null || email.isEmpty()
                    || !email.matches(VALID_EMAIL)) {
                System.out.println("Не валидный email");
                request.setAttribute("emailMustBeValid", "Введите валидный email");
                isValidInput = false;
            }
            if (name == null || name.isEmpty()) {
                System.out.println("Не ввели имя");
                request.setAttribute("nameMustBeValid", "Введите Ваше имя");
                isValidInput = false;
            }
            if (password == null || password.isEmpty()
                    || passwordRepeat == null || passwordRepeat.isEmpty()) {
                System.out.println("Не ввели пароль");
                request.setAttribute("passwordMustBeValid", "Введите пароль");
                isValidInput = false;
            }
            if (!password.equals(passwordRepeat)) {
                System.out.println("Не совпал пароль");
                request.setAttribute("passwordRepeatMustBeValid", "Пароли должны совпадать");
                isValidInput = false;
            }
            System.out.println(isValidInput);
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
                    int statusCode = registrationClient.registrateUser_JSON(user).getStatus();
                    switch (statusCode) {
                        case 200:
                        case 201:
                        case 202: {
                            // Success!
                            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                            if (requestDispatcher != null) {
                                requestDispatcher.forward(request, response);
                            }
                            break;
                        }
                        case 204: // TODO: handle the situation with no content
                        case 404: {
                            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/notfound.jsp");
                            if (requestDispatcher != null) {
                                requestDispatcher.forward(request, response);
                            }
                            break;
                        }
                        case 500:
                        case 501:
                        case 502:
                        case 503:
                        case 504:
                        case 505: {
                            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                            if (requestDispatcher != null) {
                                requestDispatcher.forward(request, response);
                            }
                            break;
                        }
                        default:
                            break;
                    }
                } catch (ClientErrorException cee) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
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
    
    @Override
    public void destroy() {
        if (registrationClient != null) {
            registrationClient.close();
        }
    }

}
