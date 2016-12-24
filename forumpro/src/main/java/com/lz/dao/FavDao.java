package com.lz.dao;

import com.lz.entity.Fav;
import com.lz.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class FavDao {
    public Fav findFav(Integer userid, Integer topicid) {
        String sql="select * from t_fav where user_id=? and topic_id=?";
        return DbHelp.query(sql,new BeanHandler<Fav>(Fav.class),userid,topicid);
    }

    public void addFav(Fav fav) {
        String sql="insert into t_fav(topic_id,user_id) values(?,?)";
        DbHelp.update(sql,fav.getTopic_id(),fav.getUser_id());
    }

    public void deleteFave(Fav fav) {
        String sql="delete from t_fav where user_id=? and topic_id=?";
        DbHelp.update(sql,fav.getUser_id(),fav.getTopic_id());
    }
}
