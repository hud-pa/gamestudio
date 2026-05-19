package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import sk.tuke.gamestudio.game.reversi.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.reversi.core.Field;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = { "sk.tuke.gamestudio" },
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "sk.tuke.gamestudio.server.*"))
public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
        //SpringApplication.run(SpringClient.class, args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.play();
    }

    @Bean
    public ConsoleUI consoleUI(Field field) {
        return new ConsoleUI(field);
    }

    @Bean
    public Field field() {
        return new Field();
    }

    @Bean
    public ScoreService scoreService() {
        //return new ScoreServiceJDBC();
        //return new ScoreServiceFile();
        return new ScoreServiceJPA();
        //return new ScoreServiceRestClient();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
        //return new CommentServiceRestClient();
    }

   @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
        //return new RatingServiceRestClient();
    }

   /* @Bean
    public GamePlayService gamePlayService() {
        return new GamePlayServiceJPA();
    }*/
}
