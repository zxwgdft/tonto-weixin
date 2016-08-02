package com.tonto.weixin.handle.action.tree;


import com.tonto.weixin.spring.SpringBeanHolder;
import com.tonto.weixin.core.handle.action.ProcessAction;
import com.tonto.weixin.core.handle.action.common.MenuAction;

/**
 * <p>获取{@link ProcessAction}的实例工厂</p>
 * @author	TontoZhou
 * @date	2015-4-22
 */
public class ActionBeanFactory {
	
	//private final static Logger logger=Logger.getLogger(ActionBeanFactory.class);
	
	public static ProcessAction getProcessAction(String name)
	{		
		return getSpringBean(name);
	}
	
	private static MenuAction menuAction=null;
	static{
		menuAction=new MenuAction();
	}
	
	public static MenuAction getMenuAction() {		
		return menuAction;
	}
	
	private static ProcessAction getSpringBean(String name)
	{
		return (ProcessAction) SpringBeanHolder.getBean(name);
	}
	
	
}
