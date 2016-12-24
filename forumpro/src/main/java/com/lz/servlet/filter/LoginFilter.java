package com.lz.servlet.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
                Map map=request.getParameterMap();
                Set paramSet=map.entrySet();
                Iterator iterator=paramSet.iterator();
                if(iterator.hasNext()){
                    url+="?";
                    while(iterator.hasNext()){
                        Map.Entry mapEntry= (Map.Entry) iterator.next();
                        Object key=mapEntry.getKey();
                        Object value=mapEntry.getValue();
                        String []val= (String[]) value;
                        String params="";

                        for(int i=0;i<val.length;i++){
                            params+=key+"="+val[i]+"&";
                            url+=params;
                        }
                        url=url.substring(0,url.length()-1);
                    }
                }
                response.sendRedirect("/login?redirect="+url);
            }
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
