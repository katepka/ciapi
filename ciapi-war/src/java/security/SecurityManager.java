package security;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 * SecurityManager проверяет, относится ли http запрос к защищенной странице
 * и обладает ли запрос соответствующим разрешением.
 */
public class SecurityManager {
    
    /**
     * Проверяет, относится ли запрос к защищенной странице
     * @param request проходит проверку
     * @return true, если запрос относится к защищенной странице, и false, если нет
     */
    public static boolean isSecurityPage(HttpServletRequest request) {
        String urlPattern = UrlPatternUtil.getUrlPattern(request);
        Set<String> roles = SecurityConfig.getAllAppRoles();
        for (String role : roles) {
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
 
    /**
     * Проверяет, обладает ли роль пользователя соответствующим разрешением (правами доступа)
     * @param request проходит проверку
     * @return true, если роль обладает правами на запрос, и false, если нет
     */
    public static boolean hasPermission(HttpServletRequest request) {
        String urlPattern = UrlPatternUtil.getUrlPattern(request);
        Set<String> allRoles = SecurityConfig.getAllAppRoles();
        for (String role : allRoles) {
            if (!request.isUserInRole(role)) {
                continue;
            }
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
}
