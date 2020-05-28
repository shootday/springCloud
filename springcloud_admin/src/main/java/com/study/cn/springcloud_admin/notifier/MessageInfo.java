package com.study.cn.springcloud_admin.notifier;

/**
 * @program: springCloudAll
 * @Date: 2020/4/21 17:17
 * @Author: lzx
 * @Description:
 */
public class MessageInfo {

    private String content;
    public MessageInfo(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
