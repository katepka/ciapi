package security;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 * SecurityManager
 * Checks is it a secured request and does the request has a permission
 */
public class SecurityManager {
    
    /**
     * Checks is it a secured page request
     * @param request checked for security
     * @return true if the request is secured and false if not
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
     * Checks the role has a permission
     * @param request checked for a permission
     * @return true if the role has a permission for request and false if doesn't
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
