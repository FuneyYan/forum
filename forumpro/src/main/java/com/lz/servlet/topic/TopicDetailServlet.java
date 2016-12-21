package com.lz.servlet.topic;

import com.lz.entity.Topic;
import com.lz.service.TopicService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/topicDetail")
public class TopicDetailServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid=req.getParameter("topicid");
        TopicService topicService=new TopicService();
        try {
            Topic topic = topicService.findTopicById(topicid);
            req.setAttribute("topic",topic);
            forward("topic/topicDetail",req,resp);

        }catch (Exception e){
            resp.sendError(404);
        }
    }
}
