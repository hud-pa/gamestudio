package sk.tuke.gamestudio.service;

import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

import java.lang.reflect.Array;
import java.util.Arrays;

public class RatingServiceRestClient implements RatingService {
    private static final String URL = "http://localhost:8080/api/score";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(URL,rating,Rating.class);
    }

    @Override
    public int getAverageRating(String gameName) {
       // return (int)restTemplate.postForEntity(URL + "/" + gameName,);
     return getAverageRating(gameName);

    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return getRating(game,player);
    }
}
