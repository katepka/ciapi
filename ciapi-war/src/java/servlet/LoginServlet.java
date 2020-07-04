package servlet;

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
import util.AppUtils;
import repository.UserFacadeLocal;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private String errorMessage;

    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private UserMapper userMapper;
    
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
        
        User entity = null;
        UserEntry user = null;
        try {
            entity = userFacade.findUser(userName, password);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
        }

        if (entity != null) {
            user = userMapper.mapUserToUserEntry(entity);
        } else {
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
