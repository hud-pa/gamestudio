package sk.tuke.gamestudio.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "homepage";
    }

    @GetMapping("/homepage")
    public String homepage() {
        return "homepage";
    }
}