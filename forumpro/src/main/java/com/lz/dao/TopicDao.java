package com.lz.dao;

import com.lz.entity.Topic;
import com.lz.entity.TopicReplyCount;
import com.lz.entity.User;
import com.lz.util.Config;
import com.lz.util.DbHelp;
import com.lz.util.StringUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TopicDao {

    public Integer addTopic(Topic topic) {
        String sql="insert into t_topic(title,content,node_id,user_id,lastreplytime) values(?,?,?,?,?)";
        return DbHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getNode_id(),topic.getUser_id(),topic.getLastreplytime());
    }

    public Topic findTopicById(Integer topicid) {
        String sql="select * from t_topic where id=?";
        return DbHelp.query(sql,new BeanHandler<Topic>(Topic.class),topicid);

    }

    public void update(Topic topic) {
        String sql="update t_topic set title = ? ,content = ? ,clicknum = ?,favnum = ?,thanknum = ?,replynum = ?,lastreplytime = ?, node_id = ?,user_id = ? where id = ?";
        DbHelp.update(sql,topic.getTitle(),topic.getContent(),topic.getClicknum(),topic.getFavnum(),topic.getThanknum(),topic.getReplynum(),topic.getLastreplytime(),topic.getNode_id(),topic.getUser_id(),topic.getId());
    }

    public int count() {
        String sql="select count(*) from t_topic";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public int count(String nodeid) {
        String sql="select count(*) from t_topic where node_id=?";
        return DbHelp.query(sql,new ScalarHandler<Long>(),nodeid).intValue();
    }

    public List<Topic> findAll(Map<String, Object> map) {
        String sql="SELECT tu.username,tu.avatar,tt.* from t_topic tt,t_user tu WHERE tt.user_id=tu.id";
        String nodeid=String.valueOf(map.get("nodeid"))=="null"?null:String.valueOf(map.get("nodeid"));
        String where=" ";
        List<Object> array=new ArrayList<>();
        if(StringUtils.isNotEmpty(nodeid)){
            where+="and tt.node_id=?";
            array.add(nodeid);
        }
        where +=" order by lastreplytime desc limit ?,?";
        array.add(map.get("start"));
        array.add(map.get("pageSize"));
        sql+=where;
        return DbHelp.query(sql, new AbstractListHandler<Topic>() {
            @Override
            protected Topic handleRow(ResultSet rs) throws SQLException {
                Topic topic = new BasicRowProcessor().toBean(rs,Topic.class);
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setAvatar(Config.get("qiniu.domain") + rs.getString("avatar"));
                topic.setUser(user);
                return topic;
            }
        },array.toArray());
    }

    public int countTopicByDay() {
        String sql = "select count(*) from (select count(*) from t_topic group by DATE_FORMAT(createtime,'%y-%m-%d')) AS topicCount";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }


    public List<TopicReplyCount> getTopicAndReplyNumList(Integer start ,Integer pageSize) {
        String sql = "SELECT COUNT(*) topicnum,DATE_FORMAT(createtime,'%y-%m-%d') 'time',\n" +
                "(SELECT COUNT(*) FROM t_reply WHERE DATE_FORMAT(createtime,'%y-%m-%d') \n" +
                "= DATE_FORMAT(t_topic.createtime,'%y-%m-%d')) 'replynum'\n" +
                "FROM t_topic GROUP BY (DATE_FORMAT(createtime,'%y-%m-%d')) \n" +
                "ORDER BY (DATE_FORMAT(createtime,'%y-%m-%d')) DESC limit ?,?;";

        return DbHelp.query(sql,new BeanListHandler<TopicReplyCount>(TopicReplyCount.class),start,pageSize);
    }
}
