package com.tonto.weixin.core.handle.session;

/**
 * 
 * 微信会话接口
 * 
 * @author TontoZhou
 *
 */
public interface Session {
		
	/**
	 * 移除属性
	 * @param name 属性名称
	 */
	public void removeAttribute(String name);
	
	/**
	 * 获取属性
	 * @param name 属性名称
	 * @return
	 */
	public Object getAttribute(String name);
	
	/**
	 * 放入属性
	 * @param name 属性名称
	 * @param value 属性值
	 */
	public void putAttribute(String name,Object value);

	/**
	 * 获取更新时间
	 * @return
	 */
	public long getAccessTime();
	
	/**
	 * 更新接触时间
	 */
	public void updateAccessTime();
	
	/**
	 * 获取会话用户名称
	 * @return
	 */
	public String getUsername();
	
	/**
	 * 设置会话用户名称
	 * @param username
	 */
	public void setUsername(String username);
}
