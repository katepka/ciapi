package entry;

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
    
    @NotNull(message = "location cannot be null")
    private LocationEntry location;
    
    @NotNull(message = "author cannot be null")
    private UserEntry author;
    
    @NotNull(message = "created cannot be null")
    private Date created;
    
    private UserEntry coordinator;
    
    @NotNull(message = "status cannot be null")
    private StatusEntry status;
    
    private ImplementationInfoEntry implementationInfo;
    
    public IdeaEntry() {}

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

    public ImplementationInfoEntry getImplementationInfo() {
        return implementationInfo;
    }

    public void setImplementationInfo(ImplementationInfoEntry implementationInfo) {
        this.implementationInfo = implementationInfo;
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
    
    
    
}
