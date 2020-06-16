package entry;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Credentials implements Serializable {
    
    @NotNull(message = "username cannot be null")
    @Size(min = 4, max = 255)
    private String username;
    
    @NotNull(message = "password cannot be null")
    @Size(min = 3, max = 255)
    private String password;

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
