package com.lz.servlet.admin;

import com.lz.dto.JsonResult;
import com.lz.entity.User;
import com.lz.service.AdminService;
import com.lz.service.UserService;
import com.lz.servlet.BasicServlet;
import com.lz.util.Page;
import com.lz.util.StringUtils;
import com.lz.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/user")
public class AdminUserServlet extends BasicServlet {
    UserService userService=new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminService adminService=new AdminService();
        String pageNo=req.getParameter("p");
        Integer pno= StringUtils.isNumeric(pageNo)?Integer.valueOf(pageNo):1;
        Page<UserVo> page= adminService.findAllUser(pno);
        req.setAttribute("userList",page);
        forward("/admin/user",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userid=req.getParameter("userid");
        String userstate=req.getParameter("userstate");
        AdminService adminService=new AdminService();
        try{
            adminService.updateUserState(userid,userstate);
            renderJson(new JsonResult(),resp);
        }catch(RuntimeException e){
            renderJson(new JsonResult(e.getMessage()),resp);
        }
    }
}
