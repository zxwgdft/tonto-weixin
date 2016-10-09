package com.tonto.weixin.core.message.request;

/**
 * 请求消息基类
 * 
 * @author TontoZhou
 * 
 */
public abstract class RequestMessage {

	// 开发者微信号
	protected String ToUserName;
	// 发送方帐号（一个OpenID）
	protected String FromUserName;
	// 消息创建时间 （整型）
	protected long CreateTime;
	// 消息类型（text/image/location/link）
	protected String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

}
