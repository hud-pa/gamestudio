package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.reversi.core.Field;
import sk.tuke.gamestudio.game.reversi.core.Tile;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)

public class TestController {


    @RequestMapping("/test")
    public String test(Model model) {
        return "test";
    }


}


