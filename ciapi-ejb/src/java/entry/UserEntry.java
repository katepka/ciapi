package entry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserEntry extends AbstractDataEntry {
    
    @NotNull(message = "email cannot be null")
    @Size(min = 4, max = 255)
    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
      "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
      "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
      "+(?:[a-zA-Z]){2,}\\.?)$", message = "must be a valid email")
    private String email;
    
    @NotNull(message = "password cannot be null")
    @Size(min = 3, max = 255)
    private String password;
    
    @NotNull(message = "name cannot be null")
    private String name;
    
    @NotNull(message = "role cannot be null")
    private RoleEntry role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleEntry getRole() {
        return role;
    }

    public void setRole(RoleEntry role) {
        this.role = role;
    }
 
}
