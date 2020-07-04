package servlet;

import activity.CategoryActivity;
import activity.IdeaActivity;
import entity.VoteIdeas;
import entry.CategoryEntry;
import entry.IdeaEntry;
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

@WebServlet(name = "CategoryServlet", urlPatterns = {"/category"})
public class CategoryServlet extends HttpServlet {

    @EJB
    private IdeaActivity ideaActivity;
    @EJB
    private CategoryActivity categoryActivity;
    @EJB
    private VoteIdeasFacadeLocal votesIdeasFacade;

    private List<VoteIdeas> votes = new ArrayList<>();
    private String categoryId = null;
    private CategoryEntry category = null;
    private List<IdeaEntry> ideas = new ArrayList<>();
    private List<IdeaEntry> shownIdeas = new ArrayList<>();
    private long numImplementedIdeas = 0;
    private final SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        categoryId = request.getParameter("categoryId");

        ideas.clear();
        shownIdeas.clear();
        request.removeAttribute("ideas");

        if (categoryId != null) {
            category = categoryActivity.findById(Long.parseLong(categoryId));
            ideas = ideaActivity.findByCategory(Long.parseLong(categoryId));
            votes = votesIdeasFacade.findAll();

            if (ideas != null) {
                numImplementedIdeas = 0;
                for (IdeaEntry idea : ideas) {
                    if (idea.getStatus().getId() == 3) {
                        numImplementedIdeas++;
                    }
                    idea.setCreatedFormatted(formatForDate.format(idea.getCreated()));
                    /*============= Сопоставление голосов и идей ==============*/
                    idea.setVotesFor(0);
                    idea.setVotesAgainst(0);
                    if (votes != null) {
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
                    }
                    idea.countScore();
                }

                /* =========== Обработка фильтации идей по статусу ============ */
                String filteredStatus = request.getParameter("status");
                if (request.getParameter("filter") != null) {
                    shownIdeas.clear();
                    switch (filteredStatus) {
                        case "all":
                            shownIdeas = ideas;
                            break;
                        case "1":
                            for (IdeaEntry idea : ideas) {
                                if (idea.getStatus().getId() == 1L) {
                                    shownIdeas.add(idea);
                                }
                            }
                            break;
                        case "2":
                            for (IdeaEntry idea : ideas) {
                                if (idea.getStatus().getId() == 2L) {
                                    shownIdeas.add(idea);
                                }
                            }
                            break;
                        case "3":
                            for (IdeaEntry idea : ideas) {
                                if (idea.getStatus().getId() == 3L) {
                                    shownIdeas.add(idea);
                                }
                            }
                            break;
                        case "4":
                            for (IdeaEntry idea : ideas) {
                                if (idea.getStatus().getId() == 4L) {
                                    shownIdeas.add(idea);
                                }
                            }
                            break;
                        default:
                            shownIdeas.addAll(ideas);
                    }
                } else {
                    shownIdeas.addAll(ideas);
                }

                /* =========== Обработка сортировки идей по дате ============ */
                String sortType = request.getParameter("sortBy");
                if ("new".equalsIgnoreCase(sortType)) {
                    Collections.sort(shownIdeas, IdeaEntry.COMPARE_BY_CREATED);
                } else if ("popular".equalsIgnoreCase(sortType)) {
                    Collections.sort(ideas, IdeaEntry.COMPARE_BY_SCORE);
                }

                /* ========================================================== */
                request.setAttribute("ideas", shownIdeas);
                request.setAttribute("numIdeas", ideas.size());
            } else {
                request.setAttribute("numIdeas", 0);
            }
            if (category != null) {
                request.setAttribute("category", category);
            }
            request.setAttribute("numImplementedIdeas", numImplementedIdeas);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/category.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }

        } else {
            // TODO: handle the situation when categoryId is null
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
}
