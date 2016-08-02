package com.tonto.weixin.core.message.request.event;

/**
 * <h2>关注事件和二维码事件消息</h2>
 * <p>
 * 		由于微信在这两个事件上有重复，所以公用一个类
 * </p>
 * @author	TontoZhou
 * @date	2015-5-5
 */
public class QRCodeOrSubscribeEventMessage extends EventMessage{
	
	/*
	 * 事件KEY值，
	 * 如果未关注，则qrscene_为前缀，后面为二维码的参数值
	 * 如果已关注，是一个32位无符号整数，即创建二维码时的二维码scene_id
	 * 
	 * 如果为简单关注/取消关注事件则值为null
	 * 
	 */
	private String EventKey;
	
	//二维码的ticket，可用来换取二维码图片
	//如果为简单关注/取消关注事件则值为null
	private String Ticket;
	
	
	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	
	
}
