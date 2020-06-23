package util;

import entry.UserEntry;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

public class AppUtils {
    private static int REDIRECT_ID = 0;
 
    private static final Map<Integer, String> ID_URI_MAP = new HashMap<>();
    private static final Map<String, Integer> URI_ID_MAP = new HashMap<>();
 
    /**
     * Save information about logined user
     * @param session current HttpSession
     * @param loginedUser UserEntry - logined user
     */
    public static void storeLoginedUser(HttpSession session, UserEntry loginedUser) {
        session.setAttribute("loginedUser", loginedUser);
    }
 
    /**
     * Give information about logined user from session
     * @param session current HttpSession
     * @return UserEntry - logined user
     */
    public static UserEntry getLoginedUser(HttpSession session) {
        UserEntry loginedUser = (UserEntry) session.getAttribute("loginedUser");
        return loginedUser;
    }
 
    /**
     * 
     * @param session
     * @param requestUri
     * @return 
     */
    public static int storeRedirectAfterLoginUrl(HttpSession session, String requestUri) {
        Integer id = URI_ID_MAP.get(requestUri);
 
        if (id == null) {
            id = REDIRECT_ID++;
 
            URI_ID_MAP.put(requestUri, id);
            ID_URI_MAP.put(id, requestUri);
            return id;
        }
 
        return id;
    }
 
    /**
     * 
     * @param session
     * @param redirectId
     * @return 
     */
    public static String getRedirectAfterLoginUrl(HttpSession session, int redirectId) {
        String url = ID_URI_MAP.get(redirectId);
        if (url != null) {
            return url;
        }
        return null;
    }
}
