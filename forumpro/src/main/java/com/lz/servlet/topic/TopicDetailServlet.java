package com.lz.servlet.topic;

import com.lz.dto.JsonResult;
import com.lz.entity.Reply;
import com.lz.entity.Topic;
import com.lz.service.TopicService;
import com.lz.servlet.BasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/topicDetail")
public class TopicDetailServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid=req.getParameter("topicid");
        TopicService topicService=new TopicService();
        try {
            Topic topic = topicService.findTopicById(topicid);
            req.setAttribute("topic",topic);

//            获取回复列表
            List<Reply> replyList=topicService.findReplyListById(topicid);
            req.setAttribute("replyList",replyList);

            forward("topic/topicDetail",req,resp);

        }catch (Exception e){
            e.printStackTrace();
            resp.sendError(404);
        }
    }
}
