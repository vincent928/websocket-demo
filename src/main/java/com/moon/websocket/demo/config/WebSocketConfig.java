package com.moon.websocket.demo.config;

import com.moon.websocket.demo.config.interceptor.WsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author : moon
 * @Date : 2020/11/26 15:34
 * @Description :
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WsInterceptor wsInterceptor;
    @Autowired
    private WebSocketServerHandler webSocketServer;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(webSocketServer, "ws")
                .addInterceptors(wsInterceptor)
                .setAllowedOrigins("*");
    }
}
