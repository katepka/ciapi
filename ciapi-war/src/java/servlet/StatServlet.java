package servlet;

import activity.IdeaActivity;
import entity.VoteIdeas;
import entry.IdeaEntry;
import entry.UserEntry;
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
import repository.IdeaFacadeLocal;
import repository.VoteIdeasFacadeLocal;
import util.AppUtils;

/**
 * Обрабатывает GET-запрос на получение списка ТОП-10 популярных идеи. 
 * Обращается к подсистеме взаимодействия с базой данных для получения 
 * списка из 10 идей с наибольшим количеством голосов. 
 * 
 * @author Теплякова Е.А.
 */
@WebServlet(name = "StatServlet", urlPatterns = {"/rating"})
public class StatServlet extends HttpServlet {
    
    @EJB
    private IdeaActivity ideaActivity;
    @EJB
    private IdeaFacadeLocal ideaFacade;
    @EJB
    private VoteIdeasFacadeLocal votesIdeasFacade;
    private List<IdeaEntry> ideas = new ArrayList<>();
    private List<IdeaEntry> topIdeas = new ArrayList<>();
    private long numIdeas = 0;
    private long numImplementedIdeas = 0;
    private UserEntry loginedUser;
    private List<VoteIdeas> votes = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        request.removeAttribute("loginedUser");
        
        ideas.clear();
        topIdeas.clear();

        loginedUser = AppUtils.getLoginedUser(request.getSession());
        request.setAttribute("loginedUser", loginedUser);

        numIdeas = ideaFacade.count();
        numImplementedIdeas = ideaFacade.countByStatus(3L);
        votes = votesIdeasFacade.findAll();
        
        try {
            ideas = ideaActivity.findAll();
            
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/servererror.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }
        
        /*============= Сопоставление голосов и идей ==============*/
        if (ideas != null) {
            for (IdeaEntry idea : ideas) {
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
        }
        
        Collections.sort(ideas, IdeaEntry.COMPARE_BY_SCORE);
        
        if (ideas.size() > 10) {
            for (int i = 0; i < 10; i++) {
                topIdeas.add(ideas.get(i));
                System.out.println(ideas.get(i).getTitle());
            }
        } else {
            topIdeas.addAll(ideas);
        }
        

        request.setAttribute("numIdeas", numIdeas);
        request.setAttribute("numImplementedIdeas", numImplementedIdeas);

        request.setAttribute("ideas", topIdeas);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/rating.jsp");
        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }

}
