package com.lz.servlet.topic;

import com.lz.dto.JsonResult;
import com.lz.entity.Node;
import com.lz.entity.Topic;
import com.lz.entity.User;
import com.lz.service.TopicService;
import com.lz.servlet.BasicServlet;
import com.lz.util.Config;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/addTopic")
public class AddTopicServlet extends BasicServlet {
    TopicService topicService=new TopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Auth auth=Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));

        StringMap stringMap=new StringMap();
        stringMap.put("returnBody","{ \"success\": true,\"file_path\": \""+Config.get("qiniu.domain")+"${key}\"}");
        String token=auth.uploadToken(Config.get("qiniu.bucket"),null,3600,stringMap);
        req.setAttribute("token",token);
//        先查出节点
        List<Node> list=topicService.getAllNode();
        req.setAttribute("listnode",list);
        forward("topic/addtopic",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content=req.getParameter("content");
        String id=req.getParameter("nodeid");
        String title=req.getParameter("title");
        User user= (User) req.getSession().getAttribute("curr_user");

        Topic topic=topicService.addTopic(title,content,Integer.valueOf(id),user.getId());
        JsonResult jsonResult=new JsonResult(topic);
        renderJson(jsonResult,resp);
    }
}
