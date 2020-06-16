package entry;

import javax.validation.constraints.NotNull;

public abstract class AbstractDataEntry {
//    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
