package com.tonto.weixin.core.handle.session;

import com.tonto.weixin.core.IContainer;


/**
 * 
 * 会话容器
 * 
 * @author TontoZhou
 *
 */
public interface SessionContainer extends IContainer{
	
	/**
	 * 获取一个会话
	 * @return
	 */
	public Session getSession(String username);	
	
	/**
	 * 添加一个会话
	 * @param session
	 */
	public void addSession(Session session);
	
	/**
	 * 异常一个会话
	 * @param username
	 */
	public void removeSession(String username);
	
	/**
	 * 添加一个会话
	 * @param username
	 * @return
	 */
	public Session addSession(String username);
	
}
