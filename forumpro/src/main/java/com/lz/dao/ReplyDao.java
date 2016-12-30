package com.lz.dao;

import com.lz.entity.Reply;
import com.lz.entity.User;
import com.lz.util.Config;
import com.lz.util.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ReplyDao {

    public void addReply(Reply reply){
        String sql="insert into t_reply(content,topic_id,user_id) values(?,?,?)";
        DbHelp.update(sql,reply.getContent(),reply.getTopic_id(),reply.getUser_id());
    }

    public List<Reply> findListByTopicId(Integer id){
        String sql="SELECT tu.id,tu.username,tu.avatar,tr.* from t_reply as tr ,t_user as tu where tr.user_id=tu.id and topic_id=?";
        return DbHelp.query(sql, new AbstractListHandler<Reply>() {
            @Override
            protected Reply handleRow(ResultSet resultSet) throws SQLException {
                Reply reply=new BasicRowProcessor().toBean(resultSet,Reply.class);
                User user=new User();
                user.setAvatar(Config.get("qiniu.domain")+resultSet.getString("avatar"));
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                reply.setUser(user);
                return reply;
            }
        },id);
    }

    public void delReplyByTopicId(Integer topicid) {
        String sql="delete from t_reply where topic_id=?";
        DbHelp.update(sql,topicid);
    }
}
