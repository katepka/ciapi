package servlet;

import client.CategoryClient;
import entry.CategoryEntry;
import entry.IdeaEntry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

@WebServlet(name = "CategoryServlet", urlPatterns = {"/category"})
public class CategoryServlet extends HttpServlet {

    @EJB
    private IdeaFacadeLocal ideaFacade;
    
    private String categoryId = null;
    private CategoryEntry category = null;
    private CategoryClient categoryClient;
    private List<IdeaEntry> ideas = new ArrayList<>();
    private long numImplementedIdeas = 0;
    
    @Override
    public void init() {
        categoryClient = new CategoryClient();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        categoryId = request.getParameter("categoryId"); // тоже может быть null
        System.out.println(categoryId); 
        try {
            category = categoryClient.getCategoryById_JSON(CategoryEntry.class, categoryId);
            try {
                ideas = categoryClient.getIdeasByCategoryId_JSON(categoryId);
            } catch (ClientErrorException cee) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
            }
            
            if (ideas != null) {
                for (IdeaEntry idea : ideas) {
                    if (idea.getStatus().getId() == 3) {
                        numImplementedIdeas++;
                    }
                }
                request.setAttribute("ideas", ideas);
                request.setAttribute("numIdeas", ideas.size());
            } else {
                request.setAttribute("numIdeas", 0);
            }
            if (category != null) {
                request.setAttribute("categoryTitle", category.getTitle());
                request.setAttribute("categoryDescription", category.getDescription());
            }
            request.setAttribute("numImplementedIdeas", numImplementedIdeas);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/category.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }

        } catch (ClientErrorException cee) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
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
