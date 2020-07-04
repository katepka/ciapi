package servlet;

import activity.CommentActivity;
import activity.IdeaActivity;
import entity.VoteIdeas;
import entry.CommentEntry;
import entry.IdeaEntry;
import entry.ImplementationInfoEntry;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository.VoteIdeasFacadeLocal;
import validation.EntryValidator;

@WebServlet(name = "IdeaServlet", urlPatterns = {"/ideas"})
public class IdeaServlet extends HttpServlet {

    @EJB
    private IdeaActivity ideaActivity;
    @EJB
    private CommentActivity commentActivity;
    @EJB
    private VoteIdeasFacadeLocal votesIdeasFacade;
    private String ideaId = null;
    private IdeaEntry idea = null;
    private String ideaCoordinatorName = null;
    private String ideaLocationName = null;
    private List<CommentEntry> comments = new ArrayList<>();
    private List<VoteIdeas> votes = new ArrayList<>();
    private final SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // ================= Формирование страницы idea.jsp ======================
        ideaCoordinatorName = null;
        ideaLocationName = null;
        ideaId = request.getParameter("ideaId");
        if (ideaId != null && !ideaId.trim().isEmpty()) {
            request.removeAttribute("votesFor");
            request.removeAttribute("votesAgainst");

            idea = ideaActivity.findById(Long.parseLong(ideaId));
            if (idea != null) {
                if (idea.getCoordinator() != null) {
                    ideaCoordinatorName = idea.getCoordinator().getName();
                }
                if (idea.getLocation() != null) {
                    if (idea.getLocation().getName() != null) {
                        ideaLocationName = idea.getLocation().getName();
                    }

                } else {
                    ideaLocationName = "-";
                }

                idea.setCreatedFormatted(formatForDate.format(idea.getCreated()));
                
                request.setAttribute("idea", idea);
                request.setAttribute("ideaCoordinatorName", ideaCoordinatorName);
                request.setAttribute("ideaLocationName", ideaLocationName);

                /* ================== Вывод оценок к идее ========================*/
                votes = votesIdeasFacade.findAll();
                for (VoteIdeas vote : votes) {
                    if (Objects.equals(vote.getIdea().getId(), idea.getId())) {
                        switch (vote.getVote()) {
                            case 1:
                                idea.incrementVotesFor();
                                break;
                            case -1:
                                idea.incrementVotesAgainst();
                                break;
                        }
                    }
                }
                request.setAttribute("votesFor", idea.getVotesFor());
                request.setAttribute("votesAgainst", idea.getVotesAgainst());
            }

            /* ================ Вывод комментариев к идее =====================*/
            comments = commentActivity.findByIdea(Long.parseLong(ideaId));
            Collections.sort(comments, CommentEntry.COMPARE_BY_CREATED);
            for (CommentEntry comment: comments) {
                comment.setCreatedFormatted(formatForDate.format(comment.getCreated()));
            }
            request.setAttribute("comments", comments);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/idea.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }

        } else {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/notfound.jsp");
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

        /* ============== Добавление информации о реализации ===================*/
        ideaId = request.getParameter("ideaId");
        String implInfo = request.getParameter("implInfo").trim();
        if (ideaId != null && implInfo != null) {
            idea = ideaActivity.findById(Long.parseLong(ideaId));
            if (idea != null) {
                ImplementationInfoEntry implInfoEntry = new ImplementationInfoEntry();
                implInfoEntry.setDescription(implInfo);
                idea.setImplementationInfo(implInfoEntry);
                
                EntryValidator.validate(idea);
                ideaActivity.updateIdea(Long.parseLong(ideaId), idea);
            }
        }
        response.sendRedirect(request.getContextPath() + "/ideas?ideaId=" + ideaId);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
