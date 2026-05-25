package sk.tuke.gamestudio.server.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class CommentController {

    private static final String GAME_NAME = "reversi";

    @Autowired
    private UserController userController;
    @Autowired
    private CommentService commentService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/comment")
    public String commentGame(@RequestParam(value = "comment", required = false) String comment,
                              @RequestParam(value = "rating", required = false) String rating,
                              Model model) throws CommentException, RatingException {

        String player = userController.isLogged() ? userController.getLoggedUser() : "anonymous";

        if (comment != null && !comment.trim().isEmpty()) {
            Comment savedComment = new Comment(player, GAME_NAME, comment, new Date());
            commentService.addComment(savedComment);
        }

        if (rating != null && !rating.trim().isEmpty()) {
            int rated = Integer.parseInt(rating);
            Rating savedRating = new Rating(player, GAME_NAME, rated, new Date());
            ratingService.setRating(savedRating);
        }

        model.addAttribute("scores", scoreService.getBestScores(GAME_NAME));
        model.addAttribute("comments", commentService.getComments(GAME_NAME));

        try {
            model.addAttribute("myRating", ratingService.getRating(GAME_NAME, player));
        } catch (Exception e) {
            model.addAttribute("myRating", 0);
        }

        try {
            model.addAttribute("averageRating", ratingService.getAverageRating(GAME_NAME));
        } catch (Exception e) {
            model.addAttribute("averageRating", 0);
        }

        return "comment";
    }
}