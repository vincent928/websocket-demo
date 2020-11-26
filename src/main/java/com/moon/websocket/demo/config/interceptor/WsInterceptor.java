package com.moon.websocket.demo.config.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : moon
 * @Date : 2020/11/26 16:35
 * @Description :
 */
@Component
public class WsInterceptor implements HandshakeInterceptor {
    /**
     * 握手前
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        System.out.println("开始握手。。。");
        Map<String, String> params = getParams(serverHttpRequest.getURI().getQuery());
        String token = params.get("token");
        if (token != null && token.length() > 0) {
            map.put("token", token);
            return true;
        }
        return false;
    }

    /**
     * 握手后
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("握手成功。。。");
    }

    private static Map<String, String> getParams(String url) {
        Map<String, String> map = new HashMap<>();
        String[] paramsArray = url.split("&");
        for (String param : paramsArray) {
            String[] array = param.split("=");
            map.put(array[0], array[1]);
        }
        return map;
    }
}
