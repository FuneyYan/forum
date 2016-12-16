package com.lz.servlet.user;

import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/validate/email")
public class ValidateEmailServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService=new UserService();
        String email=req.getParameter("email");
        boolean result=userService.findByEmail(email);
        if(result){//没找到,可以使用
            renderTxt("true",resp);
        }else{
            renderTxt("false",resp);
        }
    }
}
