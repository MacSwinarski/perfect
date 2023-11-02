package com.perfect.one;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Scanner;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() throws IOException {
        return "Hello from Perfect-One app! running on: " + execReadToString("hostname");
    }

    public static String execReadToString(String execCommand) throws IOException {
        try (Scanner s = new Scanner(Runtime.getRuntime().exec(execCommand).getInputStream()).useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "";
        }
    }
}
