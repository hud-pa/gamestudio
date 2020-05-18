package sk.tuke.gamestudio.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class CommentController{

    @Autowired
    private UserController userController;
    @Autowired
    private CommentService commentService;

    private String gameName;

    @RequestMapping("/comment")
    public String commentGame(@RequestParam(value = "player", required = false)String player,
                              @RequestParam(value = "comment", required = false)String comment,
                              Model model) throws CommentException {

        Comment komentik = new Comment(gameName,player,comment,new java.util.Date());

        commentService.addComment(komentik);

        List<Comment> comments = commentService.getComments("Reversi");
        model.addAttribute("comments", comments);


        return "comment";

    }
    }