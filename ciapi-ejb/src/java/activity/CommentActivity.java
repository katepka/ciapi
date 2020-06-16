package activity;

import entity.Comment;
import entity.Idea;
import entity.User;
import entry.CommentEntry;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import mapper.CommentMapper;
import mapper.IdeaMapper;
import mapper.UserMapper;
import repository.CommentFacadeLocal;
import repository.IdeaFacadeLocal;
import repository.UserFacadeLocal;

@Stateless
@LocalBean
public class CommentActivity {

    @EJB
    private CommentMapper commentMapper;

    @EJB
    private CommentFacadeLocal commentFacade;
    
    @EJB
    private UserFacadeLocal userFacade;
    
    @EJB
    private IdeaFacadeLocal ideaFacade;
    
    @EJB
    private UserMapper userMapper;
    
    @EJB
    private IdeaMapper ideaMapper;
    
    public Comment createComment(CommentEntry commentEntry) {
        Comment comment = commentMapper.mapCommentEntryToComment(commentEntry);
        commentFacade.create(comment);
        return comment;
    }

    public CommentEntry findById(long id) {
        Comment comment = commentFacade.find(id);
        CommentEntry entry = commentMapper.mapCommentToCommentEntry(comment);
        return entry;
    }
    
    public List<CommentEntry> findAll() {
        List<Comment> commentList = commentFacade.findAll();
        List<CommentEntry> entryList = commentMapper.mapCommentListToCommentEntryList(commentList);
        return entryList;
    }
    
    public List<CommentEntry> findByAuthor(long authorId) {
        List<Comment> commentList = commentFacade.findByAuthor(authorId);
        List<CommentEntry> entryList = commentMapper.mapCommentListToCommentEntryList(commentList);
        return entryList;
    }
    
    public CommentEntry updateComment(long id, CommentEntry entry) {
        Comment entity = commentFacade.find(id);
        if (entity != null) {
            if (entry.getText() != null) {
                entity.setText(entry.getText());
            }
            if (entry.getCreated() != null) {
                entity.setCreated(entry.getCreated());
            }
            
            Long ideaId = entry.getIdea().getId();
            Idea idea = ideaFacade.find(ideaId);
            if (idea != null) {
                entity.setIdea(idea);
            }
            
            Long authorId = entry.getIdea().getId();
            User user = userFacade.find(authorId);
            if (idea != null) {
                entity.setAuthor(user);
            }
            
            commentFacade.edit(entity);
            return entry;
        } else {
            return null;
        }
    }
    
}
