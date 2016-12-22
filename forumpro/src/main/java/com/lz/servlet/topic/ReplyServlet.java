package com.lz.servlet.topic;

import com.lz.entity.User;
import com.lz.service.TopicService;
import com.lz.servlet.BasicServlet;
import com.lz.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addReply")
public class ReplyServlet extends BasicServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId=req.getParameter("topicid");
        if(StringUtils.isNumeric(topicId)){
            String content=req.getParameter("content");
            User user= (User) req.getSession().getAttribute("curr_user");
            TopicService topicService=new TopicService();

            topicService.addReply(content,Integer.valueOf(topicId),user.getId());

            resp.sendRedirect("/topicDetail?topicid="+topicId);
        }else{
            resp.sendError(404);
        }
    }
}
