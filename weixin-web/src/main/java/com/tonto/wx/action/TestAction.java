package com.tonto.wx.action;

import org.springframework.stereotype.Component;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.action.common.CommonProcessAction;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.request.common.TextRequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.TextResponseMessage;

@Component
public class TestAction extends CommonProcessAction{
	
	@Override
	public ResponseMessage execute(Session session,
			RequestMessage message, ProcessContext context) {
		
		String str=((TextRequestMessage)message).getContent().trim();			
		TextResponseMessage response=new TextResponseMessage();	
		response.setContent("您发送了："+str);
		return response;
	}

}
