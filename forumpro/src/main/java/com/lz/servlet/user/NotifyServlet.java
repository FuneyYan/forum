package com.lz.servlet.user;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.lz.dto.JsonResult;
import com.lz.entity.Notify;
import com.lz.entity.User;
import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/notify")
public class NotifyServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user=getCurrUser(req);
        UserService userService=new UserService();
        try{
            List<Notify> list=userService.findAllNotifyByUserid(user.getId());
            req.setAttribute("notifyList",list);
            forward("user/notify",req,resp);
        }catch (Exception e){
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService=new UserService();
        User user=getCurrUser(req);
        if(user!=null){
            List<Notify> list=userService.findAllNotifyByUserid(user.getId());
            List<Notify> unreadList= Lists.newArrayList(Collections2.filter(list, new Predicate<Notify>() {
                @Override
                public boolean apply(Notify notify) {
                    return notify.getState()==Notify.NOTIFY_STATE_UNREAD;
                }
            }));
            renderJson(new JsonResult(unreadList.size()),resp);
        }

    }
}
