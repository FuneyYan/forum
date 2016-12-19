package com.lz.servlet.user;

import com.lz.entity.User;
import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;
import com.lz.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/validate/email")
public class ValidateEmailServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email=req.getParameter("email");
        String type=req.getParameter("type");

        User user=getCurrUser(req);
        if(StringUtils.isNotEmpty(type) && "1".equals(type)){//是验证设置的邮箱
            if(user!=null){
                if(user.getEmail().equals(email)){//邮箱没有修改
                    renderTxt("true",resp);
                    return;
                }
            }
        }
        //邮箱修改或注册会执行
        UserService userService=new UserService();
        boolean result=userService.findByEmail(email);
        if(result){//没找到,可以使用
            renderTxt("true",resp);
        }else{
            renderTxt("false",resp);
        }
    }
}
