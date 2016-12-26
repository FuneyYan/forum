package com.lz.service;

import com.google.common.collect.Maps;
import com.lz.dao.*;
import com.lz.entity.*;
import com.lz.util.Config;
import com.lz.util.DbHelp;
import com.lz.util.Page;
import com.lz.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TopicService {

    TopicDao topicDao=new TopicDao();
    NodeDao nodeDao=new NodeDao();
    UserDao userDao=new UserDao();
    ReplyDao replyDao=new ReplyDao();
    FavDao favDao=new FavDao();
    ThankDao thankDao=new ThankDao();

    public List<Node> getAllNode(){
        List<Node> list=nodeDao.findAllNode();
        return list;
    }

    public Topic addTopic(String title, String content,Integer node_id, Integer user_id) {
        Topic topic =new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNode_id(node_id);
        topic.setUser_id(user_id);
        //设置当前时间为该主题的最后回复时间
        topic.setLastreplytime(new Timestamp(new Date().getTime()));
        topic.setId(topicDao.addTopic(topic));//insert返回对象id,并设置后返回对象

        return topic;
    }

    public Topic findTopicById(String topicid) {
        if(StringUtils.isNumeric(topicid)){
            try{
                Topic topic =topicDao.findTopicById(Integer.valueOf(topicid));
                User user=userDao.findById(Integer.valueOf(topic.getUser_id()));
                Node node=nodeDao.findById(Integer.valueOf(topic.getNode_id()));
                user.setAvatar(Config.get("qiniu.domain")+user.getAvatar());
                topic.setUser(user);
                topic.setNode(node);

                return topic;
            }catch(Exception e){
                throw new RuntimeException("该帖不存在或已被删除");
            }
        }else{
            throw new RuntimeException("参数错误");
        }


    }

    public void addReply(String content, Integer topicid, Integer userid) {
        Reply reply=new Reply();
        reply.setContent(content);
        reply.setTopic_id(topicid);
        reply.setUser_id(userid);

        Topic topic=topicDao.findTopicById(Integer.valueOf(topicid));
        if(topic!=null){
//          跟新最后一条回复时间
            topic.setLastreplytime(new Timestamp(new Date().getTime()));
            topicDao.update(topic);
        }else{
            throw new RuntimeException("该贴不存在或已删除");
        }

        replyDao.addReply(reply);
    }

    public List<Reply> findReplyListById(String id){
        return replyDao.findListByTopicId(Integer.valueOf(id));
    }

    /**
     * 查找用户是否收藏了某个主题
     * @param userid 用户
     * @param topicid 主题
     * @return
     */
    public Fav findFav(Integer userid, String topicid) {
        if(StringUtils.isNumeric(topicid)){
            return favDao.findFav(userid,Integer.valueOf(topicid));
        }else{
            throw new RuntimeException("该贴不存在或已经删除");
        }
    }

    /**
     * 添加收藏
     * @param topicid
     * @param userid
     */
    public void addFave(String topicid, String userid) {
        Fav fav=new Fav();
        fav.setTopic_id(Integer.valueOf(topicid));
        fav.setUser_id(Integer.valueOf(userid));
        favDao.addFav(fav);

        Topic topic=topicDao.findTopicById(Integer.valueOf(topicid));
        topic.setFavnum(topic.getFavnum()+1);
        topicDao.update(topic);
    }

    /**
     *  取消收藏
     * @param topicid
     * @param userid
     */
    public void deleteFave(String topicid, String userid) {
        Fav fav=new Fav();
        fav.setTopic_id(Integer.valueOf(topicid));
        fav.setUser_id(Integer.valueOf(userid));
        favDao.deleteFave(fav);

        Topic topic=topicDao.findTopicById(Integer.valueOf(topicid));
        topic.setFavnum(topic.getFavnum()-1);
        topicDao.update(topic);
    }

    /**
     * 更新topic的clicknum
     * @param topic
     */
    public void updateTopic(Topic topic) {
        topicDao.update(topic);
    }

    public void addThank(String topicid, String userid) {
        Thank thank=new Thank();
        thank.setTopic_id(Integer.valueOf(topicid));
        thank.setUser_id(Integer.valueOf(userid));
        thankDao.addThank(thank);

        Topic topic=topicDao.findTopicById(Integer.valueOf(topicid));
        topic.setThanknum(topic.getThanknum()+1);
        topicDao.update(topic);
    }

    public void deleteThank(String topicid, String userid) {
        Thank thank=new Thank();
        thank.setTopic_id(Integer.valueOf(topicid));
        thank.setUser_id(Integer.valueOf(userid));
        thankDao.deleteThank(thank);

        Topic topic=topicDao.findTopicById(Integer.valueOf(topicid));
        topic.setThanknum(topic.getThanknum()-1);
        topicDao.update(topic);
    }

    public Thank findThank(Integer userid, String topicid) {
        if(StringUtils.isNumeric(topicid)){
            return thankDao.findThank(userid,Integer.valueOf(topicid));
        }else{
            throw new RuntimeException("该贴不存在或已经删除");
        }
    }

    public Page<Topic> findAllTopicByNodeid(String nodeid, Integer pageNo) {
        Map<String,Object> map= Maps.newHashMap();
        int count=0;
        if(StringUtils.isEmpty(nodeid)){
            count=topicDao.count();
        }else{
            count=topicDao.count(nodeid);
        }

        Page<Topic> topicPage=new Page<Topic>(count,pageNo);
        map.put("nodeid",nodeid);
        map.put("start",topicPage.getStart());
        map.put("pageSize",topicPage.getPageSize());

        List<Topic> list=topicDao.findAll(map);
        topicPage.setItems(list);
        return topicPage;
    }
}
