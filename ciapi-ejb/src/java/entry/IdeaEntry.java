package entry;

import java.util.Comparator;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IdeaEntry extends AbstractDataEntry {
    
    @NotNull(message = "title cannot be null")
    @Size(min = 1, max = 255)
    private String title;
    
    @NotNull(message = "description cannot be null")
    private String description;
    
    @NotNull(message = "category cannot be null")
    private CategoryEntry category;
    
    private LocationEntry location;
    
    @NotNull(message = "author cannot be null")
    private UserEntry author;

    private Date created;
    
    private UserEntry coordinator;
    
    @NotNull(message = "status cannot be null")
    private StatusEntry status;
    
    private String photoRef;
    
    private ImplementationInfoEntry implementationInfo;
    
    private int score;
    
    private int votesFor;
    
    private int votesAgainst;
    
    public IdeaEntry() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEntry getCategory() {
        return category;
    }

    public void setCategory(CategoryEntry category) {
        this.category = category;
    }

    public LocationEntry getLocation() {
        return location;
    }

    public void setLocation(LocationEntry location) {
        this.location = location;
    }

    public UserEntry getAuthor() {
        return author;
    }

    public void setAuthor(UserEntry author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public UserEntry getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(UserEntry coordinator) {
        this.coordinator = coordinator;
    }

    public StatusEntry getStatus() {
        return status;
    }

    public void setStatus(StatusEntry status) {
        this.status = status;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    public ImplementationInfoEntry getImplementationInfo() {
        return implementationInfo;
    }

    public void setImplementationInfo(ImplementationInfoEntry implementationInfo) {
        this.implementationInfo = implementationInfo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getVotesFor() {
        return votesFor;
    }

    public void setVotesFor(int votesFor) {
        this.votesFor = votesFor;
    }

    public int getVotesAgainst() {
        return votesAgainst;
    }

    public void setVotesAgainst(int votesAgainst) {
        this.votesAgainst = votesAgainst;
    }
    
    public void countScore() {
        score = votesFor - votesAgainst;
        if (score < 0) {
            score = 0;
        }
    }
    
    public void incrementVotesFor() {
        votesFor++;
    }
    
    public void incrementVotesAgainst() {
        votesAgainst++;
    }
    

    @Override
    public String toString() {
        return "IdeaEntry{" + "title=" + title 
                + ", description=" + description 
                + ", category=" + category 
                + ", location=" + location 
                + ", author=" + author 
                + ", created=" + created 
                + ", coordinator=" + coordinator 
                + ", status=" + status 
                + ", implementationInfo=" + implementationInfo + '}';
    }
    
    public static final Comparator<IdeaEntry> COMPARE_BY_CREATED = new Comparator<IdeaEntry>() {
        @Override
        public int compare(IdeaEntry idea1, IdeaEntry idea2) {
            return idea2.created.compareTo(idea1.created);
        }
    };
    
    public static final Comparator<IdeaEntry> COMPARE_BY_SCORE = new Comparator<IdeaEntry>() {
        @Override
        public int compare(IdeaEntry idea1, IdeaEntry idea2) {
            return idea2.score - idea1.score;
        }
    };
     
}
