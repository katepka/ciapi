package entry;

import javax.validation.constraints.NotNull;

public class RoleEntry extends AbstractDataEntry {
    
    @NotNull(message = "title cannot be null")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
