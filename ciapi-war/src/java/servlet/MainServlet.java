package servlet;

import client.CategoryClient;
import entry.CategoryEntry;
import java.io.IOException;
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
import repository.IdeaFacadeLocal;

@WebServlet(name = "Start", urlPatterns = {"/start"})
public class MainServlet extends HttpServlet {

    @EJB
    private IdeaFacadeLocal ideaFacade;
   
    private CategoryClient categoryClient;
    
    private List<CategoryEntry> categories = null;
    long numIdeas = 0; // TODO: Оптимизировать запросы с подсчетом
    long numImplementedIdeas = 0;
    
    @Override
    public void init() {
        categoryClient = new CategoryClient();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        numIdeas = ideaFacade.count();
        numImplementedIdeas = ideaFacade.countByStatus(3L);
        try {
            categories = categoryClient.getAllCategories_JSON();
        } catch (ClientErrorException cee) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }

        request.setAttribute("numIdeas", numIdeas);
        request.setAttribute("numImplementedIdeas", numImplementedIdeas);
        
        request.setAttribute("categories", categories);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/main.jsp");
        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
    }
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    @Override
    public void destroy() {
        if (categoryClient != null) {
            categoryClient.close();
        }
    }

}
