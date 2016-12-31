package com.lz.servlet.admin;

import com.lz.dto.JsonResult;
import com.lz.entity.Node;
import com.lz.service.AdminService;
import com.lz.service.NodeService;
import com.lz.service.TopicService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/node")
public class AdminNodeServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取所有节点
        TopicService topicService=new TopicService();
        List<Node> list=topicService.getAllNode();
        req.setAttribute("nodeList",list);
        forward("/admin/node",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid=req.getParameter("nodeid");
        NodeService nodeService=new NodeService();
        try{
            nodeService.delNodeById(nodeid);
            renderJson(new JsonResult(),resp);
        }catch(RuntimeException e){
            renderJson(new JsonResult(e.getMessage()),resp);
        }
    }
}
