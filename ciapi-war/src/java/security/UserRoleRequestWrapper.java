package security;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Класс-оболочка, расширяет HTTPServletRequest, переопределяя методы getUserPrincipal() 
 * и isUserInRole(). 
 * Если пользователь или роль в этой оболочке не заданы, выполняется запрос родительского запроса,
 * чтобы попытаться получить то, что когда-либо было установлено контейнером. 
 * Класс предназначен для создания и использования UserRoleFilter.
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
