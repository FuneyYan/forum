package com.lz.dao;

import com.lz.entity.User;
import com.lz.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class UserDao {

    public boolean findByUserName(String username) {
        String sql="select * from t_user where username=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),username)==null;
    }

    public boolean findByEmail(String email) {
        String sql="select * from t_user where email=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),email)==null;
    }

    public void save(User user) {
        String sql="insert into t_user(username,password,email,phone,state,avatar) values(?,?,?,?,?,?)";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar());
    }
}
