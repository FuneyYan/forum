package com.lz.dao;

import com.lz.entity.Admin;
import com.lz.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class AdminDao {

    public Admin findAdminByName(String adminname) {
        String sql="select * from t_admin where adminname=?";
        return DbHelp.query(sql,new BeanHandler<Admin>(Admin.class),adminname);
    }

    public void delTopicById(Integer topicid) {
        String sql="delete from t_topic where id=?";
        DbHelp.update(sql,topicid);
    }
}
