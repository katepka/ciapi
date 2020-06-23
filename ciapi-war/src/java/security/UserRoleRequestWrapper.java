package security;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * An extension for the HTTPServletRequest that overrides the getUserPrincipal()
 * and isUserInRole(). We supply these implementations here, where they are not
 * normally populated unless we are going through the facility provided by the
 * container.
 * If he user or roles are null on this wrapper, the parent request is consulted
 * to try to fetch what ever the container has set for us. This is intended to
 * be created and used by the UserRoleFilter.
 */
public class UserRoleRequestWrapper extends HttpServletRequestWrapper {
    
    private String userName;
    private String roleName;
    private HttpServletRequest realRequest;

    public UserRoleRequestWrapper(String userName, String roleName, HttpServletRequest request) {
        super(request);
        this.userName = userName;
        this.roleName = roleName;
        this.realRequest = request;
    }
    
    @Override
    public boolean isUserInRole(String role) {
        if (this.roleName == null) {
            return this.realRequest.isUserInRole(role);
        }
        return roleName.equals(role);
    }
 
    @Override
    public Principal getUserPrincipal() {
        if (this.userName == null) {
            return realRequest.getUserPrincipal();
        }
 
        return new Principal() {
            @Override
            public String getName() {
                return userName;
            }
        };
    }
    
}
