package com.jokey.rabbit.socket.config.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author JokeyFeng
 * @date: 2020/8/18
 * @project: spring-boot
 * @package: com.jokey.rabbit.socket.config.registry
 * @comment: Session注册器
 */
@Component
public class SocketSessionRegistry {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketSessionRegistry.class);
	
	/**
	 * 这个集合存储session
	 */
	private final ConcurrentMap<String, Set<String>> userSessionIds = new ConcurrentHashMap<>();
	
	private final Object lock = new Object();
	
	/**
	 * 获取sessionId
	 *
	 * @param userId
	 * @return
	 */
	public Set<String> getSessionIds(String userId) {
		return userSessionIds.getOrDefault(userId, new HashSet<>());
	}
	
	/**
	 * 获取所有session
	 *
	 * @return
	 */
	public ConcurrentMap<String, Set<String>> getAllSessionIds() {
		return this.userSessionIds;
	}
	
	/**
	 * register session
	 *
	 * @param userId
	 * @param sessionId
	 */
	public void registerSessionId(String userId, String sessionId) {
		Assert.notNull(userId, "User ID must not be null");
		Assert.notNull(sessionId, "Session ID must not be null");
		synchronized (this.lock) {
			Set<String> set = this.userSessionIds.get(userId);
			if (set == null) {
				set = new CopyOnWriteArraySet<>();
				this.userSessionIds.put(userId, set);
			}
			set.add(sessionId);
		}
		LOGGER.info("===============当前在线人数=============:   " + userSessionIds.size());
	}
	
	/**
	 * remove session
	 *
	 * @param userId
	 * @param sessionId
	 */
	public void unregisterSessionId(String userId, String sessionId) {
		Assert.notNull(userId, "User ID must not be null");
		Assert.notNull(sessionId, "Session ID must not be null");
		synchronized (this.lock) {
			Set<String> set = this.userSessionIds.get(userId);
			if (set != null && set.remove(sessionId) && set.isEmpty()) {
				this.userSessionIds.remove(userId);
			}
		}
		LOGGER.info("===============当前在线人数=============:   " + userSessionIds.size());
	}
}
