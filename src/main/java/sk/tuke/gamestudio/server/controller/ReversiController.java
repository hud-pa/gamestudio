package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.reversi.core.Field;
import sk.tuke.gamestudio.game.reversi.core.GameState;
import sk.tuke.gamestudio.game.reversi.core.PlayerState;
import sk.tuke.gamestudio.game.reversi.core.Tile;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/reversi")
public class ReversiController {

        private static final String GAME_NAME = "reversi";

        @Autowired
        private ScoreService scoreService;
        //+++

        @Autowired
        private UserController userController;

        private Field field;
        private boolean firstTime = true;
        private boolean scoreSaved = false;
        private String message;

        public String getMessage() {
        return message;
    }

        private String scoreBlack;
        public String getScoreBlack() {
        return scoreBlack;
    }
        public void setScoreBlack(String scoreBlack) {
        this.scoreBlack = scoreBlack;
    }

         private String scoreWhite;
         public String getScoreWhite() {
        return scoreWhite;
    }
        public void setScoreWhite(String scoreWhite) {
        this.scoreWhite= scoreWhite;
    }




        @RequestMapping
        public String reversi(String row, String column, Model model) {
            if (field == null)
                newGame();
            try {
                if(firstTime){
                    field.setPlayerState(PlayerState.PLAYING_BLACK);
                    //field.markFields();
                    firstTime = false;
                }else
                if (field.getState() == GameState.PLAYING) {
                    //field.markFields();
                    if(column !=null && row != null)
                    field.putDisk(Integer.parseInt(row), Integer.parseInt(column));
                    field.changePlayer();
                    field.markFields();
                    scoreBlack = "Score of BLACK player is : " + field.getBlackDisksCount();//////////////
                    scoreWhite = "Score of WHITE player is : "+ field.getWhiteDisksCount();

                    if(field.getState() != GameState.PLAYING && !scoreSaved) {
                        String player = userController.isLogged()
                                ? userController.getLoggedUser()
                                : "anonymous";

                        scoreService.addScore(new Score(
                                GAME_NAME,
                                player,
                                winnerPoints(),
                                new Date()
                        ));
                        scoreSaved = true;
                    }

                    if(field.getState() == GameState.WIN_BLACK)
                        message = "Black player won ! Your score is : " + field.getBlackDisksCount();
                    else if(field.getState() == GameState.WIN_WHITE)
                        message = "White player won ! Your score is : " + field.getWhiteDisksCount();
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            prepareModel(model);
            return "reversi";
        }



        @RequestMapping("/new")
        public String newGame(Model model) {
            newGame();
            prepareModel(model);
            return "reversi";
        }

        public GameState getGameState() {
            return field.getState();
        }

        public PlayerState getPlayerState() {
        return field.getPlayerState();
    }

    public int score(){
            return field.getBlackDisksCount();
    }

        public String getHtmlField() {
            StringBuilder sb = new StringBuilder();
            sb.append("<table class='field'>\n");
            for (int row = 0; row < field.getRowCount(); row++) {
                sb.append("<tr>\n");
                for (int column = 0; column < field.getColumnCount(); column++) {
                    Tile tile = field.getTile(row, column);
                    sb.append("<td>\n");
                    if (field.equals(this.field))
                        sb.append("<a href='" +
                                String.format("/reversi?row=%s&column=%s", row, column)
                                + "'>\n");
                    sb.append("<img src='/images/reversi/" + getImageName(tile) + ".png'>");
                    if (field.equals(this.field))
                        sb.append("</a>\n");
                    sb.append("</td>\n");
                }
                sb.append("</tr>\n");
            }
            sb.append("</table>\n");

            return sb.toString();
        }

        public int winnerPoints(){
            if(getGameState() == GameState.WIN_BLACK) return field.getBlackDisksCount();
            else return field.getWhiteDisksCount();
        }

        private String getImageName(Tile tile) {
            switch (tile.getState()) {
                case EMPTY:
                    return "empty";
                case MARKED:
                    return "marked";
                case BLACK_DISK:
                    return "black_disk";
                case WHITE_DISK:
                    return "white_disk";
            }
            throw new IllegalArgumentException("State is not supported " + tile.getState());
        }

        private void prepareModel(Model model) {
            model.addAttribute("scores", scoreService.getBestScores(GAME_NAME));
        }

        private void newGame() {
            field = new Field();
            scoreSaved = false;
            message = null;
            scoreBlack = null;
            scoreWhite = null;
        }

    }


