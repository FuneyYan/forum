package com.lz.servlet.user;

import com.google.common.collect.Maps;
import com.lz.entity.User;
import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;
import com.lz.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/foundPassword/newPassword")
public class ResetPassword extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token=req.getParameter("_");
        if(StringUtils.isEmpty(token)){
            resp.sendError(404);
        }else{
            UserService userService=new UserService();
            try{
                User user=userService.tokenFindUser(token);
                req.setAttribute("user",user);
                req.setAttribute("token",token);
                forward("/user/resetpassword",req,resp);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("id");
        String token=req.getParameter("token");
        String password=req.getParameter("password");

        UserService userService=new UserService();
        Map<String,String> result= Maps.newHashMap();
        try{
            userService.resetPassword(id,token,password);
            result.put("state","success");
        }catch (Exception e){
            e.printStackTrace();
            result.put("state","error");
            result.put("message",e.getMessage());
        }
        renderJson(result,resp);
    }
}
