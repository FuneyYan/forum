package com.lz.servlet.user;

import com.lz.dto.JsonResult;
import com.lz.entity.User;
import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/notifyRead")
public class NotifyReadServlet extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids=req.getParameter("ids");
        UserService userService=new UserService();
        User user=getCurrUser(req);
        userService.readNotify(ids);
        renderJson(new JsonResult(),resp);
    }
}
