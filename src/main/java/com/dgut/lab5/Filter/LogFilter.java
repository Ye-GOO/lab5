package com.dgut.lab5.Filter;

import com.dgut.lab5.bean.User;
import org.springframework.http.HttpRequest;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LogFilter")
public class LogFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest HttpRequest = (HttpServletRequest)request;
        HttpServletResponse HttpResponse = (HttpServletResponse) response;
        HttpSession session = HttpRequest.getSession();
        User user = (User)session.getAttribute("user");
        if(user==null) {
            HttpResponse.sendRedirect("/login");
        }else if(user.getIdentity().equals("学生用户")){
            HttpResponse.sendRedirect("/");
        }
        chain.doFilter(request,response);
    }

}
