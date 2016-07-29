package com.wangj.itemclickexpanddemo.bean;

/**
 * Created by wangrui on 2016/7/27.
 */
public class ReplyBean {
    private String target;
    private String content;
    private boolean isvisible;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isvisible() {
        return isvisible;
    }

    public void setIsvisible(boolean isvisible) {
        this.isvisible = isvisible;
    }

    @Override
    public String toString() {
        return "ReplyBean{" +
                "target='" + target + '\'' +
                ", content='" + content + '\'' +
                ", isvisible=" + isvisible +
                '}';
    }
}
