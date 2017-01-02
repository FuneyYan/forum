package com.lz.servlet.topic;

import com.lz.dto.JsonResult;
import com.lz.entity.*;
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
        User user=getCurrUser(req);
        try {
            Topic topic = topicService.findTopicById(topicid);
            topic.setClicknum(topic.getClicknum()+1);
            topicService.updateTopic(topic);
            if(user!=null){
                Fav fav=topicService.findFav(user.getId(),topicid);
                req.setAttribute("fav",fav);
                Thank thank=topicService.findThank(user.getId(),topicid);
                req.setAttribute("thank",thank);
            }
            req.setAttribute("topic",topic);

//            获取回复列表
            List<Reply> replyList=topicService.findReplyListById(topicid);
            req.setAttribute("replyList",replyList);

            forward("topic/topicDetail",req,resp);

        }catch (Exception e){
//            e.printStackTrace();
            resp.sendError(404,"该帖不存在");
        }
    }
}
