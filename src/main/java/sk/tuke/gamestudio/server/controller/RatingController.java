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
    @Autowired
    private UserController userController;
    @Autowired
    private RatingService ratingService;
    private String gameName;

    @RequestMapping("/rating")
    public String rateGame(@RequestParam(value = "rating", required = false)
                                    String rating, Model model) throws RatingException {
        int rated;
        gameName = "reversi";

        if(rating!=null) {
            rated = Integer.parseInt(rating);
            Rating ratincek = new Rating(userController.getLoggedUser(),gameName,rated,new java.util.Date());
            ratingService.setRating(ratincek);
        }

        //int ratingg = ratingService.getAverageRating("GuessWhatsInPicture");
        int myRating = ratingService.getRating("GuessWhatsInPicture",userController.getLoggedUser());
        model.addAttribute("myRating", myRating);
        //model.addAttribute("rating", ratingg);


        return "rating";
    }


}
