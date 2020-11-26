package com.moon.websocket.demo.ws;

import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : moon
 * @Date : 2020/11/26 16:10
 * @Description : Session缓存管理池
 */
public class WsSessionCacheManager {

    private static ConcurrentHashMap<String, WebSocketSession> SESSION_CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 添加Session
     *
     * @param key
     * @param session
     */
    public static void add(String key, WebSocketSession session) {
        SESSION_CACHE_MAP.put(key, session);
    }

    /**
     * 获取session
     *
     * @param key
     * @return
     */
    public static WebSocketSession get(String key) {
        return SESSION_CACHE_MAP.get(key);
    }

    /**
     * 删除session，并返回
     *
     * @param key
     * @return
     */
    public static WebSocketSession remove(String key) {
        return SESSION_CACHE_MAP.remove(key);
    }

    /**
     * 删除并关闭连接
     *
     * @param key
     */
    public static void removeAndClose(String key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
