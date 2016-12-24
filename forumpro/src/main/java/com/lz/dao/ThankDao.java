package com.lz.dao;

import com.lz.entity.Fav;
import com.lz.entity.Thank;
import com.lz.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Administrator on 2016/12/24.
 */
public class ThankDao {
    public Thank findThank(Integer userid, Integer topicid) {
        String sql="select * from t_thank where user_id=? and topic_id=?";
        return DbHelp.query(sql,new BeanHandler<Thank>(Thank.class),userid,topicid);
    }

    public void addThank(Thank thank) {
        String sql="insert into t_thank(topic_id,user_id) values(?,?)";
        DbHelp.update(sql,thank.getTopic_id(),thank.getUser_id());
    }

    public void deleteThank(Thank thank) {
        String sql="delete from t_thank where user_id=? and topic_id=?";
        DbHelp.update(sql,thank.getUser_id(),thank.getTopic_id());
    }
}
