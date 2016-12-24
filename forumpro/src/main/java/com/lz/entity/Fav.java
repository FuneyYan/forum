package com.lz.entity;

import java.sql.Timestamp;

public class Fav {
    private Integer user_id;
    private Integer topic_id;
    private Timestamp cretetime;

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

    public Timestamp getCretetime() {
        return cretetime;
    }

    public void setCretetime(Timestamp cretetime) {
        this.cretetime = cretetime;
    }

    @Override
    public String toString() {
        return "Fav{" +
                "user_id=" + user_id +
                ", topic_id=" + topic_id +
                ", cretetime=" + cretetime +
                '}';
    }
}
