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
public class AccountCheckFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute("user");

		String requestURL = request.getRequestURI();
		if (requestURL.equals(request.getContextPath() + "/")
				|| requestURL.contains("/login") 
				|| requestURL.startsWith(request.getContextPath() + "/css") 
				|| requestURL.endsWith("/login")) {
			chain.doFilter(request, response);
			return;
		}

				if (user == null) {
				response.sendRedirect(request.getContextPath() + "/");
				return;
			}

		if (user == null || user.getAuthority() != 2) {

			if (requestURL.contains("update/input")) {
				String empIdParam = request.getParameter("empId");

				if (empIdParam != null && !empIdParam.equals(String.valueOf(user.getEmpId()))) {
					response.sendRedirect(request.getContextPath() + "/list");
					return;
				}
			}

			if (requestURL.contains("delete") || requestURL.contains("regist")) {
				response.sendRedirect(request.getContextPath() + "/list");
			}
		}

		chain.doFilter(request, response);
	}
}
