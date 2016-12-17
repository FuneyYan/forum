package com.lz.servlet.user;

import com.google.common.collect.Maps;
import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/foundPassword")
public class FoundPasswordServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/foundPassword",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value=req.getParameter("value");
        String type=req.getParameter("type");
        String token= req.getSession().getId();//获取客户端的sessionid,后面限制发送邮件
        UserService userService=new UserService();

        Map<String,String> result= Maps.newHashMap();
        try{
            userService.foundPassword(token,type,value);
            result.put("state","success");
        }catch (Exception e){
            e.printStackTrace();
            result.put("state","error");
            result.put("message",e.getMessage());
        }
        renderJson(result,resp);
    }
}
