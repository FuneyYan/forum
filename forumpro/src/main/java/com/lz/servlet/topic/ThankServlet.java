package com.lz.servlet.topic;

import com.lz.dto.JsonResult;
import com.lz.entity.Topic;
import com.lz.service.TopicService;
import com.lz.servlet.BasicServlet;
import com.lz.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/thank")
public class ThankServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid=req.getParameter("topicid");
        String userid=req.getParameter("userid");
        String action=req.getParameter("action");
        TopicService topicService=new TopicService();
        JsonResult result=null;


        if(StringUtils.isNumeric(topicid) && StringUtils.isNumeric(userid)){
            if(action.equals("unthank")){//收藏
                topicService.addThank(topicid,userid);
            }else{//取消收藏
                topicService.deleteThank(topicid,userid);
            }
            Topic topic=topicService.findTopicById(topicid);
            renderJson(new JsonResult(topic.getThanknum()),resp);
        }else{
            resp.sendError(404);
        }
    }
}
