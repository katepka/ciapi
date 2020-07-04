package security;

import entry.UserEntry;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.AppUtils;

/**
 * Класс SecurityFilter проверяет все клиентские запросы, 
 * прежде чем предоставить доступ к защищенным страницам.
 * Если пользователь не прошел процедуру идентификации и аутентификации
 * - его перенаправляет на страницу /login
 * Если пользователь не прошел процедуру авторизации - его перенаправляет
 * на страницу Access Denied
 */
@WebFilter("/*")
public class SecurityFilter implements Filter {

    public SecurityFilter() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        
        String servletPath = request.getServletPath();
        
        UserEntry loginedUser = AppUtils.getLoginedUser(request.getSession());
        
        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest wrapRequest = request;
        
        if (loginedUser != null) {
            String userName = loginedUser.getEmail();
            String roleName = loginedUser.getRole().getTitle();
            wrapRequest = new UserRoleRequestWrapper(userName, roleName, request);
        }
        
        if (SecurityManager.isSecurityPage(request)) {
            if (loginedUser == null) {
                String requestUri = request.getRequestURI();
                int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }
            
            boolean hasPermission = SecurityManager.hasPermission(wrapRequest);
            if (!hasPermission) {
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/accessDenied.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }
        
        chain.doFilter(wrapRequest, response);
    }

    @Override
    public void destroy() {}
    
}
