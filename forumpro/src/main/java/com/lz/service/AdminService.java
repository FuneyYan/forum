package com.lz.service;

import com.lz.dao.AdminDao;
import com.lz.entity.Admin;
import com.lz.util.Config;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminService {
    Logger logger= LoggerFactory.getLogger(AdminService.class);
    AdminDao adminDao=new AdminDao();
    public Admin findAdminByName(String adminname,String password,String ip) {
        Admin admin=adminDao.findAdminByName(adminname);
        if(admin!=null){
            if(admin.getPassword().equals((DigestUtils.md5Hex(Config.get("password.salt")+password)))){
                logger.debug("管理员{}登陆了后台管理系统,ip为{}",adminname,ip);
                return admin;
            }else{
                throw new RuntimeException("账号密码错误");
            }
        }else{
            throw new RuntimeException("账号密码错误");
        }
    }
}
