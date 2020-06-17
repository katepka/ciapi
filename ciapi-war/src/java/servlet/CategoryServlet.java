package servlet;

import client.CategoryClient;
import entry.CategoryEntry;
import entry.IdeaEntry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;

@WebServlet(name = "CategoryServlet", urlPatterns = {"/category"})
public class CategoryServlet extends HttpServlet {
    
    private String categoryId = null;
    private CategoryEntry category = null;
    private CategoryClient categoryClient;
    private List<IdeaEntry> ideas = new ArrayList<>();
    
    @Override
    public void init() {
        categoryClient = new CategoryClient();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        int numImplementedIdeas = 0;
        categoryId = request.getParameter("categoryId"); // тоже может быть null
        
        try {
            
            category = categoryClient.getCategoryById_JSON(CategoryEntry.class, categoryId);
            ideas = categoryClient.getIdeasByCategoryId_JSON(categoryId);
            
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
            // TODO: handle it!!!
            System.out.println("404 | " + Arrays.toString(cee.getStackTrace()));
            
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/category.jsp");
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
