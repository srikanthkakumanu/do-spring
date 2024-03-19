package spring.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SimpleWebAppController {

    @GetMapping("/greetings")
    public String greetings() {
        return "<h1>Hello</h1>";
    }
}
