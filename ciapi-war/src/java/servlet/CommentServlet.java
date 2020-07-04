package servlet;

import activity.CommentActivity;
import activity.IdeaActivity;
import entry.CommentEntry;
import entry.IdeaEntry;
import entry.UserEntry;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
import util.AppUtils;

@WebServlet(name = "CommentServlet", urlPatterns = {"/comment"})
public class CommentServlet extends HttpServlet {

    @EJB
    private IdeaActivity ideaActivity;
    private String ideaId;
    private UserEntry loginedUser = null;
    private IdeaEntry idea = null;

    @EJB
    private CommentActivity commentActivity;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // =============== Добавление нового комментария к идее ================
        String commentText = request.getParameter("commentText");

        if (request.getParameter("comment") != null
                && commentText != null
                && !commentText.trim().isEmpty()) {

            ideaId = request.getParameter("ideaId");
            if (ideaId != null) {
                CommentEntry comment = new CommentEntry();
                comment.setText(commentText);
                loginedUser = AppUtils.getLoginedUser(request.getSession());
                comment.setAuthor(loginedUser);
                try {
                    idea = ideaActivity.findById(Long.parseLong(ideaId));
                    comment.setIdea(idea);
                    comment.setCreated(Date.valueOf(LocalDate.now()));
                    System.out.println(comment.getCreated());
                    commentActivity.createComment(comment);

                    response.sendRedirect(request.getContextPath() + "/ideas?ideaId=" + ideaId);
                } catch (ClientErrorException cee) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
                    if (requestDispatcher != null) {
                        requestDispatcher.forward(request, response);
                    }
                }
            } else {
                // TODO: handle the situation when ideaId is null
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
