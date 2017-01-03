package com.lz.servlet.topic;

import com.lz.dto.JsonResult;
import com.lz.entity.Node;
import com.lz.entity.Topic;
import com.lz.service.TopicService;
import com.lz.servlet.BasicServlet;
import com.lz.util.Config;
import com.lz.util.StringUtils;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/topicEdit")
public class TopicEditServlet extends BasicServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Auth auth=Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));

        StringMap stringMap=new StringMap();
        stringMap.put("returnBody","{ \"success\": true,\"file_path\": \""+Config.get("qiniu.domain")+"${key}\"}");
        String token=auth.uploadToken(Config.get("qiniu.bucket"),null,3600,stringMap);
        req.setAttribute("token",token);

        String topicid=req.getParameter("topicid");
        TopicService topicService=new TopicService();
        if(StringUtils.isNumeric(topicid)){
            Topic topic=topicService.findTopicById(topicid);
            List<Node> list=topicService.getAllNode();
            if(topic!=null){
                req.setAttribute("topic",topic);
                req.setAttribute("nodeList",list);
                forward("topic/topicEdit",req,resp);
            }else{
                resp.sendError(404);
            }
        }else{
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title=req.getParameter("title");
        String content=req.getParameter("content");
        String nodeid=req.getParameter("nodeid");
        String topicid=req.getParameter("topicId");

        TopicService topicService=new TopicService();
        try{
            topicService.updateTopicByid(title,content,nodeid,topicid);
            JsonResult result=new JsonResult();
            result.setState(JsonResult.SUCCESS);
            result.setData(topicid);
            renderJson(result,resp);
        }catch (Exception e){
            renderJson(new JsonResult(e.getMessage()),resp);
        }
    }
}
