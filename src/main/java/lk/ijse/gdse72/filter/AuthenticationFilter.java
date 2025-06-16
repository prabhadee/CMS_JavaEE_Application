package lk.ijse.gdse72.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.ijse.gdse72.model.podos.UserDTO;

import java.io.IOException;

@WebFilter(urlPatterns = {"/employee/*", "/admin/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        UserDTO user = null;

        if (session != null) {
            user = (UserDTO) session.getAttribute("user");
        }

        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index");
            return;
        }

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        if (requestURI.startsWith(contextPath + "/admin/") && !user.isAdmin()) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index");
            return;
        }

        if (requestURI.startsWith(contextPath + "/employee/") && !user.isEmployee()) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index");
            return;
        }

        chain.doFilter(request, response);
    }
}