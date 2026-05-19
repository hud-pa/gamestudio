package sk.tuke.gamestudio.service;

import org.springframework.transaction.annotation.Transactional;
import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Double average = (Double) entityManager
                .createQuery("SELECT AVG(e.rating) FROM Rating e WHERE e.game=:game")
                .setParameter("game", game)
                .getSingleResult();

        return average == null ? 0 : (int) Math.round(average);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            Rating current = (Rating) entityManager
                    .createNamedQuery("Rating.getRating")
                    .setParameter("game", game)
                    .setParameter("player", player)
                    .getSingleResult();

            return current.getRating();
        } catch (NoResultException e) {
            return 0;
        }
    }
}


