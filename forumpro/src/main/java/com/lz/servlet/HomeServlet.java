package com.lz.servlet;

import com.lz.entity.Node;
import com.lz.entity.Topic;
import com.lz.service.TopicService;
import com.lz.util.Page;
import com.lz.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/home")
public class HomeServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nodeid=req.getParameter("nodeid");
        String p=req.getParameter("p");
        Integer pageNo=StringUtils.isNumeric(p)?Integer.valueOf(p):1;
        if (!StringUtils.isEmpty(nodeid) && !StringUtils.isNumeric(nodeid)){
            forward("index",req,resp);
            return;
        }

        TopicService topicService=new TopicService();
        List<Node> list=topicService.getAllNode();

        Page<Topic> page=topicService.findAllTopicByNodeid(nodeid,pageNo);
        req.setAttribute("nodeList",list);
        req.setAttribute("page",page);
        forward("index",req,resp);
    }
}
