package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.reversi.core.Field;
import sk.tuke.gamestudio.game.reversi.core.GameState;
import sk.tuke.gamestudio.game.reversi.core.Tile;
import sk.tuke.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/mines")
public class ReversiController {

        @Autowired
        private ScoreService scoreService;
        //+++

        @Autowired
        private UserController userController;

        private Field field;


        @RequestMapping
        public String stones(String row, String column, Model model) {
            if (field == null)
                newGame();
            try {
                if (field.getState() == GameState.PLAYING) {
                    field.putDisk(Integer.parseInt(row), Integer.parseInt(column));
                   /* if (userController.isLogged() && field.getState() == GameState.WIN_BLACK) {
                        scoreService.addScore(new Score(
                                (String)userController.getLoggedUser(),
                                field.getWhiteDisksCount(),
                                "disks",
                                new Date()
                        ));
                    }*/
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

   /* @RequestMapping("/mark")
    public String changeMark(Model model) {
        marking = !marking;
        prepareModel(model);
        return "mines";
    }

    public boolean isMarking() {
        return marking;
    }*/

        public GameState getGameState() {
            return field.getState();
        }

        //Tento pristup sice nie je idealny, ale pre zaciatok je najjednoduchsi
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
                                String.format("/mines?row=%s&column=%s", row, column)
                                + "'>\n");
                    sb.append("<img src='/images/mines/" + getImageName(tile) + ".png'>");
                    if (field.equals(this.field))
                        sb.append("</a>\n");
                    sb.append("</td>\n");
                }
                sb.append("</tr>\n");
            }
            sb.append("</table>\n");

            return sb.toString();
        }

        private String getImageName(Tile tile) {
            switch (tile.getState()) {
                case EMPTY:
                    return "empty";
                case MARKED:
                    return "marked";
                case BLACK_DISK:
                    return "black";
                case WHITE_DISK:
                    return "white";
            }
            throw new IllegalArgumentException("State is not supported " + tile.getState());
        }

        private void prepareModel(Model model) {
            model.addAttribute("scores", scoreService.getBestScores("reversi"));
        }

        private void newGame() {
            field = new Field();
        }
    }


