package com.lz.dao;

import com.lz.entity.Reply;
import com.lz.util.DbHelp;


public class ReplyDao {

    public void addReply(Reply reply){
        String sql="insert into t_reply(content,topic_id,user_id) values(?,?,?)";
        DbHelp.update(sql,reply.getContent(),reply.getTopic_id(),reply.getUser_id());
    }

}
