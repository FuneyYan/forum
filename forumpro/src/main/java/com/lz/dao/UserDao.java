package com.lz.dao;

import ch.qos.logback.classic.db.DBHelper;
import com.lz.entity.User;
import com.lz.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class UserDao {

    public User findByUserName(String username) {
        String sql="select * from t_user where username=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),username);
    }

    public boolean findByEmail(String email) {
        String sql="select * from t_user where email=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),email)==null;
    }
    public User findByEmailUser(String email) {
        String sql="select * from t_user where email=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),email);
    }

    public void save(User user) {
        String sql="insert into t_user(username,password,email,phone,state,avatar) values(?,?,?,?,?,?)";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar());
    }

    public void activeUser(User user) {
        String sql="UPDATE  t_user SET password=?,email=?,phone=?,state=?,avatar=? where username=?";
//        System.out.println("user:  "+user);
        DbHelp.update(sql,user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar(),user.getUsername());
    }

    public User findById(int i) {
        String sql="select * from t_user where id=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),i);
    }

    public void update(User user) {
        String sql = "update t_user set password=?,email=?,phone=?,state=?,avatar=? where id = ?";
        DbHelp.update(sql,user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar(),user.getId());
    }
}
