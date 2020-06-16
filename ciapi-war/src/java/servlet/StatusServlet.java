package servlet;

import activity.StatusActivity;
import entity.Status;
import entry.StatusEntry;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository.StatusFacadeLocal;

@WebServlet(name = "StatusServlet", urlPatterns = {"/StatusServlet"})
public class StatusServlet extends HttpServlet {

    @EJB
    private StatusActivity statusActivity;

    @EJB
    private StatusFacadeLocal statusFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        StatusEntry statusEntry = null;
        Status status = null;
        String title = request.getParameter("statusTitle");
        String description = request.getParameter("statusDescription");
        if (title != null && description != null && !title.isEmpty() && !description.isEmpty()) {
            statusEntry = new StatusEntry();
            statusEntry.setTitle(title);
            statusEntry.setDescription(description);
            status = statusActivity.createStatus(statusEntry);
        }
        
        String statusId = request.getParameter("statusId");
        if (statusId!= null && !statusId.isEmpty()) {
            try {
                Integer id = Integer.parseInt(statusId);
                status = statusFacade.find(id);
            } catch (NumberFormatException ex) {
                
            }
        }
      
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StatusServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h4> Дратути</h4>");
            if (status != null) {
                out.println("New status: " + status.getTitle());
                out.println("Description: " + status.getDescription());
            } else {
                out.println("Фигня... Статус не создался...");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
