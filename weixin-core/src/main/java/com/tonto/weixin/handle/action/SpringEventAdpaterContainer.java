package com.tonto.weixin.handle.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tonto.weixin.core.handle.action.EventActionContainer;
import com.tonto.weixin.core.handle.action.EventAdpaterContainer;
import com.tonto.weixin.core.handle.action.event.ButtonEventAdpater;
import com.tonto.weixin.core.handle.action.event.EventAdpater;
import com.tonto.weixin.core.handle.action.event.LocationEventAdpater;
import com.tonto.weixin.core.handle.action.event.QRCodeOrSubscribeEventAdpater;
import com.tonto.weixin.core.message.request.event.ButtonEventMessage;
import com.tonto.weixin.core.message.request.event.EventMessage;
import com.tonto.weixin.core.message.request.event.LocationEventMessage;
import com.tonto.weixin.core.message.request.event.QRCodeOrSubscribeEventMessage;

/**
 * 
 * 基于spring的事件适配器容器
 * 
 * @author TontoZhou
 *
 */
public class SpringEventAdpaterContainer implements EventAdpaterContainer{
	
	private final static Logger logger=Logger.getLogger(SpringEventAdpaterContainer.class);
	
	Map<Class<? extends EventMessage>,EventAdpater> eventActionMap;
	
	EventActionContainer eventActionContainer;
	ButtonEventAdpater buttonEventAdpater = new ButtonEventAdpater();
	
	/**
	 * 加载所有EventAction
	 */
	public void initialize()
	{
		logger.info("------------------加载EventAdpater开始-----------------");
		
		eventActionMap=new HashMap<Class<? extends EventMessage>,EventAdpater>();
		
		eventActionMap.put(LocationEventMessage.class, new LocationEventAdpater());
		eventActionMap.put(QRCodeOrSubscribeEventMessage.class, new QRCodeOrSubscribeEventAdpater());
		
		buttonEventAdpater.setEventActionContainer(eventActionContainer);
		eventActionContainer.initialize();
		
		eventActionMap.put(ButtonEventMessage.class, buttonEventAdpater);
		
		logger.info("------------------加载EventAdpater结束-----------------");
	}
		
	public EventAdpater getEventAction(Class<? extends EventMessage> clazz)
	{
		return eventActionMap.get(clazz);
	}

	public EventActionContainer getEventActionContainer() {
		return eventActionContainer;
	}

	public void setEventActionContainer(EventActionContainer eventActionContainer) {
		this.eventActionContainer = eventActionContainer;
		eventActionContainer.initialize();
		buttonEventAdpater.setEventActionContainer(eventActionContainer);
	}
	
}
