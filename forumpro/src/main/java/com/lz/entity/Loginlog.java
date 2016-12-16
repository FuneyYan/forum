package com.lz.entity;

import java.sql.Timestamp;

public class Loginlog {
    private Integer id;
    private Timestamp logintime;
    private String ip;
    private Integer userid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getLogintime() {
        return logintime;
    }

    public void setLogintime(Timestamp logintime) {
        this.logintime = logintime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Loginlog{" +
                "id=" + id +
                ", logintime=" + logintime +
                ", ip='" + ip + '\'' +
                ", userid=" + userid +
                '}';
    }
}
