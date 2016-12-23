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
import com.lz.util.DbHelp;
import com.lz.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
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
        //设置当前时间为该主题的最后回复时间
        topic.setLastreplytime(new Timestamp(new Date().getTime()));
        topic.setId(topicDao.addTopic(topic));//insert返回对象id,并设置后返回对象

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
                //设置点击次数
                topic.setClicknum(topic.getClicknum()+1);
                topicDao.update(topic);

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

        Topic topic=topicDao.findTopicById(Integer.valueOf(topicid));
        if(topic!=null){
//          跟新最后一条回复时间
            topic.setLastreplytime(new Timestamp(new Date().getTime()));
            topicDao.update(topic);
        }else{
            throw new RuntimeException("该贴不存在或已删除");
        }

        replyDao.addReply(reply);
    }

    public List<Reply> findReplyListById(String id){
        return replyDao.findListByTopicId(Integer.valueOf(id));
    }
}
