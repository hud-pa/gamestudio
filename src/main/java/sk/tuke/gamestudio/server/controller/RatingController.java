package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class RatingController {
    private static final String GAME_NAME = "reversi";

    @Autowired
    private UserController userController;

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating")
    public String rateGame(@RequestParam(value = "rating", required = false) String rating,
                           Model model) throws RatingException {
        String player = userController.isLogged() ? userController.getLoggedUser() : "anonymous";

        if (rating != null && !rating.trim().isEmpty()) {
            int rated = Integer.parseInt(rating);
            Rating savedRating = new Rating(player, GAME_NAME, rated, new java.util.Date());
            ratingService.setRating(savedRating);
        }

        int myRating = ratingService.getRating(GAME_NAME, player);
        model.addAttribute("myRating", myRating);

        return "rating";
    }
}
