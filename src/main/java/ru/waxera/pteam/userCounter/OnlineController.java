package ru.waxera.pteam.userCounter;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class OnlineController {

    private final OnlineUserService onlineUserService;

    public OnlineController(OnlineUserService onlineUserService) {
        this.onlineUserService = onlineUserService;
        System.out.println("User Controller is running");
    }

    @PostMapping("/ping")
    public void ping(@RequestBody ClientPingRequest request) {
        String clientId = request.getClientId();
        if (clientId != null && !clientId.trim().isEmpty()) {
            onlineUserService.ping(clientId);
            System.out.println("Pinged from client: " + clientId);
        }
    }

    @GetMapping("/online")
    public int getOnlineCount() {
        return onlineUserService.getOnlineCount();
    }

    @GetMapping("/debug/clients")
    public Map<String, Long> debugClients() {
        return onlineUserService.getClientActivity();
    }

    static class ClientPingRequest {
        private String clientId;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }
    }
}