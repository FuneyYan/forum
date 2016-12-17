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

@WebServlet("/validate/user")
public class ValidateUserServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService=new UserService();
        String username=req.getParameter("username");
        username= StringUtils.isoToUtf8(username);
        User user=userService.findByUserName(username);
        if(user==null){//没找到,可以使用
            renderTxt("true",resp);
        }else{
            renderTxt("false",resp);
        }
    }
}
