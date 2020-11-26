package com.moon.websocket.demo.config;

import com.moon.websocket.demo.ws.WsSessionCacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : moon
 * @Date : 2020/11/26 16:01
 * @Description :
 */
@Component
public class WebSocketServerHandler extends TextWebSocketHandler {

    private static final String TOKEN = "token";
    private static final AtomicInteger NUM = new AtomicInteger(0);

    /**
     * 同@OnOpen 建立连接
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Object token = session.getAttributes().get(TOKEN);
        if (token != null) {
            //用户连接成功,放入缓存
            WsSessionCacheManager.add(token.toString(), session);
            System.out.println("当前连接人数:" + NUM.incrementAndGet());
        } else {
            throw new RuntimeException("当前用户登录失效");
        }
    }

    /**
     * 同@OnMessage 接收消息
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //获取客户端信息
        String msg = message.getPayload();
        Object token = session.getAttributes().get(TOKEN);
        System.out.println("Server receive " + token + " message：" + msg);
        session.sendMessage(new TextMessage("Server send message To " + token + ":" + LocalDateTime.now().toString()));
    }

    /**
     * 同@Onclose 断开连接
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object token = session.getAttributes().get(TOKEN);
        if (token != null) {
            WsSessionCacheManager.remove(token.toString());
        }
        System.out.println(token + "断开连接，当前连接人数:" + NUM.decrementAndGet());
    }
}
