package servlet;

import client.CategoryClient;
import client.CategoryClient;
import entry.CategoryEntry;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Start", urlPatterns = {"/start"})
public class Start extends HttpServlet {
   
    private CategoryClient client = new CategoryClient();
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        CategoryEntry category = client.getCategoryById_JSON(CategoryEntry.class, "3");

        try (PrintWriter out = response.getWriter()) {
            out.print(category.getDescription());
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
//            out.print(client.createCategory_JSON(category).getStatus());
                out.println(client.deleteCategory_JSON(CategoryEntry.class, "2").getStatus());
        }
    }
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
