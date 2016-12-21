package com.lz.dao;

import com.lz.entity.Node;
import com.lz.entity.Topic;
import com.lz.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class TopicDao {

    public Integer addTopic(Topic topic) {
        String sql="insert into t_topic(title,content,node_id,user_id) values(?,?,?,?)";
        return DbHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getNode_id(),topic.getUser_id());
    }

    public Topic findTopicById(Integer topicid) {
        String sql="select * from t_topic where id=?";
        return DbHelp.query(sql,new BeanHandler<Topic>(Topic.class),topicid);

    }
}
