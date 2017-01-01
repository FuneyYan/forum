package com.lz.service;

import com.lz.dao.*;
import com.lz.entity.*;
import com.lz.util.Config;
import com.lz.util.Page;
import com.lz.util.StringUtils;
import com.lz.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AdminService {
    Logger logger= LoggerFactory.getLogger(AdminService.class);
    TopicService topicService=new TopicService();
    AdminDao adminDao=new AdminDao();
    ReplyDao replyDao=new ReplyDao();
    NodeDao nodeDao=new NodeDao();
    UserDao userDao=new UserDao();
//    管理员登陆
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

//    管理员删除主题
    public void delTopicByid(String topicid) {
        if(StringUtils.isNumeric(topicid)){
//            删除主题前先删除主题对应的回复
            replyDao.delReplyByTopicId(Integer.valueOf(topicid));

            Topic topic=topicService.findTopicById(topicid);
            if(topic!=null){
//                更新节点的主题数量
                Node node=nodeDao.findById(topic.getNode_id());
//                删除主题
                adminDao.delTopicById(Integer.valueOf(topicid));
                node.setTopicnum(node.getTopicnum()-1);
                nodeDao.update(node);
            }else{
                throw new RuntimeException("您要删除的主题不存在");
            }

        }else{
            throw new RuntimeException("topicid无效");
        }
    }


    public Page<UserVo> findAllUser(Integer pno) {
        Integer count=0;
        count=adminDao.count();
        Page<UserVo> page=new Page<>(count,pno);
        List<User> userList=adminDao.findAllUsers();
        List<UserVo> uservoList=new ArrayList<UserVo>();
        for (User user:userList) {
            UserVo uservo=adminDao.findUserVoById(user.getId());
            uservoList.add(uservo);
        }

        page.setItems(uservoList);
        return page;
    }

    public void updateUserState(String userid, String userstate) {
        if(StringUtils.isNumeric(userid) && StringUtils.isNumeric(userstate)){
            User user=userDao.findById(Integer.valueOf(userid));
            if(user!=null){
                if(userstate.equals("1")){
                    user.setState(User.USER_DISABLED);
                }else{
                    user.setState(User.USER_ACTIVE);
                }
                userDao.update(user);
            }else{
                throw new RuntimeException("用户不存在");
            }
        }else{
            throw new RuntimeException("参数错误");
        }
    }
}
