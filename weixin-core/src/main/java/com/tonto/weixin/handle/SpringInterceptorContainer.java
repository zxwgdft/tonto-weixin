package com.tonto.weixin.handle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tonto.weixin.core.handle.ActionInterceptor;
import com.tonto.weixin.core.handle.EventInterceptor;
import com.tonto.weixin.core.handle.interceptor.InterceptorContainer;
import com.tonto.weixin.core.handle.interceptor.MessageInterceptor;
import com.tonto.weixin.core.handle.interceptor.common.CommonMessageInterceptor;
import com.tonto.weixin.core.message.request.RequestConstant;
import com.tonto.weixin.handle.action.ProcessNodeBuilder;
import com.tonto.weixin.handle.action.SpringEventActionContainer;
import com.tonto.weixin.handle.action.SpringEventAdpaterContainer;
import com.tonto.weixin.spring.SpringBeanHolder;

/**
 * <h2>基于Spring的拦截器容器</h2>
 * <p>
 * 从spring中注入的所有{@link MessageInterceptor}
 * 实例根据location排序一个列表作为除event外的所有消息类型的拦截栈。
 * </p>
 * <p>
 * ActionInvocationInterceptor 为默认最终消息拦截器,所有非event的消息类型都需要被此最终拦截
 * </p>
 * <p>
 * EventInterceptor 为默认最终消息拦截器,event的消息类型都需要被此最终拦截
 * </p>
 * 
 * @author TontoZhou
 * @date 2015-5-5
 */
public class SpringInterceptorContainer implements InterceptorContainer {

	private final static Logger logger = Logger.getLogger(SpringInterceptorContainer.class);

	/**
	 * key:message type value:list of message interceptor
	 */
	Map<String, List<MessageInterceptor>> messageInterceptorMap;

	ActionInterceptor actionInvocationInterceptor;
	EventInterceptor eventInterceptor;

	public void initialize() {
		
		// 初始化生成messageInterceptorMap
		messageInterceptorMap = new HashMap<String, List<MessageInterceptor>>();
		List<MessageInterceptor> messageInterceptors = new ArrayList<MessageInterceptor>();

		logger.info("----------------开始获取消息拦截器----------------");

		// 获取拦截器
		Map<String, MessageInterceptor> interceptorMap = SpringBeanHolder.getBeansByType(MessageInterceptor.class);
		if (interceptorMap != null) {
			Collection<MessageInterceptor> values = interceptorMap.values();
			int size = values.size();
			if (size > 0) {
				MessageInterceptor[] is = new MessageInterceptor[size];
				int i = 0;
				for (Object obj : values) {
					is[i++] = (MessageInterceptor) obj;

				}

				// 排序

				for (int j = size - 1; j > 0; j--) {
					int location = is[j].getInterceptorLocation();
					int index = j;

					for (int k = 0; k < j; k++) {
						int l = is[k].getInterceptorLocation();
						if (l > location) {
							location = l;
							index = k;
						}
					}

					if (index != j) {
						MessageInterceptor mi = is[j];
						is[j] = is[index];
						is[index] = mi;
					}
				}

				for (MessageInterceptor mi : is) {
					messageInterceptors.add(mi);
					logger.info("加载拦截器：" + mi + "/location:" + mi.getInterceptorLocation());
				}
			}
		}

		//节点构建器
		ProcessNodeBuilder processNodeBuilder = new ProcessNodeBuilder();
		processNodeBuilder.build();
		
		//通用消息拦截器
		CommonMessageInterceptor commonMessageInterceptor = new CommonMessageInterceptor();
		commonMessageInterceptor.setProcessNodeTreeBuilder(processNodeBuilder);
		messageInterceptors.add(commonMessageInterceptor);
		logger.info("加载通用文本拦截器：CommonMessageInterceptor");
		
		actionInvocationInterceptor = new ActionInterceptor();
		actionInvocationInterceptor.setProcessNodeTreeBuilder(processNodeBuilder);
		messageInterceptors.add(actionInvocationInterceptor);
		logger.info("加载Action调用拦截器：ActionInvocationInterceptor");

		messageInterceptorMap.put(RequestConstant.REQ_MESSAGE_TYPE_IMAGE, messageInterceptors);
		messageInterceptorMap.put(RequestConstant.REQ_MESSAGE_TYPE_LINK, messageInterceptors);
		messageInterceptorMap.put(RequestConstant.REQ_MESSAGE_TYPE_LOCATION, messageInterceptors);
		messageInterceptorMap.put(RequestConstant.REQ_MESSAGE_TYPE_SHORTVIDEO, messageInterceptors);
		messageInterceptorMap.put(RequestConstant.REQ_MESSAGE_TYPE_VOICE, messageInterceptors);
		messageInterceptorMap.put(RequestConstant.REQ_MESSAGE_TYPE_TEXT, messageInterceptors);

		List<MessageInterceptor> eventInterceptors = new ArrayList<MessageInterceptor>();
		eventInterceptor = new EventInterceptor();
		
		/*
		 * 创建事件适配器和事件动作处理器
		 */
		SpringEventAdpaterContainer eventAdpaterContainer = new SpringEventAdpaterContainer();
		
		SpringEventActionContainer eventActionContainer = new SpringEventActionContainer();		
		eventActionContainer.initialize();
					
		eventAdpaterContainer.setEventActionContainer(eventActionContainer);
		eventAdpaterContainer.initialize();
		
		eventInterceptor.setEventAdpaterContainer(eventAdpaterContainer);

		eventInterceptors.add(eventInterceptor);
		logger.info("加载Event调用拦截器：EventInterceptor");

		messageInterceptorMap.put(RequestConstant.REQ_MESSAGE_TYPE_EVENT, eventInterceptors);

		logger.info("----------------获取消息拦截器结束----------------");
	}

	public List<MessageInterceptor> getMessageInterceptors(String messageType) {
		return messageInterceptorMap.get(messageType);
	}

	@Override
	public List<MessageInterceptor> getEventInterceptors() {
		return messageInterceptorMap.get(RequestConstant.REQ_MESSAGE_TYPE_EVENT);
	}

}
