package com.lz.servlet.admin;

import com.lz.entity.TopicReplyCount;
import com.lz.service.TopicService;
import com.lz.servlet.BasicServlet;
import com.lz.util.Page;
import com.lz.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/home")
public class HomeServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p=req.getParameter("p");
        TopicService topicService=new TopicService();
        Integer pageNo= StringUtils.isNumeric(p)?Integer.valueOf(p):1;
        Page<TopicReplyCount> page=topicService.findAllTopicReplyCountByDayList(pageNo);
        req.setAttribute("page",page);
        forward("/admin/home",req,resp);
    }

}
