package com.tonto.weixin.handle.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tonto.weixin.core.handle.action.EventActionContainer;
import com.tonto.weixin.core.handle.action.EventProcessAction;
import com.tonto.weixin.spring.SpringBeanHolder;

/**
 * 
 * 基于spring的事件动作容器
 * 
 * @author TontoZhou
 *
 */
public class SpringEventActionContainer implements EventActionContainer{

	private static final Logger logger = Logger.getLogger(SpringEventActionContainer.class);

	private Map<String, EventProcessAction> actionMap;

	public void initialize() {
		
		actionMap = new HashMap<String, EventProcessAction>();

		logger.info("-----------------------------初始化EventActionManager开始----------------------");

		Collection<EventProcessAction> beans = SpringBeanHolder.getBeansByType(EventProcessAction.class).values();
		for (EventProcessAction action : beans) {
			String key = action.getEventKey();
			if (key == null)
				logger.error("eventProcessAction:" + action + " has null key event");
			else {
				logger.info("加载EventProcessAction：" + action + "/key event:" + key);
				actionMap.put(key, action);
			}
		}

		logger.info("-----------------------------初始化EventActionManager结束----------------------");

	}

	public EventProcessAction getEventProcessAction(String eventkey) {
		return actionMap.get(eventkey);
	}

}
