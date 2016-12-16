package com.lz.servlet.user;

import com.google.common.collect.Maps;
import com.lz.entity.User;
import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/reg")
public class RegServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/reg",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String email=req.getParameter("email");
        String phone=req.getParameter("phone");

//        System.out.println(username+"  "+password+"  "+email+"  "+phone);

        Map<String,Object> map= Maps.newHashMap();
        try{
            UserService userService=new UserService();
            userService.save(username,password,email,phone);
            map.put("state","success");
        }catch (Exception e){
            map.put("state","error");
            map.put("message","注册新用户失败");
        }
        renderJson(map,resp);

    }
}
