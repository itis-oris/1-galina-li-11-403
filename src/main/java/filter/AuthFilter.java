package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getServletPath();

        if (!checkExcluded(path) && (session == null || session.getAttribute("user") == null)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean checkExcluded(String path) {
        return path.contains("/auth/login") ||
                path.contains("/auth/check") ||
                path.contains("/auth/register") ||
                path.contains("/index") ||
                path.equals("/") ||
                path.contains("/css/") ||
                path.contains("/js/");
    }
}