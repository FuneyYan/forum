package com.lz.servlet.user;

import com.google.common.collect.Maps;
import com.lz.entity.User;
import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends BasicServlet {
    private Logger logger= LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/login",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");

        String ip=req.getRemoteAddr();//获取客户端ip

        UserService userService=new UserService();
        Map<String,String> result= Maps.newHashMap();
        try{
            User user=userService.login(username,password,ip);
            HttpSession session=req.getSession();
            session.setAttribute("curr_user",user);//登陆后将用户放入session中

            logger.info("{}登陆了",user.getUsername());
            result.put("state","success");
        }catch(Exception e){
            e.printStackTrace();
            result.put("state","error");
            result.put("message",e.getMessage());
        }
        renderJson(result,resp);


    }
}
