package com.lz.service;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lz.dao.UserDao;
import com.lz.entity.User;
import com.lz.util.Config;
import com.lz.util.EmailUtil;
import com.lz.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserService {
    private UserDao userDao=new UserDao();

    private static Cache<String,String> cache= CacheBuilder.newBuilder()
            .expireAfterWrite(6, TimeUnit.HOURS)
            .build();

    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    public boolean findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public void save(String username,String password,String email,String phone) {
        User user=new User();
        user.setUsername(username);
        user.setPassword(DigestUtils.md5Hex(Config.get("password.salt")+password));
        user.setEmail(email);
        user.setPhone(phone);
        user.setState(User.USER_UNACTIVE);
        user.setAvatar(User.USER_DEFAULT_AVATAR);
        userDao.save(user);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                String uuid= UUID.randomUUID().toString();
                String url="http://localhost/user/active?_="+uuid;

                cache.put(uuid,username);//放入缓存6个小时

                String html="<h3>please click <a href='"+url+"'>here</a> active you account</h3>";
                EmailUtil.sendHtmlEmail(email,"用户邮件激活",html);
            }
        });

        thread.start();



    }

    public void activeByToken(String token) {
        String username=cache.getIfPresent(token);
        if (StringUtils.isEmpty(username)){
            throw new RuntimeException("token无效或过期");
        }else{
            System.out.println(username);
            User user=userDao.findByUserName(username);
            if(user==null){
                throw new RuntimeException("无法找到用户");
            }else{
                user.setState(User.USER_ACTIVE);
                userDao.activeUser(user);
                cache.invalidate(token);
            }
        }
    }
}
