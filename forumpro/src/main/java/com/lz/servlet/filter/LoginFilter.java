package com.lz.servlet.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginFilter extends AbstractFilter {
    private List<String> list=null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String validateURL=filterConfig.getInitParameter("validateURL");
         list=Arrays.asList(validateURL.split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        String url=request.getRequestURI();

        if(list!=null && list.contains(url)){
            if(request.getSession().getAttribute("curr_user")!=null){
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                response.sendRedirect("/login?redirect="+url);
            }
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
