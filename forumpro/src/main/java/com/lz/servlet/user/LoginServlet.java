package com.lz.servlet.user;

import com.google.common.collect.Maps;
import com.lz.dto.JsonResult;
import com.lz.entity.User;
import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;
import com.lz.util.Config;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
        req.getSession().removeAttribute("curr_user");
        forward("user/login",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");



        UserService userService=new UserService();

        HttpSession session=req.getSession();
//        boolean islogin=userService.validateLogin(username,req);
         req.getCookies();
        Map<String,String> result= Maps.newHashMap();
//        if(islogin){//如果已经登陆
//            result.put("state","error");
//            result.put("message","您已经登陆,请先安全退出");
//            renderJson(result,resp);
//        }else{
            String ip=req.getRemoteAddr();//获取客户端ip

            try{
                User user=userService.login(username,password,ip);

                Cookie cookie=new Cookie("islogin","yes");
                cookie.setDomain("localhost");
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 7);
                cookie.setHttpOnly(true);
                resp.addCookie(cookie);

                session.setAttribute("curr_user",user);//登陆后将用户放入session中

                logger.info("{}登陆了",user.getUsername());
                result.put("state","success");
            }catch(Exception e){
                e.printStackTrace();
                result.put("state","error");
                result.put("message",e.getMessage());
            }
            renderJson(result,resp);
//        }




    }
}
