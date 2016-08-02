package com.tonto.weixin.core.handle.action.event;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.MessageConstant;
import com.tonto.weixin.core.message.request.event.EventMessage;
import com.tonto.weixin.core.message.request.event.QRCodeOrSubscribeEventMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.TextResponseMessage;

/**
 * 
 * 扫二维码关注公众号事件
 * 
 * 
 * @author TontoZhou
 *
 */
public class QRCodeOrSubscribeEventAdpater implements EventAdpater{

	private String welcome="欢迎使用本公众号服务，感谢您的关注!\n可输入以下字符:\n" +
			"?:显示当前可选择菜单或需要输入的值\n" +
			"#:返回到上一级\n" +
			"##:返回到主菜单";
	
	@Override
	public ResponseMessage process(Session session, EventMessage message,ProcessContext processContext) {		
		QRCodeOrSubscribeEventMessage eventMsg=(QRCodeOrSubscribeEventMessage) message;
		String eventKey=eventMsg.getEventKey();
		if(eventKey==null||"".equals(eventKey))
		{
			String event=eventMsg.getEvent();
			if(MessageConstant.EVENT_TYPE_SUBSCRIBE.equals(event))
			{
				TextResponseMessage response=new TextResponseMessage();
				response.setContent(welcome);
				return response;		
			}
			
		}
		return null;
	}

	@Override
	public Class<? extends EventMessage> getEventType2Process() {
		return QRCodeOrSubscribeEventMessage.class;
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

}
