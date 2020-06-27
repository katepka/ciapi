package security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static util.AppConstants.*;

/**
 * SecurityConfig
 * Mapping roles - urlPatterns configs
 */
public class SecurityConfig {
    
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";
    public static final String ROLE_MODERATOR = "moderator";
    
    public static final Map<String, List<String>> mapConfig = new HashMap<>();
    
    static {
        init();
    }
    
    private static void init() {
        
        List<String> urlPatternsAdmin = new ArrayList<>();
        urlPatternsAdmin.add(URL_PATTERN_NEW_IDEA);
        urlPatternsAdmin.add(URL_PATTERN_ADD_COMMENT);
        urlPatternsAdmin.add(URL_PATTERN_VOTE);
        urlPatternsAdmin.add(URL_PATTERN_CREATE_NEW_IDEA);
        mapConfig.put(ROLE_ADMIN, urlPatternsAdmin);
        
        List<String> urlPatternsUser = new ArrayList<>();
        urlPatternsUser.add(URL_PATTERN_NEW_IDEA);
        urlPatternsUser.add(URL_PATTERN_ADD_COMMENT);
        urlPatternsUser.add(URL_PATTERN_VOTE);
        urlPatternsUser.add(URL_PATTERN_CREATE_NEW_IDEA);
        mapConfig.put(ROLE_USER, urlPatternsUser);
        
        List<String> urlPatternsModerator = new ArrayList<>();
        urlPatternsModerator.add(URL_PATTERN_NEW_IDEA);
        urlPatternsModerator.add(URL_PATTERN_ADD_COMMENT);
        urlPatternsModerator.add(URL_PATTERN_VOTE);
        urlPatternsModerator.add(URL_PATTERN_CREATE_NEW_IDEA);
        mapConfig.put(ROLE_MODERATOR, urlPatternsModerator);
        
    }
        
    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }
 
    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }
    
}
