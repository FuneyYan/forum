package com.lz.dto;

public class JsonResult {

    public static final String SUCCESS="success";
    public static final String ERROR="error";

    private String state;
    private String message;
    private Object data;

//    成功并且不需要返回数据的话什么构造参数都不需要
    public JsonResult(){
        this.state=SUCCESS;
    }

//    成功并且需要返回数据
    public JsonResult(Object data) {
        this.state = SUCCESS;
        this.data = data;
    }

    //错误返回错误信息
    public JsonResult(String message){
        this.state=ERROR;
        this.data=message;
    }


//    失败不返回数据
    public JsonResult(String state,String message){
        this.state=state;
        this.message=message;
    }

//    失败返回数据
    public JsonResult(String state,String message,Object data){
        this.state=state;
        this.message=message;
        this.data=data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
