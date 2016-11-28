package com.hlzt.commons.model;

import java.util.Date;

import com.hlzt.commons.model.BaseBean;

/**
 * 收到文本消息自动回复，
 * @author Administrator
 *
 */
public class TextReply  extends BaseBean{
  

    private String keyWords;//关键字

    private String replyContent;//回复内容


    private String replyMsgType;//回复消息类型



    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

   

    public String getReplyMsgType() {
        return replyMsgType;
    }

    public void setReplyMsgType(String replyMsgType) {
        this.replyMsgType = replyMsgType;
    }

	@Override
	public String toString() {
		return "TextReply [keyWords=" + keyWords + ", replyContent="
				+ replyContent + ", replyMsgType=" + replyMsgType + "]";
	}
    
    
}