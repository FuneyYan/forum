package com.lz.dao;

import com.lz.entity.Admin;
import com.lz.entity.User;
import com.lz.util.DbHelp;
import com.lz.vo.UserVo;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

public class AdminDao {

    public Admin findAdminByName(String adminname) {
        String sql="select * from t_admin where adminname=?";
        return DbHelp.query(sql,new BeanHandler<Admin>(Admin.class),adminname);
    }

    public void delTopicById(Integer topicid) {
        String sql="delete from t_topic where id=?";
        DbHelp.update(sql,topicid);
    }


    public Integer count() {
        String sql="select count(*) from t_user ";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<User> findAllUsers() {
        String sql="select * from t_user where state!=0";
        return DbHelp.query(sql,new BeanListHandler<User>(User.class));
    }

    public UserVo findUserVoById(Integer id) {
        String sql="SELECT tu.id,tu.username,tu.createtime,logintime,tll.ip,tu.state FROM  t_user tu,t_login_log tll WHERE tu.id=tll.userid AND tu.id=? ORDER BY tll.logintime desc limit 0,1";
        return DbHelp.query(sql,new BeanHandler<UserVo>(UserVo.class),id);
    }

}
