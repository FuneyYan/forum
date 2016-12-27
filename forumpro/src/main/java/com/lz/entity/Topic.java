package com.lz.entity;


import org.joda.time.DateTime;

import java.sql.Timestamp;

public class Topic {
    private Integer id;
    private String title;
    private String content;
    private Timestamp createtime;
    private Integer clicknum;
    private Integer favnum;
    private Integer thanknum;
    private Integer replynum;
    private Timestamp lastreplytime;
    private Integer node_id;
    private Integer user_id;


    private User user;
    private Node node;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Integer getClicknum() {
        return clicknum;
    }

    public void setClicknum(Integer clicknum) {
        this.clicknum = clicknum;
    }

    public Integer getFavnum() {
        return favnum;
    }

    public void setFavnum(Integer favnum) {
        this.favnum = favnum;
    }

    public Integer getThanknum() {
        return thanknum;
    }

    public void setThanknum(Integer thanknum) {
        this.thanknum = thanknum;
    }

    public Integer getReplynum() {
        return replynum;
    }

    public void setReplynum(Integer replynum) {
        this.replynum = replynum;
    }

    public Timestamp getLastreplytime() {
        return lastreplytime;
    }

    public void setLastreplytime(Timestamp lastreplytime) {
        this.lastreplytime = lastreplytime;
    }

    public Integer getNode_id() {
        return node_id;
    }

    public void setNode_id(Integer node_id) {
        this.node_id = node_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public boolean isEdit(){
        DateTime dateTime=new DateTime(getCreatetime());
        if(dateTime.plusHours(1).isAfterNow() && getReplynum()==0){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createtime=" + createtime +
                ", clicknum=" + clicknum +
                ", favnum=" + favnum +
                ", thanknum=" + thanknum +
                ", replynum=" + replynum +
                ", lastreplytime=" + lastreplytime +
                ", node_id=" + node_id +
                ", user_id=" + user_id +
                ", user=" + user +
                ", node=" + node +
                '}';
    }
}
