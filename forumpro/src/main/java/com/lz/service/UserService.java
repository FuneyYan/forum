package com.lz.service;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lz.dao.LoginlogDao;
import com.lz.dao.UserDao;
import com.lz.dto.JsonResult;
import com.lz.entity.Loginlog;
import com.lz.entity.User;
import com.lz.util.Config;
import com.lz.util.EmailUtil;
import com.lz.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserService {
    private UserDao userDao=new UserDao();
    private LoginlogDao logDao=new LoginlogDao();


    private static Cache<String,String> cache= CacheBuilder.newBuilder()
            .expireAfterWrite(6, TimeUnit.HOURS)
            .build();//发送邮件的token缓存

    private static Cache<String,String> passwordCache=CacheBuilder.newBuilder()
            .expireAfterWrite(1,TimeUnit.HOURS)
            .build();//找回密码缓存

    private static Cache<String,String> tokenCache=CacheBuilder.newBuilder()
            .expireAfterWrite(60,TimeUnit.SECONDS)
            .build();//发送邮件后,防止用户连续点击发送邮件

    /**
     * 根据用户名查找
     * @param username 用户名
     * @return 对象
     */
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    /**
     * 根据邮箱查找
     * @param email 邮箱地址
     * @return 布尔
     */
    public boolean findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * 保存对象(注册),并发送邮件
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱地址
     * @param phone 手机号
     */
    public void save(String username,String password,String email,String phone) {
        User user=new User();
        user.setUsername(username);
        user.setPassword(DigestUtils.md5Hex(Config.get("password.salt")+password));//密码加盐加密
        user.setEmail(email);
        user.setPhone(phone);
        user.setState(User.USER_UNACTIVE);
        user.setAvatar(User.USER_DEFAULT_AVATAR);
        userDao.save(user);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {//异步发送邮件
                String uuid= UUID.randomUUID().toString();
                String url="http://localhost/user/active?_="+uuid;

                cache.put(uuid,username);//放入缓存6个小时

                String html="<h3>please click <a href='"+url+"'>here</a> active you account</h3>";
                EmailUtil.sendHtmlEmail(email,"用户邮件激活",html);

            }
        });
        thread.start();
    }

    /**
     * 激活账户
     * @param token 对应save()的uuid,与username对应,在缓存中6小时有效
     */
    public void activeByToken(String token) {
        String username=cache.getIfPresent(token);
        if (StringUtils.isEmpty(username)){
            throw new RuntimeException("token无效或过期");
        }else{
            User user=userDao.findByUserName(username);
            if(user==null){
                throw new RuntimeException("无法找到用户");
            }else{
                user.setState(User.USER_ACTIVE);//激活用户
                userDao.activeUser(user);
                cache.invalidate(token);
            }
        }
    }

    /**
     * 用户登陆的记录
     * @param username 账户
     * @param password 密码
     * @param ip ip地址
     * @return 对象
     */
    public User login(String username, String password, String ip) {
        User user=userDao.findByUserName(username);
        if(user!=null && user.getPassword().equals
                (DigestUtils.md5Hex(Config.get("password.salt")+password))){
            if(user.getState()==User.USER_ACTIVE){
                Loginlog loginlog=new Loginlog();
                loginlog.setIp(ip);
                loginlog.setUserid(user.getId());
                logDao.userLogin(loginlog);
                return user;
            }else if(user.getState()==User.USER_UNACTIVE){
                throw new RuntimeException("账户未激活");
            }else{
                throw new RuntimeException("账户禁用");
            }
        }else{
            throw new RuntimeException("用户名或密码错误");
        }
    }


    /**
     * 找回密码
     * @param token 客户端的sessionID,防止频繁发送,1分钟
     * @param type 用户选择的找回密码方式
     * @param value 邮箱地址或者手机号
     */
    public void foundPassword(String token,String type, String value) {
        if(tokenCache.getIfPresent(token)==null){
            if("email".equals(type)){
                User user=userDao.findByEmailUser(value);
                if(user!=null){
                    String uuid=UUID.randomUUID().toString();
                    String url="http://localhost/foundPassword/newPassword?_="+uuid;
                    String html="<h3>点击<a href='"+url+"'>该连接</a>找回密码</h3>";

                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            EmailUtil.sendHtmlEmail(value,"找回密码",html);
                            //发送邮件后生成token放入缓存
                            passwordCache.put(uuid,user.getUsername());
                        }
                    });
                    thread.start();
                }
                tokenCache.put(token,"___");
            }else if("phone".equals(type)){
                // TODO: 2016/12/17
            }
        }else{
            throw new RuntimeException("操作频繁");
        }

    }

    /**
     * 查找用户(根据token找用户名)
     * @param token 密码缓存区uuid
     * @return
     */
    public User tokenFindUser(String token) {
        String username=passwordCache.getIfPresent(token);

        if(StringUtils.isEmpty(username)){
            throw new RuntimeException("token过期或不存在");
        }else{
            User user=userDao.findByUserName(username);
            if(user==null){
                throw new RuntimeException("用户不存在");
            }else{
                return user;
            }
        }

    }

    /**
     *  重置密码
     * @param id 用户id
     * @param token 密码缓存区uuid
     * @param password 新密码
     */
    public void resetPassword(String id, String token, String password) {
        String username=passwordCache.getIfPresent(token);
        if(StringUtils.isEmpty(username)){
            throw new RuntimeException("token过期或无效");
        }else{
            User user=userDao.findById(Integer.parseInt(id));
            if(user==null){
                throw new RuntimeException("用户不存在");
            }else{
                user.setPassword(DigestUtils.md5Hex(Config.get("password.salt")+password));
                userDao.update(user);
            }
        }

    }

    /**
     * 修改电子邮件
     * @param user
     * @param email
     */
    public void updateEmail(User user, String email) {
        user.setEmail(email);
        userDao.update(user);
    }

    /**
     * 修改登陆密码
     * @param oldPassword 原始密码
     * @param newPassword 新密码
     * @param user session(当前登陆)中的用户
     */
    public void updatePassword(String oldPassword, String newPassword, User user) {

        if(DigestUtils.md5Hex(Config.get("password.salt")+oldPassword).equals(user.getPassword())){
            user.setPassword(DigestUtils.md5Hex(Config.get("password.salt")+newPassword));
            userDao.update(user);

        }else{
            throw new RuntimeException("原始密码不正确");
        }

    }

//    修改用户的头像
    public void updateAvatar(User user, String fileKey) {
        user.setAvatar(fileKey);
        userDao.update(user);
    }
}
