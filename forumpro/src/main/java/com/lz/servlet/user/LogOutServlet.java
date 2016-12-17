package com.lz.servlet.user;

import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogOutServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
//        session.removeAttribute("curr_user");
        session.invalidate();
//        resp.sendRedirect("/login");
        req.setAttribute("message","您已经安全退出");
        forward("user/login",req,resp);
    }
}
