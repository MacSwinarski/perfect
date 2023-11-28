package com.perfect.two;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.Map;

@RestController
public class StatusController {

    @GetMapping("/status/live")
    public boolean live() throws IOException {
        return true;
    }

    @GetMapping("/status/ready")
    public boolean ready() throws IOException {
        return true;
    }

    @GetMapping("/status/services")
    public Map<String,String> others() throws IOException {
        final String perfectTwo = "perfect-one:8080";
        final RestClient restClient = RestClient.builder()
                .baseUrl("http://" + perfectTwo).build();
        int httpCode = restClient.get().uri("/status/ready").retrieve().toEntity(String.class).getStatusCode().value();
        return Map.of(perfectTwo, "status:"+httpCode);
    }
}
