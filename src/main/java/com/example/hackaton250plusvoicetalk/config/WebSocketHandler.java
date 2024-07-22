package com.example.hackaton250plusvoicetalk.config;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private static final ConcurrentLinkedQueue<WebSocketSession> maleQueue = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<WebSocketSession> femaleQueue = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<WebSocketSession> bothQueue = new ConcurrentLinkedQueue<>();
    private static final ConcurrentHashMap<WebSocketSession, WebSocketSession> matchedSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        if (payload.contains("wait_for_match")) {
            String gender = extractGender(payload);
            matchUser(session, gender);
        } else if (payload.contains("end_call")) {
            endCall(session);
        }
    }

    private String extractGender(String payload) {
        if (payload.contains("\"gender\":\"male\"")) {
            return "male";
        } else if (payload.contains("\"gender\":\"female\"")) {
            return "female";
        } else {
            return "both";
        }
    }

    private void matchUser(WebSocketSession session, String gender) throws Exception {
        WebSocketSession matchedSession = null;

        if ("male".equals(gender)) {
            matchedSession = findMatch(session, maleQueue);
        } else if ("female".equals(gender)) {
            matchedSession = findMatch(session, femaleQueue);
        } else {
            matchedSession = findMatch(session, bothQueue);
        }

        if (matchedSession == null) {
            if ("male".equals(gender)) {
                maleQueue.add(session);
            } else if ("female".equals(gender)) {
                femaleQueue.add(session);
            } else {
                bothQueue.add(session);
            }
        } else {
            matchedSessions.put(session, matchedSession);
            matchedSessions.put(matchedSession, session);
            matchedSession.sendMessage(new TextMessage("{\"type\":\"match_found\"}"));
            session.sendMessage(new TextMessage("{\"type\":\"match_found\"}"));
        }
    }

    private WebSocketSession findMatch(WebSocketSession session, ConcurrentLinkedQueue<WebSocketSession> queue) {
        WebSocketSession matchedSession = queue.poll();
        while (matchedSession != null && !matchedSession.isOpen()) {
            matchedSession = queue.poll();
        }
        return matchedSession;
    }

    private void endCall(WebSocketSession session) throws Exception {
        WebSocketSession matchedSession = matchedSessions.remove(session);
        if (matchedSession != null && matchedSession.isOpen()) {
            matchedSession.sendMessage(new TextMessage("{\"type\":\"call_ended\"}"));
            matchedSessions.remove(matchedSession);
            matchedSession.close();
        }
        session.sendMessage(new TextMessage("{\"type\":\"call_ended\"}"));
        session.close();
        sessions.remove(session.getId());
        maleQueue.remove(session);
        femaleQueue.remove(session);
        bothQueue.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSession matchedSession = matchedSessions.remove(session);
        if (matchedSession != null && matchedSession.isOpen()) {
            matchedSession.sendMessage(new TextMessage("{\"type\":\"call_ended\"}"));
            matchedSessions.remove(matchedSession);
        }
        sessions.remove(session.getId());
        maleQueue.remove(session);
        femaleQueue.remove(session);
        bothQueue.remove(session);
    }
}
