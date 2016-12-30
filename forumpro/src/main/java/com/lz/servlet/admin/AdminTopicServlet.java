package com.lz.servlet.admin;

import com.lz.dto.JsonResult;
import com.lz.entity.Topic;
import com.lz.service.AdminService;
import com.lz.service.TopicService;
import com.lz.servlet.BasicServlet;
import com.lz.util.Page;
import com.lz.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/topic")
public class AdminTopicServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p=req.getParameter("p");
        TopicService topicService=new TopicService();
        Integer pageNo= StringUtils.isNumeric(p)?Integer.valueOf(p):1;

        Page<Topic> topicPage=topicService.findAllTopicByNodeid("",pageNo);
//        获取所有帖子
        req.setAttribute("topicList",topicPage);
        forward("/admin/topic",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid=req.getParameter("topicid");
        AdminService adminService=new AdminService();
        if(StringUtils.isNumeric(topicid)){
            try {
                adminService.delTopicByid(topicid);
                renderJson(new JsonResult(JsonResult.SUCCESS),resp);
            }catch (RuntimeException e){
                renderJson(new JsonResult(JsonResult.ERROR,e.getMessage()),resp);
            }
        }else{
            renderTxt("topicid无效",resp);
        }
    }
}
