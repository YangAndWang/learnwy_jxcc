package com.learnwy.servlet;


import com.learnwy.controller.WebSocketController;

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
    // id is user login
    // order is order dish,and cook need to cook and complete is need fwy to tran dish
    private static Pattern login_id = Pattern.compile("id:([\\d]*)");
    private static Pattern order_id = Pattern.compile("order:");
    private static Pattern complete_id = Pattern.compile("complete:");
    private static String complete_order = "order:";
    //now just decide there only need two role needed to
    //服务员
    private static CopyOnWriteArraySet<WebSocketWY> fwy = new CopyOnWriteArraySet<>();
    //厨师
    private static CopyOnWriteArraySet<WebSocketWY> cs = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        System.out.println("onOpen::WebSocket");
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        fwy.remove(this);
        cs.remove(this);
        System.out.println("onClode::WebSocket");
    }

    //recivce client Message
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.printf("\nThe WebSocket Server Receive Message:%s", message);
        Matcher login = login_id.matcher(message);
        Matcher order = order_id.matcher(message);
        Matcher complete = complete_id.matcher(message);
        // 发过来用户的id来确定各自的权限以及需要通知的东西
        if (login.find()) {
            this.user_id = Long.valueOf(login.group(1));
            if (user_id != -1) {
                boolean isRemoved = webSocketSet.remove(this);
                if (isRemoved) {
                    long role_id = WebSocketController.checkPower(user_id);
                    if (role_id == 33) {
                        fwy.add(this);
                    } else if (role_id == 32) {
                        cs.add(this);
                    } else {
                    }
                }
            }
            return;
        }
        if (order.find()) {
            for (WebSocketWY webSocketWY : cs) {
                webSocketWY.sendMessage("order:");
            }
            return;
        }
        if (complete.find()) {
            for (WebSocketWY webSocketWY : fwy) {
                webSocketWY.sendMessage("complete:");
            }
            return;
        }
    }

    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
        //this.session.getBasicRemote().sendText(message);
    }

    public static void orderCS() {
        for (WebSocketWY webSocketWY : cs) {
            webSocketWY.sendMessage("order:");
        }
        return;
    }

    public static void completeCS() {
        for (WebSocketWY webSocketWY : cs) {
            webSocketWY.sendMessage("complete:");
        }
        return;
    }

    public static void orderFWY() {
        for (WebSocketWY webSocketWY : fwy) {
            webSocketWY.sendMessage("order:");
        }
        return;
    }

    public static void completeFWY() {
        for (WebSocketWY webSocketWY : fwy) {
            webSocketWY.sendMessage("complete:");
        }
        return;
    }
}
