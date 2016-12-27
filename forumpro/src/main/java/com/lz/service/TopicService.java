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
    NotifyDao notifyDao=new NotifyDao();

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

    /**
     * 增加回复
     * @param content 回复内容
     * @param topicid 回复的主题
     * @param userid 回复人的id
     */
    public void addReply(String content, Integer topicid, Integer userid) {
        Reply reply=new Reply();
        reply.setContent(content);
        reply.setTopic_id(topicid);
        reply.setUser_id(userid);
        replyDao.addReply(reply);

        Topic topic=topicDao.findTopicById(Integer.valueOf(topicid));
        if(topic!=null){
//          更新最后一条回复时间和回复数量
            topic.setLastreplytime(new Timestamp(new Date().getTime()));
            topic.setReplynum(topic.getReplynum()+1);
            topicDao.update(topic);
        }else{
            throw new RuntimeException("该贴不存在或已删除");
        }
//          增加通知
        if(userid!=topic.getUser_id()){//不是自己给自己的回复的话
            Notify notify=new Notify();
            notify.setContent("您的主题<a href='topicDetail?topicid="+topicid+"'>["+topic.getTitle()+"]</a>有了新的回复,请查看");
            notify.setUserid(topic.getUser_id());
            notify.setState(Notify.NOTIFY_STATE_UNREAD);
            notifyDao.addNotify(notify);
        }

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

    /**
     * 根据选择的节点查找主题并分页展示
     * @param nodeid 节点id
     * @param pageNo 分页号
     * @return
     */
    public Page<Topic> findAllTopicByNodeid(String nodeid, Integer pageNo) {
        Map<String,Object> map= Maps.newHashMap();
        int count=0;
        if(StringUtils.isEmpty(nodeid)){
            count=topicDao.count();//查询全部
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
