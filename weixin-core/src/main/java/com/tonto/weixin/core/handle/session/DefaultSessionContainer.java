package com.tonto.weixin.core.handle.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


/**
 * 
 * 默认会话容器
 * 
 * @author TontoZhou
 *
 */
public class DefaultSessionContainer implements SessionContainer {

	private final static Logger logger = Logger.getLogger(DefaultSessionContainer.class);

	/**
	 * <p>
	 * key:微信用户名
	 * </p>
	 * <p>
	 * value:与个人微信的一个会话
	 * </p>
	 */
	Map<String, DefaultSession> sessionMap;

	private static final int interval2check = 2000; // 每隔2秒检查一次
	private static final int intervalTime = 10 * 60 * 1000; // 超时时间10分钟

	/**
	 * 初始化
	 */
	public void initialize() {
		
		logger.info("----------------微信会话管理器初始化开始----------------");

		sessionMap = new ConcurrentHashMap<String, DefaultSession>();

		new Thread(new Runnable() {

			@Override
			public void run() {
				logger.info("启动session超时检查线程！");

				try {
					while (true) {
						Thread.sleep(interval2check);

						for (DefaultSession session : sessionMap.values()) {
							long overtime = System.currentTimeMillis() - session.accessTime;
							if (overtime > intervalTime) {
								removeSession(session);
							}
						}
					}
				} catch (Exception e) {
					logger.error("session超时检查线程错误!" + e.getMessage());
				}
			}

		}).start();

		logger.info("----------------微信会话管理器初始化结束----------------");

	}

	/**
	 * 
	 * @param username
	 * @return 如果username为null，则返回null
	 */
	public DefaultSession getSession(String username) {
		if (username == null)
			return null;

		return sessionMap.get(username);
	}

	/**
	 * session或session中的sessionId为null，否则不做操作
	 * 
	 * @param session
	 */
	public void addSession(Session session) {
		if (session != null && session instanceof DefaultSession) {
			String username = session.getUsername();
			if (username != null) {
				sessionMap.put(username, (DefaultSession) session);
			}
		}
	}

	/**
	 * @param username
	 *            为null则不做操作
	 */
	public void removeSession(String username) {
		if (username == null)
			return;
		sessionMap.remove(username);
	}

	/**
	 * @param session
	 *            为null则不做操作
	 */
	public void removeSession(Session session) {
		if (session == null)
			return;
		String username = session.getUsername();
		removeSession(username);
	}

	/**
	 * @param username
	 * @return
	 */
	public Session addSession(String username) {

		if (username != null) {
			DefaultSession session = new DefaultSession(username);
			sessionMap.put(username, session);
			return session;
		}
		return null;
	}

}
