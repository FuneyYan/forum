package com.lz.servlet.admin;

import com.lz.dao.AdminDao;
import com.lz.dto.JsonResult;
import com.lz.entity.Admin;
import com.lz.service.AdminService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends BasicServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("admin/login",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminService adminService=new AdminService();
        String adminname=req.getParameter("adminname");
        String password=req.getParameter("password");
        String ip=req.getRemoteAddr();
        try {
            Admin admin=adminService.findAdminByName(adminname,password,ip);
            req.getSession().setAttribute("curr_admin",admin);
            renderJson(new JsonResult(),resp);
        }catch (Exception e){
            renderJson(new JsonResult(e.getMessage()),resp);
        }


    }
}
