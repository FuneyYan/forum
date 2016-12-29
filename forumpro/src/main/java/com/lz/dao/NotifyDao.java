package com.lz.dao;

import com.lz.entity.Notify;
import com.lz.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class NotifyDao {

    public List<Notify> findAllNotifyByUserid(Integer userid) {
        String sql="select * from t_notify where userid=? ORDER BY readtime, createtime";
        return DbHelp.query(sql,new BeanListHandler<Notify>(Notify.class),userid);
    }

    public void addNotify(Notify notify) {
        String sql="insert into t_notify(userid,state,content) values(?,?,?)";
        DbHelp.update(sql,notify.getUserid(),notify.getState(),notify.getContent());
    }

    public void readNotify(Notify notify) {
        String sql="update t_notify set state = ?,readtime = ? where id = ?";
        DbHelp.update(sql,notify.getState(),notify.getReadtime(),notify.getId());
    }



    public Notify findByid(String id) {
        String sql="select * from t_notify where id=?";
        return DbHelp.query(sql,new BeanHandler<Notify>(Notify.class),id);
    }
}
