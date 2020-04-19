package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;

public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "palo";

    public static final String INSERT_RATING =
            "INSERT INTO rating (game, player, rating, ratedon) VALUES (?, ?, ?, ?)";

    public static final String SELECT_RATING =
            "SELECT game, player, rating, ratedon FROM rating WHERE game = ? ORDER BY ratedon DESC LIMIT 10;";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = connection.prepareStatement(INSERT_RATING)){
                ps.setString(1, rating.getGame());
                ps.setString(2, rating.getPlayer());
                ps.setInt(3, rating.getRating());
                ps.setTimestamp(4, new Timestamp(rating.getRatedon().getTime()));

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RatingException("Error saving rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        int rate = 0;
        int count=1;
        Rating rating;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = connection.prepareStatement(SELECT_RATING)){
                ps.setString(1, game);
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        rating = new Rating(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getInt(3),
                                rs.getTimestamp(4)
                        );
                        rate=rate+rating.getRating();
                        count++;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Error loading avarage rating", e);
        }
        return rate/count;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = connection.prepareStatement(SELECT_RATING)){
                ps.setString(1, game);
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        Rating rating = new Rating(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getInt(3),
                                rs.getTimestamp(4)
                        );
                        if(player.equals(rating.getPlayer())){
                            return rating.getRating();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Error loading rating", e);
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        Rating rating = new Rating("mastermind", "x", 5, new java.util.Date());
        Rating rating1 = new Rating("mastermind", "x", 4, new java.util.Date());
        RatingService ratingService = new RatingServiceJDBC();
        ratingService.setRating(rating);
        ratingService.setRating(rating1);
        System.out.println(ratingService.getAverageRating("mastermind"));
        System.out.println(ratingService.getRating("mastermind", "x"));
    }

}
