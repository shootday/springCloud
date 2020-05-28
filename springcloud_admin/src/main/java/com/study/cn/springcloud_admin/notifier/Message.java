package com.study.cn.springcloud_admin.notifier;

/**
 * @program: springCloudAll
 * @Date: 2020/4/21 17:17
 * @Author: lzx
 * @Description:  消息类
 */
public class Message {

    private String msgtype;
    private MessageInfo text;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public MessageInfo getText() {
        return text;
    }

    public void setText(MessageInfo text) {
        this.text = text;
    }
}


