package jp.co.sss.crud.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.entity.Employee;

@Component
public class LoginCheckFilter extends HttpFilter {
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
	    String requestURL = request.getRequestURI();

	    if (requestURL.startsWith(request.getContextPath() + "/css") || 
	        requestURL.endsWith("/login") ||
	        requestURL.equals(request.getContextPath() + "/")) {
	        chain.doFilter(request, response);
	        return;
	    }

	    HttpSession session = request.getSession();
	    Employee user = (Employee) session.getAttribute("user");
	    session.setAttribute("user", user);

	    if (user == null) {
	        response.sendRedirect(request.getContextPath() + "/");
	        return;
	    }

	    chain.doFilter(request, response);
	}


}
