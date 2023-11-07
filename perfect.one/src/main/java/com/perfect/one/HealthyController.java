package com.perfect.one;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HealthyController {

    @GetMapping("/healty")
    public boolean isHealthy() throws IOException {
        return true;
    }
}
