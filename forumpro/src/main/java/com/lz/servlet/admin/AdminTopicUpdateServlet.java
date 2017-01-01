package com.lz.servlet.admin;

import com.lz.dto.JsonResult;
import com.lz.service.AdminService;
import com.lz.service.TopicService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/topicUpdate")
public class AdminTopicUpdateServlet extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid=req.getParameter("nodeid");
        String topicid=req.getParameter("topicid");
        AdminService adminService=new AdminService();
        try{
            adminService.updateTopicNode(nodeid,topicid);
            renderJson(new JsonResult(),resp);
        }catch(RuntimeException e){
            renderJson(new JsonResult(e.getMessage()),resp);
        }

    }
}
