package com.lz.service;

import com.lz.dao.NodeDao;
import com.lz.dao.ReplyDao;
import com.lz.dao.TopicDao;
import com.lz.dao.UserDao;
import com.lz.entity.Node;
import com.lz.entity.Reply;
import com.lz.entity.Topic;
import com.lz.entity.User;
import com.lz.util.Config;
import com.lz.util.StringUtils;

import java.util.List;

public class TopicService {

    TopicDao topicDao=new TopicDao();
    NodeDao nodeDao=new NodeDao();
    UserDao userDao=new UserDao();
    ReplyDao replyDao=new ReplyDao();

    public List<Node> getAllNode(){
        List<Node> list=nodeDao.findAllNode();
        return list;
    }

    public Topic addTopic(String title, String content,Integer node_id, Integer user_id) {
        Topic topic =new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNode_id(node_id);
        topic.setUser_id(user_id);
        topic.setId(topicDao.addTopic(topic));

        return topic;
    }

    public Topic findTopicById(String topicid) {
        if(StringUtils.isNumeric(topicid)){
            try{
                Topic topic =topicDao.findTopicById(Integer.valueOf(topicid));
                User user=userDao.findById(Integer.valueOf(topic.getUser_id()));
                Node node=nodeDao.findById(Integer.valueOf(topic.getNode_id()));
                user.setAvatar(Config.get("qiniu.domain")+user.getAvatar());
                topic.setUser(user);
                topic.setNode(node);
                return topic;
            }catch(Exception e){
                throw new RuntimeException("该帖不存在或已被删除");
            }
        }else{
            throw new RuntimeException("参数错误");
        }


    }


    public void addReply(String content, Integer topicid, Integer userid) {
        Reply reply=new Reply();
        reply.setContent(content);
        reply.setTopic_id(topicid);
        reply.setUser_id(userid);
        replyDao.addReply(reply);
    }
}
