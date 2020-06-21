package servlet;

import client.CategoryClient;
import entity.VotesIdeas;
import entry.CategoryEntry;
import entry.IdeaEntry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
import repository.VotesIdeasFacadeLocal;

@WebServlet(name = "CategoryServlet", urlPatterns = {"/category"})
public class CategoryServlet extends HttpServlet {

    @EJB
    private IdeaFacadeLocal ideaFacade;
    
    @EJB
    private VotesIdeasFacadeLocal votesIdeasFacade;
    
    private List<VotesIdeas> votes = new ArrayList<>();
    private String categoryId = null;
    private CategoryEntry category = null;
    private CategoryClient categoryClient;
    private List<IdeaEntry> ideas = new ArrayList<>();
    private List<IdeaEntry> shownIdeas = new ArrayList<>();
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
        try {
            category = categoryClient.getCategoryById_JSON(CategoryEntry.class, categoryId);
            try {
                ideas = categoryClient.getIdeasByCategoryId_JSON(categoryId);
                votes = votesIdeasFacade.findAll();
            } catch (ClientErrorException cee) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", cee);
            }
            
            if (ideas != null) {
                numImplementedIdeas = 0;
                for (IdeaEntry idea : ideas) {
                    if (idea.getStatus().getId() == 3) {
                        numImplementedIdeas++;
                    }
                    /*============= Сопоставление голосов и идей ==============*/
                    idea.setVotesFor(0);
                    idea.setVotesAgainst(0);
                    if (votes != null) {
                        for (VotesIdeas vote : votes) {
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
                            shownIdeas = ideas;
                    }
                } else {
                    shownIdeas = ideas;
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
