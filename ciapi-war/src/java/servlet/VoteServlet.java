package servlet;

import client.IdeaClient;
import entity.Idea;
import entity.User;
import entity.VoteIdeas;
import entry.IdeaEntry;
import entry.UserEntry;
import java.io.IOException;
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
import mapper.IdeaMapper;
import mapper.UserMapper;
import repository.VotesIdeasFacadeLocal;
import util.AppUtils;

@WebServlet(name = "VoteServlet", urlPatterns = {"/vote"})
public class VoteServlet extends HttpServlet {

    private String ideaId;
    private UserEntry loginedUser = null;
    private IdeaClient ideaClient = null;
    private IdeaEntry idea = null;
    
    @EJB
    private VotesIdeasFacadeLocal votesIdeasFacade;
    @EJB
    private IdeaMapper ideaMapper;
    @EJB
    private UserMapper userMapper;
    
    @Override
    public void init() {
        ideaClient = new IdeaClient();
    }

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
       
        ideaId = request.getParameter("ideaId");

        if (ideaId != null) {
            loginedUser = AppUtils.getLoginedUser(request.getSession());
            boolean removed = false;
            try {
                idea = ideaClient.getIdeaById_JSON(IdeaEntry.class, ideaId);
                if (request.getParameter("voteFor") != null) {
                    List<VoteIdeas> castVotes = votesIdeasFacade
                            .findVote(loginedUser.getId(), Long.parseLong(ideaId));
                    if (castVotes.size() > 0) {
                        for (VoteIdeas vote : castVotes) {
                            if (vote.getVote() == 1) {
                                removeVote(vote.getId());
                                removed = true;
                            }
                        }
                        if (!removed) {
                            addVote(1, idea, loginedUser);
                        }
                    } else {
                        addVote(1, idea, loginedUser);
                    }
                }

                if (request.getParameter("voteAgainst") != null) {
                    List<VoteIdeas> castVotes = votesIdeasFacade
                            .findVote(loginedUser.getId(), Long.parseLong(ideaId));
                    if (castVotes.size() > 0) {
                        for (VoteIdeas vote : castVotes) {
                            if (vote.getVote() == -1) {
                                removeVote(vote.getId());
                                removed = true;
                            }
                        }
                        if (!removed) {
                            addVote(-1, idea, loginedUser);
                        }
                    } else {
                        addVote(-1, idea, loginedUser);
                    }
                }
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
    
    private void removeVote(Long id) {
        votesIdeasFacade.remove(id);
    }

    private void addVote(int i, IdeaEntry idea, UserEntry loginedUser) {
        VoteIdeas newVote = new VoteIdeas();
        short voteFor = (short) ((i == 1) ? 1 : -1);
        newVote.setVote(voteFor);
        Idea ratedIdea = ideaMapper.mapIdeaEntryToIdea(idea);
        newVote.setIdea(ratedIdea);
        User author = userMapper.mapUserEntryToUser(loginedUser);
        newVote.setUser(author);
        System.out.println(newVote.toString());
        votesIdeasFacade.create(newVote);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    @Override
    public void destroy() {
        if (ideaClient != null) {
            ideaClient.close();
        }
    }

}
