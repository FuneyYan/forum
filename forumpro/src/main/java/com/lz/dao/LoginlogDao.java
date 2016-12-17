package com.lz.dao;

import com.lz.entity.Loginlog;
import com.lz.util.DbHelp;

public class LoginlogDao {
    public void userLogin(Loginlog loginlog){
        String sql="insert into t_login_log(ip,userid) values(?,?)";
        DbHelp.update(sql,loginlog.getIp(),loginlog.getUserid());
    }
}
