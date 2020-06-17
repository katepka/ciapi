package servlet;

import client.CategoryClient;
import client.IdeaClient;
import client.StatusClient;
import entry.CategoryEntry;
import entry.IdeaEntry;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;

@WebServlet(name = "Start", urlPatterns = {"/start"})
public class MainServlet extends HttpServlet {
   
    private CategoryClient categoryClient = new CategoryClient();
    private IdeaClient ideaClient = new IdeaClient();
    private StatusClient statusClient = new StatusClient();
    
    private List<CategoryEntry> categories = null;
    private List<IdeaEntry> ideas = null;
    private List<IdeaEntry> implementedIdeas = null;
    long numIdeas = 0; // TODO: Оптимизировать запросы с подсчетом

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
  
        try {
            categories = categoryClient.getAllCategories_JSON();
            ideas = ideaClient.getAllIdeas_JSON();
            implementedIdeas = statusClient.getIdeasByStatusId_JSON("3");
        } catch (ClientErrorException cee) {
            // TODO: handle it
            System.out.println(cee.getStackTrace());
        }
        

        if (ideas == null || ideas.isEmpty()) {
            request.setAttribute("numIdeas", 0);
        } else {
            request.setAttribute("numIdeas", ideas.size());
        }
        
        if (implementedIdeas == null || implementedIdeas.isEmpty()) {
            request.setAttribute("numImplementedIdeas", 0);
        } else {
            request.setAttribute("numImplementedIdeas", implementedIdeas.size());
        }
        
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
        
        CategoryEntry category = new CategoryEntry();
        
//        category.setTitle("novyi zagolovok");
//        category.setDescription("novoe opisanie");
//        category.setIconRef("refer");
        
        try (PrintWriter out = response.getWriter()) {
//            out.print(categoryClient.createCategory_JSON(category).getStatus());
                out.println(categoryClient.deleteCategory_JSON(CategoryEntry.class, "2").getStatus());
        }
    }
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
