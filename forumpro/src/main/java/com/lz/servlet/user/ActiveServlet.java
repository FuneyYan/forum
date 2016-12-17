package com.lz.servlet.user;

import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;
import com.lz.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/active")
public class ActiveServlet extends BasicServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token=req.getParameter("_");
        System.out.println("token : "+token);

        UserService userService=new UserService();
        if(StringUtils.isEmpty(token)){
            resp.sendError(404);
        }else{
            try{
                userService.activeByToken(token);
                forward("user/active_success",req,resp);
            }catch (Exception e){
                e.printStackTrace();
                req.setAttribute("message",e.getMessage());
                forward("user/active_error",req,resp);
            }
        }


    }
}
