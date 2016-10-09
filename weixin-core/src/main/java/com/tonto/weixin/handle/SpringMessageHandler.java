package com.tonto.weixin.handle;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.tonto.weixin.core.CoreServlet;
import com.tonto.weixin.core.handle.DefaultMessageHandler;


/**
 * 
 * 基于spring的消息处理器
 * 
 * @author TontoZhou
 *
 */
public class SpringMessageHandler extends DefaultMessageHandler implements ApplicationListener<ContextRefreshedEvent>{
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 由于web项目会存在两个容器导致该事件发生两次，所以在这里判断没父容器的容器发生事件才做操作
		if (event.getApplicationContext().getParent() == null) {
			
			if(CoreServlet.getCoreServlet() != null)				
				CoreServlet.getCoreServlet().setHandler(this);
			else
				CoreServlet.setMessageHandler(this);
			
			getInterceptorContainer().initialize();
			getSessionContainer().initialize();
			
		}
	}
	
}
