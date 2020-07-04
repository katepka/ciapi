package entry;

import java.util.Comparator;
import java.util.Date;
import javax.validation.constraints.NotNull;

public class CommentEntry extends AbstractDataEntry {
    @NotNull(message = "text cannot be null")
    private String text;
    
    private Date created;
    
    private String createdFormatted;
    
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

    public String getCreatedFormatted() {
        return createdFormatted;
    }

    public void setCreatedFormatted(String createdFormatted) {
        this.createdFormatted = createdFormatted;
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
    
    public static final Comparator<CommentEntry> COMPARE_BY_CREATED = new Comparator<CommentEntry>() {
        @Override
        public int compare(CommentEntry comment1, CommentEntry comment2) {
            return comment2.created.compareTo(comment1.created);
        }
    };
    
}
