package io.sekiro.inbox_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    @GetMapping("/secured")
    public String secured() {
        return "home, secured";
    }

    @GetMapping("/")
    public String home() {
        return "home, home";
    }
}
