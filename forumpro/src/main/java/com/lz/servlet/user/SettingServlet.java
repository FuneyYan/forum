package com.lz.servlet.user;

import com.google.common.collect.Maps;
import com.lz.dto.JsonResult;
import com.lz.entity.User;
import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;
import com.lz.util.Config;
import com.qiniu.util.Auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/setting")
public class SettingServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auth auth=Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));
        String token=auth.uploadToken(Config.get("qiniu.bucket"));
        req.setAttribute("token",token);
        forward("user/setting",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");

        if("email".equals(action)){
            emailUpdate(req,resp);
        }else if("password".equals(action)){
            passwordUpdate(req,resp);
        }else if("avatar".equals(action)){
            updateAvatar(req,resp);
        }
    }

//    修改头像
    private void updateAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileKey=req.getParameter("fileKey");
        User user=getCurrUser(req);

        UserService userService=new UserService();
        userService.updateAvatar(user,fileKey);

        JsonResult result=new JsonResult();
        result.setState(JsonResult.SUCCESS);
        renderJson(result,resp);
    }

    //修改密码
    private void passwordUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String oldPassword=req.getParameter("oldpassword");
        String newPassword=req.getParameter("newpassword");
        User user=getCurrUser(req);

        UserService userService=new UserService();
        try{
            userService.updatePassword(oldPassword,newPassword,user);
            renderJson(new JsonResult(),resp);
        }catch (Exception e){
            renderJson(new JsonResult(JsonResult.ERROR,e.getMessage()),resp);
        }
    }

    //修改邮件
    private void emailUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email=req.getParameter("email");
        User user=getCurrUser(req);

        UserService userService=new UserService();
        userService.updateEmail(user,email);

        Map<String,String> result= Maps.newHashMap();
        result.put("state","success");
        renderJson(result,resp);
    }
}
