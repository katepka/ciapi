package mapper;

import entity.Comment;
import entity.Idea;
import entity.User;
import entry.CommentEntry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import repository.IdeaFacadeLocal;
import repository.UserFacadeLocal;

@Stateless
@LocalBean
public class CommentMapper {

    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private IdeaFacadeLocal ideaFacade;
    @EJB
    private UserMapper userMapper;
    @EJB
    private IdeaMapper ideaMapper;

    public Comment mapCommentEntryToComment(CommentEntry entry) {
        Comment entity = new Comment();
        if (entry.getText() != null) {
            entity.setText(entry.getText());
        }
        if (entry.getCreated() != null) {
            entity.setCreated(entry.getCreated());
        }
        
        Long userId = entry.getAuthor().getId();
        User user = userFacade.find(userId);
        if (user != null) {
            entity.setAuthor(user);
        }
        
        Long ideaId = entry.getIdea().getId();
        Idea idea = ideaFacade.find(ideaId);
        if (idea != null) {
            entity.setIdea(idea);
        }
        return entity;
    }
    
    public CommentEntry mapCommentToCommentEntry(Comment entity) {
        CommentEntry entry = new CommentEntry();
        if (entity.getId() != null) {
            entry.setId(entity.getId());
        }
        if (entity.getText() != null) {
            entry.setText(entity.getText());
        }
        if (entity.getCreated() != null) {
            entry.setCreated(entity.getCreated());
        }
        User user = entity.getAuthor();
        if (user != null) {
            entry.setAuthor(userMapper.mapUserToUserEntry(user));
        }
        Idea idea = entity.getIdea();
        if (idea != null) {
            entry.setIdea(ideaMapper.mapIdeaToIdeaEntry(idea));
        }
        return entry;
    }
    
    public List<CommentEntry> mapCommentListToCommentEntryList(List<Comment> entities) {
        List<CommentEntry> entries = new ArrayList<>();
        for (Comment entity : entities) {
            entries.add(mapCommentToCommentEntry(entity));
        }
        return entries;
    }
}
