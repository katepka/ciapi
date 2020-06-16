package entry;

import java.util.Date;
import javax.validation.constraints.NotNull;

public class CommentEntry extends AbstractDataEntry {
    @NotNull(message = "text cannot be null")
    private String text;
    
    @NotNull(message = "created cannot be null")
    private Date created;
    
    @NotNull(message = "author cannot be null")
    private UserEntry author;
    
    @NotNull(message = "idea cannot be null")
    private IdeaEntry idea;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public UserEntry getAuthor() {
        return author;
    }

    public void setAuthor(UserEntry author) {
        this.author = author;
    }

    public IdeaEntry getIdea() {
        return idea;
    }

    public void setIdea(IdeaEntry idea) {
        this.idea = idea;
    }
    
    
}
