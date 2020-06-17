package entry;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

public abstract class AbstractDataEntry implements Serializable {
//    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
