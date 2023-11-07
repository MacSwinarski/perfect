package com.perfect.one;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class StatusController {

    @GetMapping("/status")
    public boolean status() throws IOException {
        return true;
    }
}
