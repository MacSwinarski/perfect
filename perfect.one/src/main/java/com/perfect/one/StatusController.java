package com.perfect.one;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
}
