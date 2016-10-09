package com.tonto.weixin.core.handle.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <p>表示服务器与个人微信的会话</p>
 * @author	TontoZhou
 * @date	2015-4-14
 */
public class DefaultSession implements Session{
	/**
	 * 最后访问时间
	 */
	long accessTime;
	
	/**
	 * 微信用户号
	 */
	String username;
	
	/**
	 * 存放属性
	 */
	Map<String,Object> attributeMap;
		
	
	public DefaultSession(String username)
	{
		if(username==null)
			throw new RuntimeException("session usename can't be null!");
		
		this.username=username;
		attributeMap=new ConcurrentHashMap<String,Object>();		
		updateAccessTime();
	}
	
	
	public void removeAttribute(String name)
	{
		if(name==null)
			return;
		
		attributeMap.remove(name);
	}
	
	public Object getAttribute(String name)
	{
		if(name==null)
			return null;
		
		return attributeMap.get(name);
	}
	
	public void putAttribute(String name,Object value)
	{
		if(name==null)
			throw new RuntimeException("attribute name can't be null");
		
		if(value==null)
			removeAttribute(name);
		
		attributeMap.put(name, value);
	}

	public long getAccessTime() {
		return accessTime;
	}
	
	/**
	 * 跟新访问时间
	 */
	public void updateAccessTime()
	{
		accessTime=System.currentTimeMillis();
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

}
