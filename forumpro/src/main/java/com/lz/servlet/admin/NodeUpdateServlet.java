package com.lz.servlet.admin;

import com.lz.dto.JsonResult;
import com.lz.entity.Node;
import com.lz.service.NodeService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/nodeUpdate")
public class NodeUpdateServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid=req.getParameter("nodeid");
        NodeService nodeService=new NodeService();
        try {
            Node node=nodeService.findNodeById(nodeid);
            req.setAttribute("node",node);
            forward("/admin/nodeUpdate",req,resp);
        }catch (RuntimeException e){
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid=req.getParameter("nodeid");
        String nodename=req.getParameter("nodename");
        NodeService nodeService=new NodeService();
        try{
            nodeService.updateNode(nodeid,nodename);
            renderJson(new JsonResult(),resp);
        }catch (RuntimeException e){
            renderJson(new JsonResult(e.getMessage()),resp);
        }
    }
}
