package com.learnwy.servlet;


import com.learnwy.model.SysMenu;

import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ServerEndpoint("/webSocket")
public class WebSocketWY {
    private static int onLineCount = 0;
    private static CopyOnWriteArraySet<WebSocketWY> webSocketSet = new CopyOnWriteArraySet<WebSocketWY>();
    private Session session;
    private long user_id;
    private static Pattern login_id = Pattern.compile("id:([\\d]*)");
    private static String complete_order = "order:";

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        onLineCount++;
        System.out.println("onOpen::WebSocket");
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        onLineCount--;
        System.out.println("onClode::WebSocket");
    }

    //recivce client Message
    @OnMessage
    public void onMessage(String message, Session session) {
        Matcher matcher = login_id.matcher(message);
        if (matcher.find()) {
            this.user_id = Long.valueOf(matcher.group(0));
            return;
        }


        //sendMessageToOther
        for (WebSocketWY webSocketWY : webSocketSet) {
            if (webSocketWY != this) {
                webSocketWY.sendMessage(message);
            }
        }
    }

    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
        //this.session.getBasicRemote().sendText(message);
    }
}
