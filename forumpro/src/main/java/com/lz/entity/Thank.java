package com.lz.entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/24.
 */
public class Thank {
    private Integer user_id;
    private Integer topic_id;
    private Timestamp createtime;



    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }


    @Override
    public String toString() {
        return "Thank{" +
                "user_id=" + user_id +
                ", topic_id=" + topic_id +
                ", createtime=" + createtime +
                '}';
    }
}


