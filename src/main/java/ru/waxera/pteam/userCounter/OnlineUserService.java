package ru.waxera.pteam.userCounter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OnlineUserService {

    final Map<String, Long> clientActivity = new ConcurrentHashMap<>();

    private static final long CLIENT_EXPIRE_AFTER_MS = 30_000; // 30 секунд

    @PostConstruct
    public void init() {
        cleanupExpiredClients();
        System.out.println("UserService is init");
    }

    public void ping(String clientId) {
        clientActivity.put(clientId, System.currentTimeMillis());
    }

    public int getOnlineCount() {
        return clientActivity.size();
    }

    public Map<String, Long> getClientActivity() {
        return clientActivity;
    }

    @Scheduled(fixedDelay = 10_000)
    public void cleanupExpiredClients() {
        long now = System.currentTimeMillis();
        clientActivity.entrySet().removeIf(entry ->
                (now - entry.getValue()) > CLIENT_EXPIRE_AFTER_MS
        );
    }
}