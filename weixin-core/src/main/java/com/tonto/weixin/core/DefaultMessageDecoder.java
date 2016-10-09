package com.tonto.weixin.core;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tonto.common.util.ParseUtil;
import com.tonto.weixin.core.message.request.EventConstant;
import com.tonto.weixin.core.message.request.RequestConstant;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.request.common.ImageRequestMessage;
import com.tonto.weixin.core.message.request.common.LinkRequestMessage;
import com.tonto.weixin.core.message.request.common.LocationRequestMessage;
import com.tonto.weixin.core.message.request.common.ShortVideoRequestMessage;
import com.tonto.weixin.core.message.request.common.TextRequestMessage;
import com.tonto.weixin.core.message.request.common.VideoRequestMessage;
import com.tonto.weixin.core.message.request.common.VoiceRequestMessage;
import com.tonto.weixin.core.message.request.event.ButtonEventMessage;
import com.tonto.weixin.core.message.request.event.EventMessage;
import com.tonto.weixin.core.message.request.event.LocationEventMessage;
import com.tonto.weixin.core.message.request.event.QRCodeOrSubscribeEventMessage;

/**
 * 
 * 默认消息解码
 * 
 * @author TontoZhou
 *
 */
public class DefaultMessageDecoder implements MessageDecoder{
	
	private final static Logger logger=Logger.getLogger(DefaultMessageDecoder.class);
	
	@Override
	public RequestMessage decode(HttpServletRequest request) {
		try{
			InputStream inputStream=request.getInputStream();
			
			SAXReader reader=new SAXReader();
			Document document=reader.read(inputStream);
	        
			Element root=document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> elements=root.elements();
			
			Map<String,String> paramMap=new HashMap<String,String>();			
			
			for(Element element:elements)
				paramMap.put(element.getName(), element.getText());
						
			if(logger.isDebugEnabled())
				logger.debug("get request:"+paramMap);				
						
			return createMessage(paramMap);
		}
		catch(Exception e)
		{
			logger.error("解析微信服务器的请求错误！"+e.getMessage());
		}
		
		return null;
	}
	
	
	//-------------------------------------------------------------------
	// 
	//  反射生成消息类
	//
	//--------------------------------------------------------------------
	
	private static final Map<String,Class<? extends RequestMessage>> msgType2classMap;
	private static final Map<String,Class<? extends EventMessage>> eventType2classMap;
	
	static{
		//写死
		
		msgType2classMap=new HashMap<String,Class<? extends RequestMessage>>();
		msgType2classMap.put(RequestConstant.REQ_MESSAGE_TYPE_TEXT, TextRequestMessage.class);
		msgType2classMap.put(RequestConstant.REQ_MESSAGE_TYPE_IMAGE, ImageRequestMessage.class);
		msgType2classMap.put(RequestConstant.REQ_MESSAGE_TYPE_LINK, LinkRequestMessage.class);
		msgType2classMap.put(RequestConstant.REQ_MESSAGE_TYPE_LOCATION, LocationRequestMessage.class);
		msgType2classMap.put(RequestConstant.REQ_MESSAGE_TYPE_SHORTVIDEO, ShortVideoRequestMessage.class);
		msgType2classMap.put(RequestConstant.REQ_MESSAGE_TYPE_VIDEO, VideoRequestMessage.class);
		msgType2classMap.put(RequestConstant.REQ_MESSAGE_TYPE_VOICE, VoiceRequestMessage.class);
				
		eventType2classMap=new HashMap<String,Class<? extends EventMessage>>();
		
		eventType2classMap.put(EventConstant.EVENT_TYPE_SCAN, QRCodeOrSubscribeEventMessage.class);
		eventType2classMap.put(EventConstant.EVENT_TYPE_SUBSCRIBE, QRCodeOrSubscribeEventMessage.class);
		eventType2classMap.put(EventConstant.EVENT_TYPE_UNSUBSCRIBE, QRCodeOrSubscribeEventMessage.class);
		eventType2classMap.put(EventConstant.EVENT_TYPE_LOCATION, LocationEventMessage.class);
		eventType2classMap.put(EventConstant.EVENT_TYPE_VIEW, ButtonEventMessage.class);
		eventType2classMap.put(EventConstant.EVENT_TYPE_CLICK, ButtonEventMessage.class);
	}
	
	private RequestMessage createMessage(Map<String,String> paramMap) throws Exception
	{	
		String msgType=paramMap.get("MsgType");
		
		if(msgType!=null)
		{
			Class<? extends RequestMessage> msgClass=null;
			
			if(RequestConstant.REQ_MESSAGE_TYPE_EVENT.equals(msgType))
			{
				String event=paramMap.get("Event");
				msgClass=eventType2classMap.get(event);
			}
			else
			{
				msgClass=msgType2classMap.get(msgType);
			}
			
			if(msgClass!=null)
			{		
				return stringMap2object(paramMap, msgClass);
			}
			else
			{
				throw new Exception("不能找到对应的Message类，MessageType："+msgType);
			}
		}
		else
		{
			throw new Exception("不能找到对应的Message类，MessageType："+msgType);
		}	
		
	}
	
	private static <T> T stringMap2object(Map<String, String> valueMap, Class<T> clazz) {
				
		T object;
		try {
			object = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			return null;
		}

		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			String name = method.getName();
			if (name.startsWith("set") && name.length() > 3 && method.getParameterTypes().length == 1 && method.getReturnType() == void.class) {
				
				name = name.substring(3);

				String value = valueMap.get(name);

				if (value != null && !"".equals(value)) {
					Object objValue = ParseUtil.parseString(value, method.getParameterTypes()[0]);

					try {
						method.invoke(object, objValue);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						if (logger.isDebugEnabled())
							logger.error("属性：" + name + "赋值错误！", e);
						continue;
					}
				}

			}

		}

		return object;

	}
}
